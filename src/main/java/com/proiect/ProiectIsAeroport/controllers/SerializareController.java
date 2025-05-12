package com.proiect.ProiectIsAeroport.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.proiect.ProiectIsAeroport.dto.ZborDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class SerializareController {
    @PostMapping("/adaugareZbor")
    public ResponseEntity<String> salveazaZbor(@RequestBody ZborDto zborDto) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            File file = new File("zboruri.json");
            List<ZborDto> listaZboruri = new ArrayList<>();

            if (file.exists()) {
                ZborDto[] existente = objectMapper.readValue(file, ZborDto[].class);
                listaZboruri.addAll(Arrays.asList(existente));
            }

            listaZboruri.add(zborDto);
            objectMapper.writeValue(file, listaZboruri);
            return ResponseEntity.ok("Zbor Adaugat");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ERROR: " + e.getMessage());
        }
    }
}
