package com.example.mockup.controller;

import com.example.mockup.model.Assessment;
import com.example.mockup.validators.AssessmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


public class AssessmentController {

    @Autowired
    private AssessmentValidator assessmentValidator;

    @InitBinder("productCreateRequest")
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(assessmentValidator);
    }

    @PostMapping("/api/core/{id}/store")
    public ResponseEntity<Assessment> getPayload(@RequestParam String id,
                                     @RequestBody @Valid Assessment assessment) {

        return ResponseEntity.accepted().body(assessment);

    }
}
