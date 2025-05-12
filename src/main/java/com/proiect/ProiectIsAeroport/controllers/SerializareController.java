package com.proiect.ProiectIsAeroport.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.proiect.ProiectIsAeroport.dto.ZborDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class SerializareController {

    @PostMapping("/salveazaZbor")
    public ResponseEntity<String> salveazaZbor(@RequestBody ZborDto zborDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Loghează pentru debug
            System.out.println("Date primite: " + objectMapper.writeValueAsString(zborDto));

            File file = new File("zboruri.json");
            System.out.println("Scriere in: " + file.getAbsolutePath());

            List<ZborDto> listaZboruri = new ArrayList<>();

            if (file.exists() && file.length() > 0) {
                try {
                    ZborDto[] existente = objectMapper.readValue(file, ZborDto[].class);
                    listaZboruri.addAll(Arrays.asList(existente));
                } catch (Exception e) {
                    System.err.println("Eroare la citirea JSON-ului existent: " + e.getMessage());
                }
            }

            listaZboruri.add(zborDto);

            objectMapper.writeValue(file, listaZboruri);

            return ResponseEntity.ok("Zbor adăugat cu succes!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Eroare la salvare: " + e.getMessage());
        }
    }
}
