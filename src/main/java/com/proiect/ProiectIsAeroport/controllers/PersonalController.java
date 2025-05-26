package com.proiect.ProiectIsAeroport.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonalController {

    @GetMapping("/personal")
    public String showPersonalPage() {
        return "personal_web/personal";
    }

}
