package com.example.mockup.controller;

import com.example.mockup.configuration.AssessmentControllerTestConfiguration;
import com.example.mockup.exceptions.ControllerAdvisor;
import com.example.mockup.model.Assessment;
import com.example.mockup.validators.AssessmentValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-1-spring-mockmvc-example-in-standalone-mode
 *
 * https://www.baeldung.com/restclienttest-in-spring-boot
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AssessmentControllerTestConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssessmentControllerTest {

    @Autowired
    AssessmentValidator assessmentValidator;

    @Autowired
    AssessmentController assessmentController;

    @Autowired
    ControllerAdvisor controllerAdvisor;

    private MockMvc mockMvc;

    @BeforeAll
    public void init() {

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(assessmentController)
                .setControllerAdvice(controllerAdvisor)
                .setValidator(assessmentValidator)
                //.alwaysDo(print())
                .build();
    }

    @Test
    public void whenPostResponseThenFail() throws Exception {

        Assessment assessment = Assessment.builder().id(111).description("dd").status("ss").phase("pp").build();

        mockMvc.perform(post("/api/core/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(assessment)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", "/api/account/12345"))
                .andExpect(jsonPath("$.accountId").value("12345"))
                .andExpect(jsonPath("$.accountType").value("SAVINGS"))
                .andExpect(jsonPath("$.balance").value(5000));



    }



}
