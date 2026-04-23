package com.example.comp2005.controller;

import com.example.comp2005.service.F1Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "F1", description = "Patient room history") // set category
public class F1Controller {

    private final F1Service f1Service;

    public F1Controller(F1Service f1Service) {
        this.f1Service = f1Service;
    }

    // describe purpose
    @Operation(
            summary = "Get rooms for a specific patient",
            description = "Returns a list of room IDs that the specified patient has ever been admitted to"
    )
    // possible HTTP responses
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned list of rooms")
    })
    // custom endpoint, test manually via http://localhost:8080/api/F1/1
    @GetMapping("/F1/{patientId}")
    public ResponseEntity<List<Integer>> getRoomsByPatient(@PathVariable int patientId) {
        return ResponseEntity.ok(f1Service.getRoomsByPatient(patientId));
    }
}
