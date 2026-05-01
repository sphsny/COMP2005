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

    // normal case, overloaded staff is returned
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

    // edge case: staff has only two active allocations
    @Test
    void shouldNotReturnStaffWithJustTwoAllocations() {
        // employee 4 with one active allocation, employee 6 with 2 active allocations
        LocalDateTime now = LocalDateTime.now();

        when(apiService.getAllocations()).thenReturn(List.of(
                new Allocation(2, 2, 6, 5, now.minusHours(1).toString(), now.plusHours(2).toString()),
                new Allocation(3, 3, 6, 4, now.minusHours(1).toString(), now.plusHours(2).toString())
        ));

        List<Integer> result = f4Service.getOverloadedStaff();

        assertTrue(result.isEmpty());
    }

    // edge case: return empty list if no allocations found
    @Test
    void shouldReturnEmptyIfNoAllocationsExist() {
        when(apiService.getAllocations()).thenReturn(List.of());

        List<Integer> result = f4Service.getOverloadedStaff();

        assertTrue(result.isEmpty());
    }

    // edge case: allocation start time in the future
    @Test
    void shouldNotReturnOverloadedIfConcurrentAllocationsInTheFuture() {
        LocalDateTime now = LocalDateTime.now();

        // employee 4 overloaded in the future
        when(apiService.getAllocations()).thenReturn(List.of(
                new Allocation(1, 1, 4, 3, now.plusHours(1).toString(), now.plusHours(3).toString()),
                new Allocation(2, 2, 4, 5, now.plusHours(1).toString(), now.plusHours(3).toString()),
                new Allocation(3, 3, 4, 7, now.plusHours(1).toString(), now.plusHours(3).toString())
        ));

        List<Integer> result = f4Service.getOverloadedStaff();

        assertTrue(result.isEmpty());
    }

    // corner case: multiple overloaded employees
    @Test
    void shouldReturnMultipleOverloadedStaff() {
        LocalDateTime now = LocalDateTime.now();

        when(apiService.getAllocations()).thenReturn(List.of(
                // employee 4 with 3 active allocations
                new Allocation(1, 1, 1, 3, now.minusHours(2).toString(), null),
                new Allocation(2, 2, 1, 5, now.minusHours(1).toString(), null),
                new Allocation(3, 3, 1, 7, now.minusHours(1).toString(), null),
                // employee 6 with 3 active allocations
                new Allocation(4, 4, 2, 2, now.minusHours(2).toString(), null),
                new Allocation(5, 5, 2, 4, now.minusHours(1).toString(), null),
                new Allocation(6, 6, 2, 8, now.minusHours(1).toString(), null)
        ));

        List<Integer> result = f4Service.getOverloadedStaff();

        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
        assertEquals(2, result.size()); // 2 overloaded employees returned
    }
}