package com.example.mockup.configuration;

import com.example.mockup.model.Assessment;
import com.example.mockup.service.MockService;
import com.example.mockup.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
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
public class MockControllerTestConfiguration {

    private final static PrivateKey PRIVATE_KEY;
    private final static PublicKey PUBLIC_KEY;

    static {

        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PRIVATE_KEY = keyPair.getPrivate();
        PUBLIC_KEY = keyPair.getPublic();

    }

    @Bean
    MockService mockService() {
        return new MockService();
    }

    @Bean(name = "testRestTemplate")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(new RestTemplateInterceptor());
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }

    @Bean
    public CustomResponseCreator customResponseCreator() {
        return new CustomResponseCreator();
    }


    public class CustomResponseCreator implements ResponseCreator {

        @Override
        public ClientHttpResponse createResponse(ClientHttpRequest clientHttpRequest) throws IOException {

            String token = clientHttpRequest.getHeaders().get("Authorization").get(0);

            Claims claims = JwtUtils.verifyToken(token, PUBLIC_KEY);

            return new MockClientHttpResponse(objectMapper().writeValueAsBytes(new Assessment()), HttpStatus.CONFLICT);
        }
    }

    public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {


            String token = JwtUtils.createSignedJwt(PRIVATE_KEY);

            request.getHeaders().add("Authorization", "Bearer " + token);


            return execution.execute(request, body);

        }
    }

    @Bean
    public ObjectMapper objectMapper() {

        return new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    }

}
