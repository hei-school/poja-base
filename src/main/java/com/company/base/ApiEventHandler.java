package com.company.base;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.model.HttpApiV2ProxyRequest;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@PojaGenerated
public class ApiEventHandler implements RequestStreamHandler {

  private static final SpringBootLambdaContainerHandler<HttpApiV2ProxyRequest, AwsProxyResponse> handler;

  static {
    SpringBootLambdaContainerHandler<HttpApiV2ProxyRequest, AwsProxyResponse> tempHandler;
    try {
      tempHandler = SpringBootLambdaContainerHandler.getHttpApiV2ProxyHandler(PojaApplication.class);
    } catch (ContainerInitializationException e) {
      System.err.println("Error initializing Spring Boot Application: " + e.getMessage());
      e.printStackTrace();
      tempHandler = null;
    }
    handler = tempHandler;
  }

  @Override
  public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
    if (handler == null) {
      throw new IllegalStateException("Handler was not initialized. Check initialization logs for details.");
    }

    try {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      input.transferTo(buffer);
      String requestPayload = buffer.toString(StandardCharsets.UTF_8);
      System.out.println("Incoming request: " + requestPayload);

      InputStream newInput = new ByteArrayInputStream(requestPayload.getBytes(StandardCharsets.UTF_8));

      handler.proxyStream(newInput, output, context);

    } catch (Exception e) {
      System.err.println("Error processing request: " + e.getMessage());
      e.printStackTrace();

      AwsProxyResponse errorResponse = new AwsProxyResponse();
      errorResponse.setStatusCode(500);
      errorResponse.setBody("{\"message\":\"Internal Server Error\"}");
      output.write(errorResponse.getBody().getBytes(StandardCharsets.UTF_8));
    }
  }
}
