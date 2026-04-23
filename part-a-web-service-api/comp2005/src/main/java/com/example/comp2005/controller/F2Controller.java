package com.example.comp2005.controller;

import com.example.comp2005.service.F2Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "F2", description = "Patients in room last 7 days")
public class F2Controller {

    private final F2Service f2Service;

    public F2Controller(F2Service f2Service) {
        this.f2Service = f2Service;
    }

    @Operation(
            summary = "Get patients in a room within last 7 days",
            description = "Returns a list of patient IDs who have been in the specified room within the last 7 days"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned list of patients")
    })
    // test manually via http://localhost:8080/api/F2/1
    // !!always returns empty list because the external api data is outdated!!
    @GetMapping("/F2/{roomId}")
    public ResponseEntity<List<Integer>> getPatientsInRoomWithinLast7Days(@PathVariable int roomId) {
        return ResponseEntity.ok(f2Service.getPatientsInRoomWithinLast7Days(roomId));
    }
}