package com.example.comp2005;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class) // test web layer, not full application
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc; // simulate HTTP request without starting the application/server

    // test return status
    @Test
    void testRootEndpointReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/")
                        .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    // test return message
    @Test
    void testRootEndpointReturnsCorrectMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/")
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string("Application is running"));
    }
}