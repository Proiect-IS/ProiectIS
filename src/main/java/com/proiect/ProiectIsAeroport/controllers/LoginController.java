package com.proiect.ProiectIsAeroport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "client_web/login"; // Asigură-te că aceasta este calea corectă către login.html
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("userType") String userType,
                               @RequestParam("password") String password,
                               Model model) {
        if ("companie".equals(userType)) {
            // Logica de verificare a parolei pentru companii
            if (checkCompanyPassword(username, password)) {
                return "redirect:/companie.html"; // Redirect către pagina companiei
            } else {
                model.addAttribute("error", "Nume de utilizator sau parolă incorecte.");
                return "client_web/login"; // Rămâi pe pagina de login cu mesaj de eroare
            }
        } else if ("client".equals(userType)) {
            // Logica de verificare a parolei pentru clienți standard
            if (checkStandardUserPassword(username, password)) {
                return "redirect:/home.html"; // Redirect către pagina principală a clientului
            } else {
                model.addAttribute("error", "Nume de utilizator sau parolă incorecte.");
                return "client_web/login"; // Rămâi pe pagina de login cu mesaj de eroare
            }
        } else {
            model.addAttribute("error", "Tip de utilizator invalid.");
            return "client_web/login"; // Rămâi pe pagina de login cu mesaj de eroare
        }
    }

    private boolean checkCompanyPassword(String username, String password) {
        return "companie1".equals(username) && "parolacompanie".equals(password);
    }

    private boolean checkStandardUserPassword(String username, String password) {
        return "user1".equals(username) && "parola1".equals(password) ||
                "user2".equals(username) && "parola2".equals(password);
    }
}