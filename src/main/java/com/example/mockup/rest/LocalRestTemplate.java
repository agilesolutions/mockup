package com.example.mockup.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;

public class LocalRestTemplate extends RestTemplate {

    private ConcurrentHashMap<String, String> payload = new ConcurrentHashMap();

    @Override
    public <T> ResponseEntity exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {


        return ResponseEntity.accepted().body(payload.get(""));

        //return super.exchange(url, method, requestEntity, responseType, uriVariables);
    }
}
