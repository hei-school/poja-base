package com.company.base;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

@PojaGenerated
public class ApiEventHandler implements RequestStreamHandler {
	private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
	private static final ObjectMapper om = new ObjectMapper().findAndRegisterModules();
	public static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

	static {
		try {
			handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(PojaApplication.class);
		} catch (ContainerInitializationException e) {
			throw new RuntimeException("Initialization of Spring Boot Application failed", e);
		}
	}

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context)
		throws IOException {
		AwsProxyRequest awsProxyRequest = om.readValue(input, new TypeReference<>() {
		});
		AwsProxyResponse proxyResponse;
		if (awsProxyRequest.isBase64Encoded()) {
			var decodeAsBytes = BASE64_DECODER.decode(awsProxyRequest.getBody());
			awsProxyRequest.setBody(new String(decodeAsBytes));
			awsProxyRequest.setIsBase64Encoded(false);

			var response = handler.proxy(awsProxyRequest, context);
			om.writeValue(output, response);
			return;
		}
		var response = handler.proxy(awsProxyRequest, context);
		om.writeValue(output, response);
	}
}
