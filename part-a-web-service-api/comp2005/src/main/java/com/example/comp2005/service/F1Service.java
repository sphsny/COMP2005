package com.example.comp2005.service;

// Business logic: return the distinct room IDs a patient has been admitted to.
// Returns an empty list if the patient has no admissions or no room allocations.

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class F1Service {
    // get external API
    private final ApiService apiService;

    public F1Service(ApiService apiService) {
        this.apiService = apiService;
    }

    // step 1: find all admission IDs for a patient via GET /Admissions
    public List<Integer> getRoomsByPatient(int patientId) {
        Set<Integer> admissionIds = apiService.getAdmissions().stream() // convert list into stream
                .filter(a -> a.patientID == patientId) // keep admissions where patient id matches
                .map(a -> a.id) // return admission ids
                .collect(Collectors.toSet()); // put admission ids into set (no duplicates)

        // step 2: find all room IDs from room allocations matching the admission IDs via GET /RoomAllocations
        return apiService.getRoomAllocations().stream() // convert list into stream
                // filter to keep room allocations associated with the set of admission ids
                .filter(ra -> admissionIds.contains(ra.admissionID))
                .map(ra -> ra.roomID) // return room ids
                .distinct() // remove duplicate room ids
                .sorted() // sort by number
                .collect(Collectors.toList()); // return as list of integers
    }
}
