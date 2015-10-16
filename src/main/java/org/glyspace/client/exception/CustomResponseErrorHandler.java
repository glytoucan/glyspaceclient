package org.glyspace.client.exception;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

public class CustomResponseErrorHandler implements ResponseErrorHandler {
	
	private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

	public boolean hasError(ClientHttpResponse response) throws IOException {
		return errorHandler.hasError(response);
	}

	public void handleError(ClientHttpResponse response) throws IOException {
		String body = "";
		if (response.getStatusCode() == HttpStatus.UNAUTHORIZED ) {
			// do not attempt to get the body
		} else {
			body = IOUtils.toString(response.getBody());
		}
	    CustomClientException exception = new CustomClientException(response.getStatusCode(), body, body);
	    throw exception;
	}
}