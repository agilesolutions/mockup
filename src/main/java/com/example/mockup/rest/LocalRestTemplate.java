package com.example.mockup.rest;

import com.example.mockup.payloads.ErrorResponse;
import com.example.mockup.payloads.SuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalRestTemplate extends RestTemplate {

    private ConcurrentHashMap<String, String> payloads = new ConcurrentHashMap();

    @Override
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return super.exchange(url, method, requestEntity, responseType, uriVariables);
    }


    @Override
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {

        // isolate the path parameter to identifying the Fenergo identifier
        String id = url.split("/")[3];

        // If match, return its payload over REST
        if (payloads.contains(id)) {

            try {

                switch (new ObjectMapper().readValue(payloads.get(id), Object.class).getClass().getSimpleName()) {
                    case "SuccessResponse":
                        ResponseEntity.status(HttpStatus.OK).body(payloads.get(id));
                    case "ErrorResponse":
                        throw new HttpClientErrorException(HttpStatus.CONFLICT, "LEM already exists", payloads.get(id).getBytes(), null);
                    default:
                        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad response format, please upload valid response!", "Bad response format, please upload valid response!".getBytes(), null);

                }

            } catch (JsonProcessingException e) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad response format, please upload valid response!", "Bad response format, please upload valid response!".getBytes(), null);
            }

        } else {
            // all other cases return an Error response, plus an HTTP conflict code
            ErrorResponse error = new ErrorResponse();
            error.setCode("500");
            error.setMessage("LEM already exists");
            try {
                throw new HttpClientErrorException(HttpStatus.CONFLICT, "LEM already exists", new ObjectMapper().writeValueAsString(error).getBytes(), null);
            } catch (JsonProcessingException e) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad response format, please upload valid response!", "Bad response format, please upload valid response!".getBytes(), null);
            }
        }

    }

    public String storePayload(String id, String payload) {

        if (payloads.contains(id)) {
            payloads.replace(id, payload);
        } else {
            payloads.put(id, payload);
        }
        return payload;
    }
}
