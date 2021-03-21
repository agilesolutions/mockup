package com.example.mockup.validators;


import com.example.mockup.model.Assessment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * https://stackoverflow.com/questions/14817281/spring-validation-annotations-being-ignored-when-using-custom-validator
 * https://stackoverflow.com/questions/28702809/how-to-manually-trigger-spring-validation
 * <p>
 * must read
 * <p>
 * https://stackoverflow.com/questions/39001106/implementing-custom-validation-logic-for-a-spring-boot-endpoint-using-a-combinat
 * https://github.com/pavelfomin/spring-boot-rest-example/tree/feature/custom-validator
 */
@Component
public class AssessmentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Assessment.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "field.required");
    }

}
