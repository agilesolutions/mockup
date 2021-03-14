package com.example.mockup.controller;

import com.example.mockup.configuration.AssessmentControllerTestConfiguration;
import com.example.mockup.exceptions.ControllerAdvisor;
import com.example.mockup.model.Assessment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * read https://examples.javacodegeeks.com/enterprise-java/spring/using-mockrestserviceserver-test-rest-client/
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AssessmentControllerTestConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssessmentControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private MockRestServiceServer mockServer;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new AssessmentController())
                .setControllerAdvice(new ControllerAdvisor())
                .build();
    }

    @Test
    public void whenPostResponseThenFail() throws Exception {

        Assessment assessment = Assessment.builder().id(111).description("").status("").phase("").build();

        mockMvc.perform(post("/api/core/111/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assessment))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }

    @Test
    public void whenGetResponseThenSucceed() throws Exception {

        Assessment assessment = Assessment.builder().id(111).description("").status("").phase("").build();

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("/api/core/111/store")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(objectMapper.writeValueAsString(assessment)))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(Assessment.builder().build()))
                );


    }


}
