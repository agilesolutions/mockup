package com.example.mockup.controller;


import com.example.mockup.model.Assessment;
import com.example.mockup.service.AssessmentService;
import com.example.mockup.validators.AssessmentValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
public class AssessmentController {

    AssessmentService assessmentService;


    @InitBinder("assessment")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new AssessmentValidator());
    }


    @RequestMapping(value = { "/api/v1/core/store" }, method = { RequestMethod.POST })
    public ResponseEntity<Assessment> storeAssessment(
                                     @RequestBody @Valid Assessment assessment) {

        return ResponseEntity.accepted().body(assessmentService.saveAssessment(assessment));

    }
}
