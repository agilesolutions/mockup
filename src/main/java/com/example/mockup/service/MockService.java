package com.example.mockup.service;

import com.example.mockup.model.Assessment;
import com.example.mockup.rest.LocalRestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@Service
public class MockService {

    @Qualifier("testRestTemplate")
    @Autowired
    private RestTemplate restTemplate;


    // try it out on HTTP responses on Mock rest controller
    // https://docs.spring.io/spring-android/docs/current/reference/html/rest-template.html
    public Assessment saveAssessment(Assessment assessment) throws JsonProcessingException {

        // Set the Content-Type header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<String>(new ObjectMapper().writeValueAsString(assessment), requestHeaders);

        ResponseEntity<Assessment> responseEntity = restTemplate.exchange("/api/assessment/save", HttpMethod.POST, requestEntity, Assessment.class);

        return responseEntity.getBody();
    }




}
