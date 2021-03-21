package com.example.mockup.controller;

import com.example.mockup.configuration.AssessmentControllerTestConfiguration;
import com.example.mockup.configuration.MockControllerTestConfiguration;
import com.example.mockup.model.Assessment;
import com.example.mockup.service.MockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


/**
 * https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-1-spring-mockmvc-example-in-standalone-mode
 * <p>
 * https://www.baeldung.com/restclienttest-in-spring-boot
 * https://examples.javacodegeeks.com/enterprise-java/spring/using-mockrestserviceserver-test-rest-client/
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MockControllerTestConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MockControllerTest {

    @Autowired
    @Qualifier("testRestTemplate")
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockService mockService;

    private MockRestServiceServer mockServer;

    @BeforeAll
    public void init() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void whenGetResponseThenSucceed() throws Exception {

        Assessment assessment = new Assessment();
        assessment.setId(111L);

        mockServer.expect(requestTo("/api/assessment/save"))
                .andExpect(content().json(objectMapper.writeValueAsString(assessment)))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andRespond(withSuccess(objectMapper.writeValueAsString(assessment), MediaType.APPLICATION_JSON));

        assessment = mockService.saveAssessment(Assessment.builder().id(111L).build());

        //String result = service.addComment("cute puppy");
        //System.out.println("testAddComment: " + result);

        mockServer.verify();
        assertEquals(111L, assessment.getId());

/*
        mockServer.expect(once(),requestTo("http://localhost/api/core/store"))
                .andExpect(clientHttpRequest -> {
                    clientHttpRequest.getBody().
                })
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(assessment)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andRespond(r -> {

                    String token = r.getHeaders().get("Authorization").get(0);

                    return new MockClientHttpResponse(objectMapper.writeValueAsBytes(new Assessment()), HttpStatus.CONFLICT);

                });
                */

//                        .andRespond(withStatus(HttpStatus.OK)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .body(objectMapper.writeValueAsString(Assessment.builder().build())));

        mockServer.verify();

    }


}
