package com.proiect.ProiectIsAeroport.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddFlyController {
    @GetMapping("/adaugareZbor")
    public String home() {
        return "companie_web/adaugareZbor";
    }
}
