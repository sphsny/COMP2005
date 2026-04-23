package com.example.comp2005.unit;

import com.example.comp2005.controller.F2Controller;
import com.example.comp2005.service.F2Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(F2Controller.class)
class F2ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private F2Service f2Service;

    @Test
    void shouldReturn200WithListOfPatients() throws Exception {
        // mock the service business logic
        when(f2Service.getPatientsInRoomWithinLast7Days(3)).thenReturn(List.of(2, 5));
        // mock calling the endpoint
        mockMvc.perform(get("/api/F2/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(2)) // first index value == 3
                .andExpect(jsonPath("$[1]").value(5)); // second index value == 5
    }

    @Test
    void shouldReturn200WithEmptyListIfNoPatientsInLast7Days() throws Exception {
        when(f2Service.getPatientsInRoomWithinLast7Days(3)).thenReturn(List.of());

        mockMvc.perform(get("/api/F2/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
