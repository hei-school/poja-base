package com.company.base.file;

import java.net.URI;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class BucketConf {

  @Getter private final String bucketName;
  @Getter private final S3Client s3Client = S3Client.create();
  @Getter private final S3Presigner s3Presigner;

  @SneakyThrows
  public BucketConf(
      @Value("${aws.region}") String region,
      @Value("${aws.endpoint}") String awsEndpoint,
      @Value("${aws.s3.bucket}") String bucketName) {
    this.bucketName = bucketName;
    this.s3Presigner =
        S3Presigner.builder()
            .endpointOverride(new URI(awsEndpoint))
            .region(Region.of(region))
            .build();
  }
}
