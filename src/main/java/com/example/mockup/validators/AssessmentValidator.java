package com.example.mockup.validators;

import com.example.mockup.model.Assessment;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validator;

/**
 * https://stackoverflow.com/questions/14817281/spring-validation-annotations-being-ignored-when-using-custom-validator
 * https://stackoverflow.com/questions/28702809/how-to-manually-trigger-spring-validation
 *
 * must read
 *
 * https://stackoverflow.com/questions/39001106/implementing-custom-validation-logic-for-a-spring-boot-endpoint-using-a-combinat
 * https://github.com/pavelfomin/spring-boot-rest-example/tree/feature/custom-validator
 */
@Component
public class AssessmentValidator extends SpringValidatorAdapter {


    public AssessmentValidator(Validator targetValidator) {
        super(targetValidator);
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return Assessment.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {

        // check JSR-303 Constraints
        super.validate(target, errors);

        Assessment assessment = (Assessment) target;

        errors.reject(HttpStatus.CONFLICT.getReasonPhrase());

    }


}
