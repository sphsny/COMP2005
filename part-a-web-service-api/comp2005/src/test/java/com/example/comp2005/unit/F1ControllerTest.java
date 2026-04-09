package com.example.comp2005.unit;

import com.example.comp2005.controller.F1Controller;
import com.example.comp2005.service.F1Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(F1Controller.class)
class F1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private F1Service f1Service;

    @Test
    void shouldReturn200WithListOfRooms() throws Exception {
        // mock the service business logic
        when(f1Service.getRoomsByPatient(2)).thenReturn(List.of(3, 5));
        // mock calling the endpoint
        mockMvc.perform(get("/api/F1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(3)) // first index value == 3
                .andExpect(jsonPath("$[1]").value(5)); // second index value == 5
    }

    @Test
    void shouldReturn200WithEmptyListIfPatientNotFound() throws Exception {
        // mock non existing patient -> empty list
        when(f1Service.getRoomsByPatient(10000)).thenReturn(List.of());

        mockMvc.perform(get("/api/F1/10000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
