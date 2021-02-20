package com.example.mockup.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;

public class LocalRestTemplate extends RestTemplate {

    private ConcurrentHashMap<String, String> payloads = new ConcurrentHashMap();

    @Override
    public <T> ResponseEntity exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {

        String id = url.split("/")[3];

        return ResponseEntity.accepted().body(payloads.get(""));

        //return super.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    public void storePayload(String id, String payload) {

        if (payloads.contains(id)) {
            payloads.replace(id, payload);
        } else {
            payloads.put(id, payload);
        }
    }
}
