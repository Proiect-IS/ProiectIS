package com.proiect.ProiectIsAeroport.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proiect.ProiectIsAeroport.companie.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CompanieController {

    public List<Zbor> zboruri = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File zboruriFile = new File("src/main/resources/zboruri.json");
    public List<Rezervare> rezervari = new ArrayList<>();
    private final ObjectMapper objectMapper1 = new ObjectMapper();
    private final File rezervariFile = new File("src/main/resources/rezervari.json");

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
        try {
            if (rezervariFile.exists()) {
                if (rezervariFile.length() > 0) {
                    objectMapper1.registerSubtypes(Zbor_Regulat.class, Zbor_Sezonier.class);
                    rezervari = objectMapper1.readValue(rezervariFile, new TypeReference<List<Rezervare>>() {
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

    @PostMapping("/stergeZbor")
    public String StergereZbor(@RequestParam("codCursa") String cod_cursa){
        zboruri.removeIf(zbor -> zbor.getCod_cursa().equals(cod_cursa));

        // Ștergi rezervările asociate
        rezervari.removeIf(rezervare -> rezervare.getCodCursa().equalsIgnoreCase(cod_cursa));

        // Salvează lista de zboruri actualizată
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(zboruriFile, zboruri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Salvează lista de rezervări actualizată
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(rezervariFile, rezervari);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/companie";
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
