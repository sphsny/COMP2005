package com.example.comp2005;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    String index() {
        return "Application is running";
    }
}