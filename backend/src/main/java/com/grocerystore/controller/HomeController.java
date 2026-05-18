package com.grocerystore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Grocery Store backend is running. Use /api/products or /api/auth/** endpoints.";
    }
}
