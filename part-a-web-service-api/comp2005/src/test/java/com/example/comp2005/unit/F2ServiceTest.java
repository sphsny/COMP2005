package com.example.comp2005.unit;

import com.example.comp2005.model.Admission;
import com.example.comp2005.model.Allocation;
import com.example.comp2005.service.ApiService;
import com.example.comp2005.service.F2Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class F2ServiceTest {

    @Mock
    private ApiService apiService;

    @InjectMocks
    private F2Service f2Service;

    // cases vary so no @BeforeEach set up here

    // normal case: return list of patients that have been in room X within the last 7 days
    @Test
    void shouldReturnPatientInRoomWithinLast7Days() {
        // create mock time by taking local current time and deducting 3 days
        LocalDateTime recentDate = LocalDateTime.now().minusDays(3);

        // mock api service, retrieve room id from allocation, patient id from admission
        when(apiService.getAllocations()).thenReturn(List.of(
                new Allocation(1, 1, 4, 3, recentDate.toString(), null)
        ));
        when(apiService.getAdmissions()).thenReturn(List.of(
                new Admission(1, 2, recentDate.toString(), null)
        ));

        List<Integer> result = f2Service.getPatientsInRoomWithinLast7Days(3); // pass room id

        assertTrue(result.contains(2)); // patient with id 2 should be in list
    }

    // confirm allocations older than 7 days are not returned in list
    @Test
    void shouldNotReturnPatientOlderThan7Days() {
        LocalDateTime oldDate = LocalDateTime.now().minusDays(10); // 10 days ago

        when(apiService.getAllocations()).thenReturn(List.of(
                new Allocation(1, 1, 4, 3, oldDate.toString(), oldDate.plusDays(1).toString())
        ));
        when(apiService.getAdmissions()).thenReturn(List.of(
                new Admission(1, 2, oldDate.toString(), oldDate.plusDays(1).toString())
        ));

        List<Integer> result = f2Service.getPatientsInRoomWithinLast7Days(3);

        assertTrue(result.isEmpty());
    }

    // confirm double patients are returned once
    @Test
    void shouldReturnDistinctPatients() {
        LocalDateTime recentDate = LocalDateTime.now().minusDays(2);

        // same patient appears in same room twice
        when(apiService.getAllocations()).thenReturn(List.of(
                new Allocation(1, 1, 4, 3, recentDate.toString(), null),
                new Allocation(2, 2, 4, 3, recentDate.toString(), null)
        ));
        when(apiService.getAdmissions()).thenReturn(List.of(
                new Admission(1, 2, recentDate.toString(), null),
                new Admission(2, 2, recentDate.toString(), null)
        ));

        List<Integer> result = f2Service.getPatientsInRoomWithinLast7Days(3);

        assertEquals(1, result.size()); // should return only one result
    }

    // return empty if no allocations found
    @Test
    void shouldReturnEmptyIfNoAllocationsExist() {
        // return empty lists
        when(apiService.getAllocations()).thenReturn(List.of());
        when(apiService.getAdmissions()).thenReturn(List.of());

        List<Integer> result = f2Service.getPatientsInRoomWithinLast7Days(3);

        assertTrue(result.isEmpty());
    }
}