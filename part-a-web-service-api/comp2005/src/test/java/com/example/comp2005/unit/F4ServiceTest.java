package com.example.comp2005.unit;

import com.example.comp2005.model.Allocation;
import com.example.comp2005.service.ApiService;
import com.example.comp2005.service.F4Service;
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
class F4ServiceTest {

    @Mock
    private ApiService apiService;

    @InjectMocks
    private F4Service f4Service;

    @Test
    void shouldReturnOverloadedStaff() {
        // employee 4 with 3 active allocations
        LocalDateTime now = LocalDateTime.now();

        when(apiService.getAllocations()).thenReturn(List.of(
                new Allocation(1, 1, 4, 3, now.minusHours(2).toString(), null),
                new Allocation(2, 2, 4, 5, now.minusHours(1).toString(), now.plusHours(1).toString()),
                new Allocation(3, 3, 4, 7, now.minusMinutes(30).toString(), now.plusHours(1).toString())
        ));

        List<Integer> result = f4Service.getOverloadedStaff();

        assertTrue(result.contains(4));
    }

    @Test
    void shouldReturnEmptyIfNoStaffOverloaded() {
        // employee 4 with one active allocation, employee 6 with 2 active allocations
        LocalDateTime now = LocalDateTime.now();

        when(apiService.getAllocations()).thenReturn(List.of(
                new Allocation(1, 1, 4, 3, now.minusHours(2).toString(), now.plusHours(1).toString()),
                new Allocation(2, 2, 6, 5, now.minusHours(1).toString(), now.plusHours(2).toString()),
                new Allocation(3, 3, 6, 4, now.minusHours(1).toString(), now.plusHours(2).toString())
        ));

        List<Integer> result = f4Service.getOverloadedStaff();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyIfNoAllocationsExist() {
        when(apiService.getAllocations()).thenReturn(List.of());

        List<Integer> result = f4Service.getOverloadedStaff();

        assertTrue(result.isEmpty());
    }
}