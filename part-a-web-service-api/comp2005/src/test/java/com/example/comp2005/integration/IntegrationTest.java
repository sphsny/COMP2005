package com.example.comp2005.integration;

import com.example.comp2005.model.Admission;
import com.example.comp2005.model.Allocation;
import com.example.comp2005.model.RoomAllocation;
import com.example.comp2005.service.ApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*

    Integration test using Bottom-Up Approach:
    HTTP request -> Controller -> Service -> ApiService (external API mock) -> response
    Each endpoint is tested in progressive order.
    Only the external API is mocked.
    Logging not implemented intentionally, but tests were given DisplayNames for improved readability.

    This test confirms that:
    - Endpoint mapping is correct
    - HTTP returns status code 200 (OK)
    - Responses are returned in valid JSON format
    - Controller and Service interaction works (Controller calls Service, Service calls external API)
    - Service logic works

*/

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // starts the SpringBoot application in test environment
@AutoConfigureMockMvc // simulate HTTP requests
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApiService apiService; // external API mock

    private static final Logger log = LoggerFactory.getLogger(IntegrationTest.class);

    // ------------------
    // ------- F1 -------
    // ------------------

    @Test
    @DisplayName("GET /api/F1/{patientId} returns rooms for patient") // test name
    void F1_returnsRooms() throws Exception {

        // mock GET /Admission/2
        when(apiService.getAdmissions()).thenReturn(List.of(
                new Admission(1, 2, "2020-11-28T16:45:00", null)
        ));

        // mock GET /RoomAllocation/1
        when(apiService.getRoomAllocations()).thenReturn(List.of(
                new RoomAllocation(1, 1, 3, "2020-11-28T16:45:00", null)
        ));

        // return roomID=3
        mockMvc.perform(get("/api/F1/2")) // calls the F1 service
                .andExpect(status().isOk()) // status code 200 confirmation
                .andExpect(jsonPath("$").isArray()) // check whether array for multiple rooms
                .andExpect(jsonPath("$[0]").value(3)); // compare value
    }

    @Test
    @DisplayName("GET /api/F1/{patientId} returns empty list")
    void F1_returnsEmpty() throws Exception {
        // mock empty returns
        when(apiService.getAdmissions()).thenReturn(List.of());
        when(apiService.getRoomAllocations()).thenReturn(List.of());

        mockMvc.perform(get("/api/F1/-999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty()); // confirm empty json
    }

    // ------------------
    // ------- F2 -------
    // ------------------

    @Test
    @DisplayName("GET /api/F2/{roomId} returns patients within last 7 days")
    void F2_returnsRecentPatients() throws Exception {
        LocalDateTime recent = LocalDateTime.now().minusDays(2); // set time as two days ago

        // mock GET /Allocations/3
        when(apiService.getAllocations()).thenReturn(List.of(
                new Allocation(1, 1, 10, 3, recent.toString(), null)
        ));

        // mock GET /Admissions/1
        when(apiService.getAdmissions()).thenReturn(List.of(
                new Admission(1, 2, recent.toString(), null)
        ));

        // mock endpoint for room 3, return patient 2
        mockMvc.perform(get("/api/F2/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(2));
    }

    @Test
    @DisplayName("GET /api/F2/{roomId} returns empty when no allocations within last week")
    void F2_returnsEmpty() throws Exception {
        LocalDateTime moreThan7DaysAgo = LocalDateTime.now().minusDays(10); // mock allocation from 10 days ago

        // mock GET /Allocations/3
        when(apiService.getAllocations()).thenReturn(List.of(
                new Allocation(1, 1, 10, 3, moreThan7DaysAgo.toString(), null)
        ));

        // mock GET /Admissions/1
        when(apiService.getAdmissions()).thenReturn(List.of(
                new Admission(1, 3, moreThan7DaysAgo.toString(), null)
        ));

        // perform call for room 3, returns empty
        mockMvc.perform(get("/api/F2/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // ------------------
    // ------- F3 -------
    // ------------------

    @Test
    @DisplayName("GET /api/F3 returns least used room")
    void F3_returnsLeastUsedRoom() throws Exception {
        // room 3 used twice, room 5 used once
        // mock GET /RoomAllocations
        when(apiService.getRoomAllocations()).thenReturn(List.of(
                new RoomAllocation(1, 1, 3, "2020-01-01T00:00:00", null),
                new RoomAllocation(2, 2, 3, "2020-01-01T00:00:00", null),
                new RoomAllocation(3, 3, 5, "2020-01-01T00:00:00", null)
        ));

        // mock endpoint call, expected value of room: 5
        mockMvc.perform(get("/api/F3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(5));
    }

    @Test
    @DisplayName("GET /api/F3 returns empty if no rooms used")
    void F3_returnsEmpty() throws Exception {
        // mock GET /RoomAllocations returns empty list
        when(apiService.getRoomAllocations()).thenReturn(List.of());

        mockMvc.perform(get("/api/F3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // ------------------
    // ------- F4 -------
    // ------------------

    @Test
    @DisplayName("GET /api/F4 returns overloaded staff")
    void F4_returnsOverloadedStaff() throws Exception {
        String now = LocalDateTime.now().minusHours(1).toString(); // start time mock

        // mock GET /Allocations -> staff 2 has 3 concurrent admissions
        when(apiService.getAllocations()).thenReturn(List.of(
                new Allocation(1, 1, 2, 2, now, null),
                new Allocation(2, 2, 2, 3, now, null),
                new Allocation(3, 3, 2, 1, now, null)
        ));

        mockMvc.perform(get("/api/F4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(2));
    }

    @Test
    @DisplayName("GET /api/F4 returns empty if no overloaded staff")
    void F4_returnsEmpty() throws Exception {
        // mock GET /Allocations returns empty list
        when(apiService.getAllocations()).thenReturn(List.of());

        mockMvc.perform(get("/api/F4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}