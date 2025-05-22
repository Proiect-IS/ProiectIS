package com.proiect.ProiectIsAeroport.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proiect.ProiectIsAeroport.companie.Zbor;
import com.proiect.ProiectIsAeroport.companie.Zbor_Regulat;
import com.proiect.ProiectIsAeroport.companie.Zbor_Sezonier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private List<Zbor> zboruri = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File zboruriFile = new File("src/main/resources/zboruri.json");

    public HomeController() {
        try {
            if (zboruriFile.exists()) {
                if (zboruriFile.length() > 0) {
                    objectMapper.registerSubtypes(Zbor_Regulat.class, Zbor_Sezonier.class);
                    zboruri = objectMapper.readValue(zboruriFile, new TypeReference<List<Zbor>>() {
                    });
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

    public List<Zbor> FindZbor(@RequestParam("plecare") String oras_plecare, @RequestParam("destinatie") String oras_destinatie, @RequestParam("tip_zbor") boolean esteTurRetur) {
        List<Zbor> zborList = zboruri
                .stream()
                .filter(p -> p.getOras_plecare().equalsIgnoreCase(oras_plecare))
                .filter(p -> p.getOras_destinatie().equalsIgnoreCase(oras_destinatie))
                .filter(p -> p.isEsteTurRetur() == esteTurRetur)
                .collect(Collectors.toList());
        if (zborList.size() == 0) {
            System.out.println("No zboruri found");
        }
        return zborList;
    }

    @GetMapping("/")
    public String home(@RequestParam(value = "plecare", required = false) String oras_plecare,
                       @RequestParam(value = "destinatie", required = false) String oras_destinatie,
                       @RequestParam(value = "tip_zbor", required = false) Boolean esteTurRetur,
                       org.springframework.ui.Model model) {

        List<Zbor> zborList = new ArrayList<>();

        if (oras_plecare != null && oras_destinatie != null && esteTurRetur != null) {
            zborList = FindZbor(oras_plecare, oras_destinatie, esteTurRetur);
            System.out.println(zborList);
        } else {
            System.out.println("Parametrii de căutare nu sunt completi.");
            // Poți adăuga aici un mesaj pentru utilizator în model dacă dorești
            // model.addAttribute("mesaj", "Introduceți criteriile de căutare.");
        }

        model.addAttribute("zboruriCautare", zborList);

        System.out.println(zborList);

        return "client_web/home";
    }
    @GetMapping("/detaliiZbor")
    public String detaliiZbor(@RequestParam("codCursa") String codCursa, org.springframework.ui.Model model) {
        // ... logica pentru a căuta zborul în baza codului cursei
        Zbor zborSelectat = findZborByCodCursa(codCursa); // Implementează această metodă

        if (zborSelectat != null) {
            model.addAttribute("zbor", zborSelectat);
            return "client_web/detaliiZbor";
        } else {
            // ... gestionare eroare ...
            return "error";
        }
    }
    private Zbor findZborByCodCursa(String codCursa) {
        return zboruri.stream()
                .filter(zbor -> zbor.getCod_cursa().equals(codCursa))
                .findFirst()
                .orElse(null);
    }
}
