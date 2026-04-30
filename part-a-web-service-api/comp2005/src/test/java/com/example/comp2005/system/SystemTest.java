package com.example.comp2005.system;

// test whole real working application with real external api

// for now (before service implementation):
// test that external api returns something and is reachable

// start the full Spring application on localhost:8080
// call each endpoint

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "server.port=8080" // boot springboot app on port 8080
        })

// entire SystemTest fails if one test fails (non 200 response/non JSON body)

public class SystemTest {

    private static final Logger log = LoggerFactory.getLogger(SystemTest.class); // use for logging

    @Test
    void PerformanceTest() {
        // define endpoints
        String[] endpoints = {
                "F1/1",
                "F2/1",
                "F3",
                "F4"
        };

        // loop through each endpoint
        for (String endpoint : endpoints) {
            // our spring boot application's API address
            String baseUrl = "http://localhost:8080/api/";
            String url = baseUrl + endpoint;

            log.info("Testing performance for: {}", url);
            long start = System.currentTimeMillis(); // start time
            ResponseEntity<String> response =
                    new RestTemplate().getForEntity(url, String.class); // http response
            long duration = System.currentTimeMillis() - start; // duration

            // log responses for each endpoint
            log.info("Endpoint: {}", endpoint);
            log.info("Status: {}", response.getStatusCode());
            log.info("Response: {}", response.getBody());
            log.info("Response time: {} ms", duration);

            assertEquals(HttpStatus.OK, response.getStatusCode()); // endpoint reached
            assertNotNull(response.getBody()); // body returned
        }
    }
}
