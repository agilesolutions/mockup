package com.example.mockup.controller;

import com.example.mockup.configuration.AssessmentControllerTestConfiguration;
import com.example.mockup.exceptions.ControllerAdvisor;

import com.example.mockup.model.Assessment;
import com.example.mockup.service.AssessmentService;
import com.example.mockup.validators.AssessmentValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    AssessmentController assessmentController;

    @Autowired
    AssessmentService assessmentService;

    @Autowired
    ControllerAdvisor controllerAdvisor;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeAll
    public void init() {

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(assessmentController)
                .setControllerAdvice(controllerAdvisor)
                .setValidator(new AssessmentValidator())
                //.alwaysDo(print())
                .build();
    }

    @Test
    public void whenPostResponseThenFail() throws Exception {

        Assessment assessment = new Assessment();

        assessment.setId(111L);

        when(assessmentService.saveAssessment(assessment)).thenReturn(assessment);

        mockMvc.perform(post("/api/v1/core/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assessment))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                /*
                *  Body = {"fieldErrors":[{"field":"phase","code":"NotBlank","rejectedValue":null},{"field":"description","code":"NotBlank","rejectedValue":null},{"field":"status","code":"NotBlank","rejectedValue":null}],"globalErrors":[]}
                 */
                .andExpect(jsonPath("$.fieldErrors[0].field").value("status"));



        /*
                ArgumentCaptor<Assessment> argumentCaptor = ArgumentCaptor.forClass(Assessment.class);
        verify(assessmentService).saveAssessment(argumentCaptor.capture());
        Assessment capturedArgument = argumentCaptor.<Assessment> getValue();
        Assertions.assertEquals(999,argumentCaptor.getValue().getId());
*/


    }


    @Test
    public void whenPostResponseThenSucceed() throws Exception {

        Assessment assessment = Assessment.builder()
                .id(111L)
                .phase("phase1")
                .status("status1")
                .description("descripion1")
                .build();

        when(assessmentService.saveAssessment(assessment)).thenReturn(assessment);

        mockMvc.perform(post("/api/v1/core/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assessment))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("111"));



        ArgumentCaptor<Assessment> argumentCaptor = ArgumentCaptor.forClass(Assessment.class);
        verify(assessmentService).saveAssessment(argumentCaptor.capture());
        Assessment capturedArgument = argumentCaptor.<Assessment> getValue();
        Assertions.assertEquals(111,argumentCaptor.getValue().getId());



    }


}
