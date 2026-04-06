package com.example.comp2005.unit;

import com.example.comp2005.model.Admission;
import com.example.comp2005.model.Allocation;
import com.example.comp2005.service.ApiService;
import com.example.comp2005.service.F1Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// JUNIT 5 Syntax
// @BeforeAll @AfterAll @BeforeEach @AfterEach @Test
// @AssumeFalse @AssumeTrue @AssumeThat

// Test Cases:
// return rooms for patient X
// corner case: patient has been to a room twice -> return room only once in the list
// edge case: patient has not been to a room yet -> null is returned

// Business logic:
// get patientID -> get admissionID -> get roomID

@ExtendWith(MockitoExtension.class)
class F1ServiceTest {

    @Mock
    private ApiService apiService;

    @InjectMocks
    private F1Service f1Service;

    // first A: assert data so only business logic is tested
    @BeforeEach
    void setUp() {
        List<Admission> fakeAdmissions = List.of(
                new Admission(1, 2, "2020-11-28T16:45:00", "2020-11-28T23:56:00"),
                new Admission(2, 1, "2020-12-07T22:14:00", null),
                new Admission(3, 2, "2021-09-23T21:50:00", "2021-09-27T09:56:00")
        );

        List<Allocation> fakeAllocations = List.of(
                new Allocation(1, 1, 4, 3, "2020-11-28T16:45:00", "2020-11-28T23:56:00"),
                new Allocation(2, 3, 4, 5, "2021-09-23T21:50:00", "2021-09-24T09:50:00"),
                new Allocation(3, 2, 6, 7, "2020-12-07T22:14:00", "2020-12-08T20:00:00")
        );

        // return fake data on api call
        when(apiService.getAdmissions()).thenReturn(fakeAdmissions);
        when(apiService.getAllocations()).thenReturn(fakeAllocations);
    }

    // second and third A: act and assert

    // -- return rooms for patient X --
    @Test
    void shouldReturnRoomsForPatient() {
        // fake patient 2 -> admissions 1 and 3 -> room 3 and 5
        List<Integer> result = f1Service.getRoomsByPatient(2);
        assertEquals(List.of(3, 5), result);
    }

    // -- corner case: patient is in the same room twice --
    @Test
    void shouldReturnDistinctRoomsOnly() {
        // patient in same room twice should only appear once
        when(apiService.getAllocations()).thenReturn(List.of(
                new Allocation(1, 1, 4, 3, "2020-11-28T16:45:00", "2020-11-28T23:56:00"),
                new Allocation(2, 1, 4, 3, "2020-11-29T16:45:00", "2020-11-29T23:56:00")
        ));

        List<Integer> result = f1Service.getRoomsByPatient(2);
        assertEquals(1, result.size());
    }

    // -- edge case: patient has not been allocated to room yet --
    @Test
    void shouldReturnEmptyListIfPatientNotFound() {
        List<Integer> result = f1Service.getRoomsByPatient(10000);
        assertTrue(result.isEmpty());
    }
}