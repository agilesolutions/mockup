package com.example.mockup.service;


import com.example.mockup.model.Assessment;
import org.springframework.stereotype.Service;

@Service
public class AssessmentService {

    public Assessment saveAssessment(Assessment assessment) {

        assessment.setId(999L);

        return assessment;
    }
}
