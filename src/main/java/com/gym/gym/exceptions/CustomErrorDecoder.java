package com.gym.gym.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus.is5xxServerError()) {
            return new RestApiServerException("Service is unavailable");
        } else if (responseStatus.is4xxClientError()) {
            return new RestApiClientException("Client error");
        } else {
            return new Exception("Generic exception");
        }
    }
}
