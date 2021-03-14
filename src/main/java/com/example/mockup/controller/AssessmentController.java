package com.example.mockup.controller;

import com.example.mockup.model.Assessment;
import com.example.mockup.validators.AssessmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class AssessmentController {

    @Autowired
    private AssessmentValidator assessmentValidator;

    @InitBinder("productCreateRequest")
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(assessmentValidator);
    }


    @PostMapping(path = "/api/core/store", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Assessment> storeAssessment(
                                     @RequestBody @Valid Assessment assessment) {

        return ResponseEntity.accepted().body(assessment);

    }
}
