package com.example.comp2005.unit;

import com.example.comp2005.controller.F4Controller;
import com.example.comp2005.service.F4Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(F4Controller.class)
class F4ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private F4Service f4Service;

    @Test
    void shouldReturn200WithOverloadedStaff() throws Exception {
        // return two overloaded staff members
        when(f4Service.getOverloadedStaff()).thenReturn(List.of(4, 6));

        mockMvc.perform(get("/api/F4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(4))
                .andExpect(jsonPath("$[1]").value(6));
    }

    @Test
    void shouldReturn200WithEmptyListIfNoOverloadedStaff() throws Exception {
        // return empty list
        when(f4Service.getOverloadedStaff()).thenReturn(List.of());

        mockMvc.perform(get("/api/F4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}