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
            // Logica de verificare a parolei pentru clienți standard
            if (checkStandardUserPassword(password)) {
                return "redirect:/home.html";
            } else {
                model.addAttribute("error", "Parolă incorectă pentru tipul de utilizator selectat.");
                return "client_web/login";
            }
        } else {
            model.addAttribute("error", "Tip de utilizator invalid.");
            return "client_web/login";
        }
    }

    // Metode placeholder pentru verificarea parolei (va trebui să le implementezi)
    private boolean checkCompanyPassword(String password) {
        // Aici vei interoga baza de date sau altă sursă pentru a verifica parola companiei
        // Compară password cu parola stocată pentru companii
        return "parolacompanie".equals(password);
    }

    private boolean checkStandardUserPassword(String password) {
        return "parolastaff".equals(password);
    }
}