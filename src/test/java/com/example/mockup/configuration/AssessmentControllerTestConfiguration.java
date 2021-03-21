package com.example.mockup.configuration;

import com.example.mockup.controller.AssessmentController;
import com.example.mockup.exceptions.ControllerAdvisor;
import com.example.mockup.service.AssessmentService;
import com.example.mockup.validators.AssessmentValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;


@Configuration
public class AssessmentControllerTestConfiguration {


    @Bean
    public AssessmentController assessmentController() {
        return new AssessmentController(assessmentService());
    }

    @Bean
    public AssessmentService assessmentService() {
        return Mockito.mock(AssessmentService.class);
    }


    @Bean
    public AssessmentValidator assessmentValidator() {
        return new AssessmentValidator();
    }

    @Bean
    ControllerAdvisor controllerAdvisor() {
        return new ControllerAdvisor();
    }

    @Bean
    public ObjectMapper objectMapper() {

        return new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    }

    @Bean
    public LocalValidatorFactoryBean defaultValidator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
