package com.example.mockup.service;

import com.example.mockup.rest.LocalRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;


@Service
public class MockService {

    @Qualifier("LocalRestController")
    @Autowired
    private LocalRestTemplate restController;


    public String storePayload(String id, String payload) {

        return restController.storePayload(id, payload);
    }



}
