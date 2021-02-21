package com.example.mockup.controller;

import com.example.mockup.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RealController {

    @Autowired
    MockService mockService;

    @PostMapping("/api/core/{id}/store")
    public ResponseEntity getPayload(@RequestParam String id,
                                      @RequestBody String payload) {

        return ResponseEntity.accepted().body(mockService.getPayload(id, payload));

    }

}
