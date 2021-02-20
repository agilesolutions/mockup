package com.example.mockup.rest;

import com.example.mockup.payloads.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;

public class LocalRestTemplate extends RestTemplate {

    private ConcurrentHashMap<String, String> payloads = new ConcurrentHashMap();

    @Override
    public <T> ResponseEntity exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {

        String id = url.split("/")[3];

        if (payloads.contains(id)) {
            return ResponseEntity.accepted().body(payloads.get(""));
        } else {
            ErrorResponse error = new ErrorResponse();
            error.setCode("500");
            error.setMessage("already exists");
            try {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ObjectMapper().writeValueAsString(error));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }


        //return super.exchange(url, method, requestEntity, responseType, uriVariables);
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
