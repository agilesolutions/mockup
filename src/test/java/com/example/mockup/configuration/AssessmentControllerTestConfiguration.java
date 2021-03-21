package com.example.mockup.configuration;

import com.example.mockup.controller.AssessmentController;
import com.example.mockup.exceptions.ControllerAdvisor;
import com.example.mockup.model.Assessment;
import com.example.mockup.service.AssessmentService;
import com.example.mockup.utils.JwtUtils;
import com.example.mockup.validators.AssessmentValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.web.client.ResponseCreator;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.*;
import java.util.List;


@Configuration
public class AssessmentControllerTestConfiguration {



    @Bean
    public AssessmentController assessmentController() {
        return new AssessmentController();
    }

    @Bean
    public AssessmentService assessmentService() {
        return Mockito.mock(AssessmentService.class);
    }

    @Bean
    ControllerAdvisor controllerAdvisor() {
        return new ControllerAdvisor();
    }

    @Bean
    public AssessmentValidator assessmentValidator() {
        return new AssessmentValidator();
    }

    @Bean
    public ObjectMapper objectMapper() {

        return new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    }

}
