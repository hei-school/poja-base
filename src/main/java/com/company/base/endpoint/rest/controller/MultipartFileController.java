package com.company.base.endpoint.rest.controller;

import static com.company.base.endpoint.rest.controller.health.PingController.KO;
import static com.company.base.endpoint.rest.controller.health.PingController.OK;
import static java.io.File.createTempFile;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class MultipartFileController {
	//shows we can handle multipart files
	@PutMapping(value = "/multipart-upload", consumes = MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadMultipart(
		@RequestHeader
		HttpHeaders httpHeaders,
		@RequestPart(name = "payload") MultipartFile multipartFile
	) {
		log.info("headers {}", httpHeaders);
		//if (true) {
		//MultipartFile multipartFile = (MultipartFile) multipart;
		log.info("multipart file details original filename = {} sizeInBytes = {}", multipartFile.getOriginalFilename(), multipartFile.getSize());
		try {
			var fileToUpload = createTempFile(multipartFile.getOriginalFilename(), null);
			multipartFile.transferTo(fileToUpload);
			log.info("saved  file details original filename = {} sizeInBytes = {}", fileToUpload.getName(), fileToUpload.length());
			return OK;
		} catch (IOException e) {
			log.info("exception");
			return KO;
		}
		//} else {
//			log.info("multipart {}", multipart);
//			return KO;
//		}
	}
}
