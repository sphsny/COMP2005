package com.example.comp2005.controller;

import com.example.comp2005.service.F3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "F3", description = "See what room is used the least")
public class F3Controller {

    private final F3Service f3Service;

    public F3Controller(F3Service f3Service) {
        this.f3Service = f3Service;
    }

    @Operation(
            summary = "Get least used room overall",
            description = "Returns a list with the least used room. Can return multiple rooms if usage is equal."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned list of least used room(s).")
    })
    // test manually via http://localhost:8080/api/F3
    // returns 2, 3, 4
    @GetMapping("/F3")
    public ResponseEntity<List<Integer>> getLeastUsedRoom() {
        return ResponseEntity.ok(f3Service.getLeastUsedRoom());
    }
}