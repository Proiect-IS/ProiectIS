package com.proiect.ProiectIsAeroport.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanieController {
    @GetMapping("/companie")
    public String home() {
        return "companie_web/companie";
    }
}
