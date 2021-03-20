package com.example.mockup.controller;

import com.example.mockup.configuration.AssessmentControllerTestConfiguration;
import com.example.mockup.model.Assessment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;


/**
 * https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-1-spring-mockmvc-example-in-standalone-mode
 *
 * https://www.baeldung.com/restclienttest-in-spring-boot
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AssessmentControllerTestConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MockControllerTest {

    @Autowired
    @Qualifier("testRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AssessmentControllerTestConfiguration.CustomResponseCreator customResponseCreator;

    private MockRestServiceServer mockServer;

    @BeforeAll
    public void init() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void whenGetResponseThenSucceed() throws Exception {

        Assessment assessment = new Assessment();
        assessment.setId(111L);

        mockServer.expect(requestTo("/api/core/111/store"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(objectMapper.writeValueAsString(assessment)))
                .andRespond(r -> {

                    String token = r.getHeaders().get("Authorization").get(0);

                    return new MockClientHttpResponse(objectMapper.writeValueAsBytes(new Assessment()), HttpStatus.CONFLICT);

                });
//                        .andRespond(withStatus(HttpStatus.OK)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .body(objectMapper.writeValueAsString(Assessment.builder().build())));

        mockServer.verify();

    }


}
