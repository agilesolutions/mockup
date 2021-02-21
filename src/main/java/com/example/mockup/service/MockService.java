package com.example.mockup.service;

import com.example.mockup.rest.LocalRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@Service
public class MockService {

    @Qualifier("LocalRestController")
    @Autowired
    private LocalRestTemplate restTemplate;


    // store HTTP responses on Mock rest controller
    public String storePayload(String id, String payload) {

        return restTemplate.storePayload(id, payload);
    }

    // try it out on HTTP responses on Mock rest controller
    // https://docs.spring.io/spring-android/docs/current/reference/html/rest-template.html
    public String getPayload(String id, String payload) {

        // Set the Content-Type header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application","json"));
        HttpEntity<String> requestEntity = new HttpEntity<String>("message", requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange("url", HttpMethod.POST, requestEntity, String.class);


        return responseEntity.getBody();
    }




}
