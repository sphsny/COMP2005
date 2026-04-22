package com.example.comp2005.unit;

import com.example.comp2005.controller.F3Controller;
import com.example.comp2005.service.F3Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(F3Controller.class)
class F3ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private F3Service f3Service;

    @Test
    void shouldReturn200WithLeastUsedRoom() throws Exception {
        // mock single least used room
        when(f3Service.getLeastUsedRoom()).thenReturn(List.of(3));

        mockMvc.perform(get("/api/F3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3));
    }

    @Test
    void shouldReturn200WithEmptyListIfNoRoomUsed() throws Exception {
        // mock empty list (no room ever used)
        when(f3Service.getLeastUsedRoom()).thenReturn(List.of());

        mockMvc.perform(get("/api/F3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}