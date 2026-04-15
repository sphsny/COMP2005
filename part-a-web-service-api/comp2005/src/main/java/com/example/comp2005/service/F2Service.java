package com.example.comp2005.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// Business logic: return the distinct patient IDs that have been to a specific room within the last 7 days

@Service
public class F2Service {

    // get external API
    private final ApiService apiService;

    public F2Service(ApiService apiService) {
        this.apiService = apiService;
    }

    public List<Integer> getPatientsInRoomWithinLast7Days(int roomId) {
        // split time into more than 7 days ago or not by taking current time and deducting 7 days
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        // step 1: find admissionIDs for room that lies within last 7 days
        Set<Integer> recentAdmissionIds = apiService.getAllocations().stream()
                .filter(al -> al.roomID == roomId) // find allocations where roomID matches to the specified room
                .filter(al -> LocalDateTime.parse(al.startTime).isAfter(sevenDaysAgo)) // check whether there are any allocations that lie within 7 days, in built LocalDateTime functions
                .map(al -> al.admissionID) // get admissions from the filtered allocations
                .collect(Collectors.toSet()); // store in set to use in step 2 as recentAdmissionIds

        // step 2: find patientIDs from those admissions
        return apiService.getAdmissions().stream() // get all admissions
                .filter(a -> recentAdmissionIds.contains(a.id)) // keep only admissions that were within the last 7 days (logic from step 1)
                .map(a -> a.patientID) // get the patient ids from the kept admissions
                .distinct() // prevent duplicates
                .sorted() // sort by numbers
                .collect(Collectors.toList()); // return list containing the patient ids

        // rest is older than 7 days and returns an empty list
    }
}