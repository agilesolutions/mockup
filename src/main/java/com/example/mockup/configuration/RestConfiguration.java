package com.example.mockup.configuration;


import com.example.mockup.rest.LocalRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RestConfiguration {

    @Autowired
    private Environment environment;

    @Bean(name="localizedRestTemplate")
    public RestTemplate getLocalizedTemplate (){

        if(Arrays.stream(this.environment.getActiveProfiles()).sequential().anyMatch(p -> p.equalsIgnoreCase("local"))) {
            return new LocalRestTemplate();
        } else {
            return new RestTemplate();
        }

    }

}
