package com.proiect.ProiectIsAeroport.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.proiect.ProiectIsAeroport.companie.*;
import com.proiect.ProiectIsAeroport.dto.ZborDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class SerializareController {

    private final List<Zbor> zboruri = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File zboruriFile = new File("src/main/resources/zboruri.json");

    // private final File zboruriFile = new File("zboruri.json");
    public static void scriere(List<Zbor> lista) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/zboruri.json");
            mapper.writeValue(file, lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SerializareController() {
        // Constructor: Încărcăm lista de zboruri din fișier la initializare
        try {
            if (zboruriFile.exists()) {
                zboruri.addAll(objectMapper.readValue(zboruriFile, new TypeReference<List<Zbor>>() {
                }));
            }
        } catch (IOException e) {
            e.printStackTrace(); // Log the error
            // Decide how to handle the error:
            // 1. Inform the user and start with an empty list
            // 2. Throw an exception to prevent the application from starting
        }
    }

    @PostMapping("/salveazaZbor")
    public ResponseEntity<String> salveazaZbor(@RequestBody java.util.Map<String, String> formData) {
        try {
            String codCursa = formData.get("codCursa");
            Tip_Zbor tipZbor = Tip_Zbor.valueOf(formData.get("tipZbor").toUpperCase());
            String rutaPlecare = formData.get("rutaPlecare");
            String rutaDestinatie = formData.get("rutaDestinatie");

            int pretClasa1 = Integer.parseInt(formData.get("pretClasa1"));
            int pretBusiness = Integer.parseInt(formData.get("pretClasaBusiness"));
            int pretEconomie = Integer.parseInt(formData.get("pretClasaEconomie"));

            Model modelAvion = Model.valueOf(formData.get("modelAvion").toUpperCase());

            //int numarLocuriTotale = Integer.parseInt(formData.get("numarLocuriTotale"));
            int locuriClasa1 = Integer.parseInt(formData.get("locuriClasa1"));
            int locuriBusiness = Integer.parseInt(formData.get("locuriClasaBusiness"));
            int locuriEconomie = Integer.parseInt(formData.get("locuriClasaEconomie"));


            // Momentan setăm esteTurRetur la false și discount la valoarea din form
            boolean esteTurRetur = false;
            int discount = Integer.parseInt(formData.get("discount"));
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
                zbor = new Zbor_Sezonier(codCursa, tipZbor, rutaDestinatie, rutaPlecare, pretBusiness, pretClasa1, pretEconomie, modelAvion, locuriBusiness, locuriClasa1, locuriEconomie, esteTurRetur, discount,zi,oraPlecare,lunaStart, lunaEnd);
            } else {
                return new ResponseEntity<>("Eroare: Tip de zbor necunoscut.", HttpStatus.BAD_REQUEST);
            }
            zboruri.add(zbor);
            scriere(zboruri);

            return new ResponseEntity<>("Zbor salvat cu succes!", HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Eroare: Valori invalide în formular.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Eroare la salvarea zborului.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
