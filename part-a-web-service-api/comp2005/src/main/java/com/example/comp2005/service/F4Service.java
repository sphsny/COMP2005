package com.example.comp2005.service;

import com.example.comp2005.model.Allocation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Business requirement: Return list of overloaded staff members

/*
Logic:
1. group allocations by employeeID
2. return list of employeeIDs that have 3+ active allocations

Overlapping logic:
1. calculate only allocations that have no endTime -> active allocation
2.
*/

@Service
public class F4Service {
    // get external API
    private final ApiService apiService;

    public F4Service(ApiService apiService) {
        this.apiService = apiService;
    }

    // get staff ID -> call staff information with this ID (/Employee/{id})
    public List<Integer> getOverloadedStaff() {
        // group allocations by employeeID
        Map<Integer, List<Allocation>> EmployeeAllocations = apiService.getAllocations().stream()
                .collect(Collectors.groupingBy(al -> al.employeeID)); // get allocations associated with an employee's ID

        // return employeeIDs where 3+ allocations are active at the same time (current)
        return EmployeeAllocations.entrySet().stream()
                .filter(employee -> hasThreeOrMoreConcurrentAllocations(employee.getValue())) // loop over each employee to get allocations
                .map(Map.Entry::getKey) // get employee ids
                .sorted() // sort IDs by ascending order in case there is multiple overloaded employees
                .collect(Collectors.toList()); // return list
    }

    private boolean hasThreeOrMoreConcurrentAllocations(List<Allocation> allocations) {
        LocalDateTime now = LocalDateTime.now(); // get current time

        // count how many allocations are active right now
        long counter = allocations.stream() // convert allocations into stream
                .filter(allocation -> {
                    LocalDateTime start = LocalDateTime.parse(allocation.startTime); // get allocation start time
                    LocalDateTime end = allocation.endTime != null // if no endTime -> allocation is currently active/handled by employee
                            ? LocalDateTime.parse(allocation.endTime)
                            : now; // assign current time as temp endTime to avoid NullPointerException error

                    return !start.isAfter(now) && !end.isBefore(now);
                })
                .count(); // add allocation to counter of current active allocations

        // return true if 3 or more allocations are active at the same time
        return counter >= 3;
    }
}