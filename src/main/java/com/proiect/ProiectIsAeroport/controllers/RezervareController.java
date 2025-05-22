package com.proiect.ProiectIsAeroport.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proiect.ProiectIsAeroport.companie.Rezervare;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rezervari")
public class RezervareController {

    private List<Rezervare> rezervari = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File rezervariFile = new File("src/main/resources/rezervari.json");

    public RezervareController() {
        try {
            if (rezervariFile.exists() && rezervariFile.length() > 0) {
                rezervari = objectMapper.readValue(rezervariFile, new TypeReference<List<Rezervare>>() {});
            } else {
                rezervariFile.createNewFile();
                rezervari = new ArrayList<>();
            }
        } catch (IOException e) {
            rezervari = new ArrayList<>();
        }
    }

    public void salveazaRezervari() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(rezervariFile, rezervari);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ Adaugă o rezervare
    @PostMapping("/adauga")
    @ResponseBody
    public String adaugaRezervare(@RequestBody Rezervare rezervare) {
        rezervari.add(rezervare);
        salveazaRezervari();
        return "Rezervare salvată cu succes.";
    }

    // ✅ Afișează toate rezervările
    @GetMapping("/toate")
    @ResponseBody
    public List<Rezervare> getRezervari() {
        return rezervari;
    }

    // ✅ Calculează locuri ocupate pentru un zbor și o clasă
    @GetMapping("/locuri-ocupate")
    @ResponseBody
    public int getLocuriOcupate(@RequestParam("codCursa") String codCursa,
                                @RequestParam("clasa") String clasa) {
        return rezervari.stream()
                .filter(r -> r.getCodCursa().equals(codCursa) && r.getClasa().equalsIgnoreCase(clasa))
                .mapToInt(r -> r.getNrAdulti() + r.getNrCopii() + r.getNrSeniori())
                .sum();
    }
}