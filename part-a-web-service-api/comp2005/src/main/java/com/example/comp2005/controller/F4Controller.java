package com.example.comp2005.controller;

import com.example.comp2005.service.F4Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "F4", description = "Staff with 3 or more concurrent patients")
public class F4Controller {

    private final F4Service f4Service;

    public F4Controller(F4Service f4Service) {
        this.f4Service = f4Service;
    }

    @Operation(
            summary = "Get staff members that are responsible for 3 or more patients at the same time",
            description = "Returns a list of staff IDs who are currently assigned to 3 or more active allocations"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned list of staff")
    })
    // test manually via http://localhost:8080/api/F4
    // returns [] because no overlapping active allocations
    @GetMapping("/F4")
    public ResponseEntity<List<Integer>> getPatientsInRoomWithinLast7Days() {
        return ResponseEntity.ok(f4Service.getOverloadedStaff());
    }
}