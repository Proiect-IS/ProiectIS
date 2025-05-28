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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @PostMapping("/salveazaZbor")
    public ResponseEntity<String> salveazaZbor(@RequestBody java.util.Map<String, String> formData) {
        try {
            String codCursa = formData.get("codCursa");
            Tip_Zbor tipZbor = Tip_Zbor.valueOf(formData.get("tipZbor").toUpperCase());
            String rutaPlecare = formData.get("rutaPlecare");
            String rutaDestinatie = formData.get("rutaDestinatie");
            double pretClasa1 = Integer.parseInt(formData.get("pretClasa1"));
            double pretBusiness = Integer.parseInt(formData.get("pretClasaBusiness"));
            double pretEconomie = Integer.parseInt(formData.get("pretClasaEconomie"));
            Model modelAvion = Model.valueOf(formData.get("modelAvion").toUpperCase());
            int locuriClasa1 = Integer.parseInt(formData.get("locuriClasa1"));
            int locuriBusiness = Integer.parseInt(formData.get("locuriClasaBusiness"));
            int locuriEconomie = Integer.parseInt(formData.get("locuriClasaEconomie"));
            boolean esteTurRetur = "true".equalsIgnoreCase(formData.get("esteTurRetur"));
            int discount = Integer.parseInt(formData.get("discount"));
            Zbor zbor;
            if(esteTurRetur && discount == 0) {
                pretClasa1=pretClasa1*0.95;
                pretBusiness=pretBusiness*0.95;
                pretEconomie=pretEconomie*0.95;
            } else if (esteTurRetur && (discount!=0)) {
                pretClasa1=pretClasa1*(1-(discount/100));
                pretBusiness=pretBusiness*(1-(discount/100));
                pretEconomie=pretEconomie*(1-(discount/100));
            }


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
                return new ResponseEntity<>("Eroare: Tip de zbor necunoscut.", HttpStatus.BAD_REQUEST);
            }

            // Citim zborurile existente
            List<Zbor> existingZboruri = new ArrayList<>();
            if (zboruriFile.exists() && zboruriFile.length() > 0) {
                try {
                    existingZboruri = objectMapper.readValue(zboruriFile, new TypeReference<List<Zbor>>() {});
                } catch (IOException e) {
                    String mesajErr = "Eroare la citirea zborurilor existente: {}" + e.getMessage();
                    return new ResponseEntity<>(mesajErr, HttpStatus.INTERNAL_SERVER_ERROR); // Important: Return error
                }
            }

            existingZboruri.add(zbor);
            scriere(existingZboruri);
            return new ResponseEntity<>("Zbor salvat cu succes!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Eroare: Valori invalide în formular.", HttpStatus.BAD_REQUEST);
        }
    }
}
