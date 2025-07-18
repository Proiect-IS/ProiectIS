package com.proiect.ProiectIsAeroport.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proiect.ProiectIsAeroport.companie.Rezervare;
import com.proiect.ProiectIsAeroport.companie.Zbor;
import com.proiect.ProiectIsAeroport.companie.Zbor_Regulat;
import com.proiect.ProiectIsAeroport.companie.Zbor_Sezonier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StaffController {
    private List<Rezervare> rezervari = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File rezervariFile = new File("src/main/resources/rezervari.json");

    public StaffController() {
        try {
            if (rezervariFile.exists()) {
                if (rezervariFile.length() > 0) {
                    objectMapper.registerSubtypes(Zbor_Regulat.class, Zbor_Sezonier.class);
                    rezervari = objectMapper.readValue(rezervariFile, new TypeReference<List<Rezervare>>() {
                    });
                } else {
                    rezervari = new ArrayList<>();
                }
            } else {
                rezervariFile.createNewFile();
                rezervari = new ArrayList<>();
            }
        } catch (IOException e) {
            rezervari = new ArrayList<>();
        }
    }

    public List<Rezervare> FindZborValidare(@RequestParam("codCursa") String codCursa ) {
        List<Rezervare> rezervareList = rezervari
                .stream()
                .filter(p->p.getCodCursa().equalsIgnoreCase(codCursa))
                .collect(Collectors.toList());
        if (rezervareList.size() == 0) {
            System.out.println("No rezervari found");
        }
        return rezervareList;
    }
    public void salveazaRezervari() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(rezervariFile, rezervari);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/valideazaPlataCash")
    public String validareRezervare(@RequestParam("numePasager") String nume ) {
        for(Rezervare rezerva : rezervari) {
            if(rezerva.getNumePasager().equalsIgnoreCase(nume)) {
                rezerva.setValidat(true);
                salveazaRezervari();
                break;
            }
        }


        return "personal_web/personal";
    }

    @GetMapping("/zboruri")
    public String staff(@RequestParam ("codCursa") String codCursa,
                       org.springframework.ui.Model model) {

        List<Rezervare> rezervareList = new ArrayList<>();

        if (codCursa !=null) {
            rezervareList = FindZborValidare(codCursa);
            System.out.println(rezervareList);
        } else {
            System.out.println("Parametrii de căutare nu sunt completi.");
            // Poți adăuga aici un mesaj pentru utilizator în model dacă dorești
            // model.addAttribute("mesaj", "Introduceți criteriile de căutare.");
        }

        model.addAttribute("rezervariCautare", rezervareList);

        System.out.println(rezervareList);

        return "personal_web/personal";
    }




}
