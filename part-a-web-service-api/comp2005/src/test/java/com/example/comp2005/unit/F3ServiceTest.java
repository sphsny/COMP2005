package com.example.comp2005.unit;

import com.example.comp2005.model.RoomAllocation;
import com.example.comp2005.service.ApiService;
import com.example.comp2005.service.F3Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class F3ServiceTest {

    @Mock
    private ApiService apiService;

    @InjectMocks
    private F3Service f3Service;

    @Test
    void shouldReturnLeastUsedRoom() {
        // room 3 used twice, room 4 used once
        when(apiService.getRoomAllocations()).thenReturn(List.of(
                new RoomAllocation(1, 2, 3, "2020-12-07T22:14:00", null),
                new RoomAllocation(2, 1, 3, "2020-11-28T16:45:00", "2020-11-28T20:45:00"),
                new RoomAllocation(3, 1, 4, "2020-11-28T20:45:00", "2020-11-28T23:56:00")
        ));

        List<Integer> result = f3Service.getLeastUsedRoom();

        assertEquals(List.of(4), result);
    }

    @Test
    void shouldReturnEmptyListIfNoRoomIsUsed() {
        // no room ever used -> empty list
        when(apiService.getRoomAllocations()).thenReturn(List.of());

        List<Integer> result = f3Service.getLeastUsedRoom();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnMultipleRoomsOnTie() {
        // rooms 3 and 5 both used only once -> tie
        when(apiService.getRoomAllocations()).thenReturn(List.of(
                new RoomAllocation(1, 1, 3, "2020-12-07T22:14:00", null),
                new RoomAllocation(2, 2, 5, "2020-11-28T16:45:00", null)
        ));

        List<Integer> result = f3Service.getLeastUsedRoom();

        assertEquals(List.of(3, 5), result);
    }
}