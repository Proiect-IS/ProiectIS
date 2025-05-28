package com.proiect.ProiectIsAeroport.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.proiect.ProiectIsAeroport.companie.*;
import com.proiect.ProiectIsAeroport.dto.ZborDto;
import com.proiect.ProiectIsAeroport.mapper.ZborMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class SerializareController {

    private List<Zbor> zboruri = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File zboruriFile = new File("src/main/resources/zboruri.json");

    // private final File zboruriFile = new File("zboruri.json");
    public void scriere(List<Zbor> lista) {
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.registerSubtypes(Zbor_Regulat.class, Zbor_Sezonier.class);
            objectMapper.writeValue(zboruriFile, lista);
        } catch (IOException e) {
        }
    }

    public SerializareController() {
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
//    @PostMapping("/salveazaZbor")
//    public ResponseEntity<String> salveazaZbor(@RequestBody java.util.Map<String, String> formData) {
//        try {
//            String codCursa = formData.get("codCursa");
//            Tip_Zbor tipZbor = Tip_Zbor.valueOf(formData.get("tipZbor").toUpperCase());
//            String rutaPlecare = formData.get("rutaPlecare");
//            String rutaDestinatie = formData.get("rutaDestinatie");
//            double pretClasa1 = Integer.parseInt(formData.get("pretClasa1"));
//            double pretBusiness = Integer.parseInt(formData.get("pretClasaBusiness"));
//            double pretEconomie = Integer.parseInt(formData.get("pretClasaEconomie"));
//            Model modelAvion = Model.valueOf(formData.get("modelAvion").toUpperCase());
//            int locuriClasa1 = Integer.parseInt(formData.get("locuriClasa1"));
//            int locuriBusiness = Integer.parseInt(formData.get("locuriClasaBusiness"));
//            int locuriEconomie = Integer.parseInt(formData.get("locuriClasaEconomie"));
//            boolean esteTurRetur = "true".equalsIgnoreCase(formData.get("esteTurRetur"));
//            int discount = Integer.parseInt(formData.get("discount"));
//            Zbor zbor;
//            if(esteTurRetur && discount == 0) {
//                pretClasa1=pretClasa1*0.95;
//                pretBusiness=pretBusiness*0.95;
//                pretEconomie=pretEconomie*0.95;
//            } else if (esteTurRetur && (discount!=0)) {
//                pretClasa1=pretClasa1*(1-((double)discount/100));
//                pretBusiness=pretBusiness*(1-((double)discount/100));
//                pretEconomie=pretEconomie*(1-((double)discount/100));
//            }
//
//
//            if (tipZbor == Tip_Zbor.REGULAT) {
//                String zi = formData.get("zi");
//                String oraPlecare = formData.get("oraPlecare");
//                zbor = new Zbor_Regulat(codCursa, tipZbor, rutaDestinatie, rutaPlecare, pretBusiness, pretClasa1, pretEconomie, modelAvion, locuriBusiness, locuriClasa1, locuriEconomie, esteTurRetur, discount, zi, oraPlecare);
//            } else if (tipZbor == Tip_Zbor.SEZONIER) {
//                String zi = formData.get("zi");
//                String oraPlecare = formData.get("oraPlecare");
//                String lunaStart = formData.get("lunaStart");
//                String lunaEnd = formData.get("lunaEnd");
//                zbor = new Zbor_Sezonier(codCursa, tipZbor, rutaDestinatie, rutaPlecare, pretBusiness, pretClasa1, pretEconomie, modelAvion, locuriBusiness, locuriClasa1, locuriEconomie, esteTurRetur, discount, zi, oraPlecare, lunaStart, lunaEnd);
//            } else {
//                return new ResponseEntity<>("Eroare: Tip de zbor necunoscut.", HttpStatus.BAD_REQUEST);
//            }
//
//            // Citim zborurile existente
//            List<Zbor> existingZboruri = new ArrayList<>();
//            if (zboruriFile.exists() && zboruriFile.length() > 0) {
//                try {
//                    existingZboruri = objectMapper.readValue(zboruriFile, new TypeReference<List<Zbor>>() {});
//                } catch (IOException e) {
//                    String mesajErr = "Eroare la citirea zborurilor existente: {}" + e.getMessage();
//                    return new ResponseEntity<>(mesajErr, HttpStatus.INTERNAL_SERVER_ERROR); // Important: Return error
//                }
//            }
//
//            existingZboruri.add(zbor);
//            scriere(existingZboruri);
//            return new ResponseEntity<>("Zbor salvat cu succes!", HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>("Eroare: Valori invalide în formular.", HttpStatus.BAD_REQUEST);
//        }
//    }


    @PostMapping("/salveazaZbor")
    public ResponseEntity<Map<String, String>> salveazaZbor(@RequestBody Map<String, String> formData) { // Returnează Map<String, String>
        Map<String, String> response = new HashMap<>();
        try {
            // 1. Extragerea și parsarea datelor (cu validare atentă)
            String codCursa = formData.get("codCursa");
            if (codCursa == null || codCursa.trim().isEmpty()) {
                throw new IllegalArgumentException("Cod cursă este obligatoriu.");
            }

            String tipZborStr = formData.get("tipZbor");
            if (tipZborStr == null || tipZborStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Tip zbor este obligatoriu.");
            }
            Tip_Zbor tipZbor = Tip_Zbor.valueOf(tipZborStr.toUpperCase());

            String rutaPlecare = formData.get("rutaPlecare");
            String rutaDestinatie = formData.get("rutaDestinatie");

            String pretClasa1Str = formData.get("pretClasa1");
            if (pretClasa1Str == null || pretClasa1Str.trim().isEmpty()) {
                throw new IllegalArgumentException("Preț Clasa 1 este obligatoriu.");
            }
            double pretClasa1 = Double.parseDouble(pretClasa1Str);

            String pretBusinessStr = formData.get("pretClasaBusiness");
            if (pretBusinessStr == null || pretBusinessStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Preț Business este obligatoriu.");
            }
            double pretBusiness = Double.parseDouble(pretBusinessStr);

            String pretEconomieStr = formData.get("pretClasaEconomie");
            if (pretEconomieStr == null || pretEconomieStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Preț Economie este obligatoriu.");
            }
            double pretEconomie = Double.parseDouble(pretEconomieStr);

            String modelAvionStr = formData.get("modelAvion");
            if (modelAvionStr == null || modelAvionStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Model avion este obligatoriu.");
            }
            Model modelAvion = Model.valueOf(modelAvionStr.toUpperCase());

            String locuriClasa1Str = formData.get("locuriClasa1");
            if (locuriClasa1Str == null || locuriClasa1Str.trim().isEmpty()) {
                throw new IllegalArgumentException("Locuri Clasa 1 sunt obligatorii.");
            }
            int locuriClasa1 = Integer.parseInt(locuriClasa1Str);

            String locuriBusinessStr = formData.get("locuriClasaBusiness");
            if (locuriBusinessStr == null || locuriBusinessStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Locuri Business sunt obligatorii.");
            }
            int locuriBusiness = Integer.parseInt(locuriBusinessStr);

            String locuriEconomieStr = formData.get("locuriClasaEconomie");
            if (locuriEconomieStr == null || locuriEconomieStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Locuri Economie sunt obligatorii.");
            }
            int locuriEconomie = Integer.parseInt(locuriEconomieStr);

            String esteTurReturStr = formData.get("esteTurRetur");
            boolean esteTurRetur = "true".equalsIgnoreCase(esteTurReturStr); // Nu e obligatoriu, checkbox-ul trimite "on" sau nimic

            String discountStr = formData.get("discount");
            if (discountStr == null || discountStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Discount este obligatoriu.");
            }
            int discount = Integer.parseInt(discountStr);

            // 2. Calcularea prețurilor (cu logica ta)
            if (esteTurRetur && discount == 0) {
                pretClasa1 *= 0.95;
                pretBusiness *= 0.95;
                pretEconomie *= 0.95;
            } else if (esteTurRetur && discount != 0) {
                pretClasa1 *= (1 - (discount / 100.0));
                pretBusiness *= (1 - (discount / 100.0));
                pretEconomie *= (1 - (discount / 100.0));
            }

            Zbor zbor;

            if (tipZbor == Tip_Zbor.REGULAT) {
                String zi = formData.get("zi");
                String oraPlecare = formData.get("oraPlecare");
                zbor = new Zbor_Regulat(codCursa, tipZbor, rutaDestinatie, rutaPlecare, pretBusiness, pretClasa1, pretEconomie, modelAvion, locuriBusiness, locuriClasa1, locuriEconomie, esteTurRetur, discount, zi, oraPlecare);
            } else if (tipZbor == Tip_Zbor.SEZONIER) {
                String zi = formData.get("zi");
                String oraPlecare = formData.get("oraPlecare");
                String lunaStart = formData.get("lunaStart");
                String lunaEnd = formData.get("lunaEnd");
                zbor = new Zbor_Sezonier(codCursa, tipZbor, rutaDestinatie, rutaPlecare, pretBusiness, pretClasa1, pretEconomie, modelAvion, locuriBusiness, locuriClasa1, locuriEconomie, esteTurRetur, discount, zi, oraPlecare, lunaStart, lunaEnd);
            } else {
                response.put("status", "error");
                response.put("message", "Eroare: Tip de zbor necunoscut.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // 3. Citirea zborurilor existente
            List<Zbor> existingZboruri = new ArrayList<>();
            if (zboruriFile.exists() && zboruriFile.length() > 0) {
                try {
                    existingZboruri = objectMapper.readValue(zboruriFile, new TypeReference<List<Zbor>>() {});
                } catch (IOException e) {
                    System.out.println("Eroare la citirea zborurilor existente");
                    response.put("status", "error");
                    response.put("message", "Eroare la citirea datelor existente.");
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            // 4. Adăugarea zborului nou
            existingZboruri.add(zbor);

            // 5. Scrierea zborurilor în fișier
            scriere(existingZboruri);
            //logger.info("Zbor salvat cu succes: {}", zbor.getCod_cursa());
            response.put("status", "success");
            response.put("message", "Zbor salvat cu succes!");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            //logger.warn("Date invalide în formular: {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "Eroare: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
           // logger.error("Date incomplete în formular", e);
            response.put("status", "error");
            response.put("message", "Eroare: Date incomplete. Asigură-te că toate câmpurile sunt completate.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
           // logger.error("Eroare neașteptată la salvarea zborului", e);
            response.put("status", "error");
            response.put("message", "Eroare internă a serverului.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
