package com.example.comp2005.system;

// test whole real working application with real external api

// for now (before service implementation):
// test that external api returns something and is reachable

// after service and controller implementations:
// start the full Spring application on localhost:8080
// call each endpoint

// data flow:
// localhost endpoint call -> controller -> service -> apiservice -> return

import com.example.comp2005.service.ApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SystemTest {

    @Autowired
    private ApiService apiService; // use actual external API

    // call each real endpoint ensuring API is reachable

    @Test
    void shouldFetchRealAdmissions() {
        var result = apiService.getAdmissions();
        assertNotNull(result); // ensure result is not null
        assertFalse(result.isEmpty()); // test fails if result is empty
    }

    @Test
    void shouldFetchRealPatients() {
        var result = apiService.getPatients();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFetchRealAllocations() {
        var result = apiService.getAllocations();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFetchRealEmployees() {
        var result = apiService.getEmployees();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFetchRoomAllocations() {
        var result = apiService.getRoomAllocations();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
