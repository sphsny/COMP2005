package com.example.comp2005.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Business requirement: Return least used room

/*
Logic:
1. group allocations by roomID
2. count occurrences
3. return room with lowest count

Edge/Corner cases:
- on tie, return multiple rooms
- if no rooms are found, return empty list
*/

@Service
public class F3Service {

    // get external API
    private final ApiService apiService;

    public F3Service(ApiService apiService) {
        this.apiService = apiService;
    }

    // Allocations(): roomIDs are 0 -> hence using RoomAllocations()
    // RoomAllocations() provides same data but different admissionID, roomIDs that are not 0, no employeeID

    public List<Integer> getLeastUsedRoom() {
        Map<Integer, Long> counts = apiService.getRoomAllocations().stream()
                // count how many allocations are associated with each roomID
                .collect(Collectors.groupingBy(al -> al.roomID, Collectors.counting()));

        if (counts.isEmpty()) return List.of(); // catch empty list

        long minCount = counts.values().stream() // long because .collect returns Long instead of int https://stackoverflow.com/questions/65124360/count-java-stream-to-integer-not-long
                .mapToLong(Long::longValue) // convert Long obj into long stream
                .min() // get smallest value from the stream
                .orElseThrow(); // java exception handler ensuring minCount is not empty

        return counts.entrySet().stream()// convert to stream
                .filter(e -> e.getValue() == minCount) // keep only entries where count matches minCount
                .map(Map.Entry::getKey) // get room id from the count/room key pairs
                .sorted() // sort by ascending values
                .collect(Collectors.toList()); // return list of least used room(s) as List<Integer>
    }
}