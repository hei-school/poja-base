package com.company.base.file;

import com.company.base.PojaGenerated;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.internal.waiters.ResponseOrException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ChecksumAlgorithm;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@PojaGenerated
@Component
public class BucketComponent {

  private final String bucketName;
  private final FileTyper fileTyper;

  private final S3Client s3Client;
  private final S3Presigner preSigner;

  public BucketComponent(@Value("${aws.s3.bucket}") String bucketName, FileTyper fileTyper) {
    this.bucketName = bucketName;
    this.fileTyper = fileTyper;
    this.s3Client = S3Client.create();
    this.preSigner = S3Presigner.builder().build();
  }

  public FileHash upload(File file, String bucketKey) {
    PutObjectRequest request =
        PutObjectRequest.builder()
            .bucket(bucketName)
            .contentType(fileTyper.apply(file).toString())
            .checksumAlgorithm(ChecksumAlgorithm.SHA256)
            .key(bucketKey)
            .build();

    PutObjectResponse objectResponse = s3Client.putObject(request, RequestBody.fromFile(file));

    waitUntilObjectExists(bucketKey);
    return new FileHash(FileHashAlgorithm.SHA256, objectResponse.checksumSHA256());
  }

  private void waitUntilObjectExists(String bucketKey) {
    ResponseOrException<HeadObjectResponse> responseOrException =
        s3Client
            .waiter()
            .waitUntilObjectExists(
                HeadObjectRequest.builder().bucket(bucketName).key(bucketKey).build())
            .matched();
    responseOrException
        .exception()
        .ifPresent(
            throwable -> {
              throw new RuntimeException(throwable);
            });
  }

  public InputStream download(String bucketKey) {
    GetObjectRequest objectRequest =
        GetObjectRequest.builder().bucket(bucketName).key(bucketKey).build();
    return s3Client.getObjectAsBytes(objectRequest).asInputStream();
  }

  public URL preSign(String bucketKey, Duration expiration) {
    GetObjectRequest getObjectRequest =
        GetObjectRequest.builder().bucket(bucketName).key(bucketKey).build();
    PresignedGetObjectRequest preSignedRequest =
        preSigner.presignGetObject(
            GetObjectPresignRequest.builder()
                .signatureDuration(expiration)
                .getObjectRequest(getObjectRequest)
                .build());
    return preSignedRequest.url();
  }
}
