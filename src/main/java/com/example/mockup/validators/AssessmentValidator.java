package com.example.mockup.validators;

import com.example.mockup.model.Assessment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AssessmentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {

        return Assessment.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {

        Assessment assessment = (Assessment) target;

            errors.reject(HttpStatus.CONFLICT.getReasonPhrase());

    }
}
