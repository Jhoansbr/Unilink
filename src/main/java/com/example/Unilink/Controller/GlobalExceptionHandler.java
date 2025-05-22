package com.example.Unilink.Controller;

import org.springframework.ui.Model;

public class GlobalExceptionHandler {
    public String handleAllExceptions(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Error: " + ex.getMessage());
        return "error";  // crea una vista error.html para mostrarlo bonito
    }
    
}
