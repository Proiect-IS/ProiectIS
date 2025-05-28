package com.proiect.ProiectIsAeroport.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "client_web/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("userType") String userType,
                               @RequestParam("password") String password,
                               Model model) {
        if ("companie".equals(userType)) {
            // Logica de verificare a parolei pentru companii
            if (checkCompanyPassword(password)) {
                return "redirect:/companie";
            } else {
                model.addAttribute("error", "Parolă incorectă pentru tipul de utilizator selectat.");
                return "client_web/login";
            }
        } else if ("staff".equals(userType)) {
            if (checkStandardUserPassword(password)) {
                return "redirect:/personal";
            } else {
                model.addAttribute("error", "Parolă incorectă pentru tipul de utilizator selectat.");
                return "client_web/login";
            }
        } else {
            model.addAttribute("error", "Tip de utilizator invalid.");
            return "client_web/login";
        }
    }

    private boolean checkCompanyPassword(String password) {
        return "parolacompanie".equals(password);
    }

    private boolean checkStandardUserPassword(String password) {
        return "parolastaff".equals(password);
    }
}