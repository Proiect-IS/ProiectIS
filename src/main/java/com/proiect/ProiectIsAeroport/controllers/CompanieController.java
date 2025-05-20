package com.proiect.ProiectIsAeroport.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proiect.ProiectIsAeroport.companie.Tip_Zbor;
import com.proiect.ProiectIsAeroport.companie.Zbor;
import com.proiect.ProiectIsAeroport.companie.Zbor_Regulat;
import com.proiect.ProiectIsAeroport.companie.Zbor_Sezonier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;

@Controller
public class CompanieController {

    private List<Zbor> zboruri = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File zboruriFile = new File("src/main/resources/zboruri.json");
    public CompanieController() {
        try {
            if (zboruriFile.exists()) {
                if (zboruriFile.length() > 0) {
                    objectMapper.registerSubtypes(Zbor_Regulat.class, Zbor_Sezonier.class);
                    zboruri = objectMapper.readValue(zboruriFile, new TypeReference<List<Zbor>>() {});
                } else {
                    zboruri = new ArrayList<>();
                }
            } else {
                zboruriFile.createNewFile();
                zboruri = new ArrayList<>();
            }
        } catch (IOException e) {
            zboruri = new ArrayList<>();
        }
    }

    @GetMapping("/companie")
    public String home(org.springframework.ui.Model model) {
        List<Zbor_Regulat> zboruriRegulate = new ArrayList<>();
        List<Zbor_Sezonier> zboruriSezoniere = new ArrayList<>();
        List<Zbor> zboruriBase = new ArrayList<>(); //Lista pentru Zbor

        for (Zbor zbor : zboruri) {
            if (zbor instanceof Zbor_Sezonier) {
                zboruriSezoniere.add((Zbor_Sezonier) zbor);
            } else if (zbor instanceof Zbor_Regulat) {
                zboruriRegulate.add((Zbor_Regulat) zbor);
            } else {
                zboruriBase.add(zbor);
            }
        }

        model.addAttribute("zboruriRegulate", zboruriRegulate);
        model.addAttribute("zboruriSezoniere", zboruriSezoniere);
        model.addAttribute("zboruriBase", zboruriBase);
        System.out.println(zboruriBase);
        return "companie_web/companie";
    }
}
