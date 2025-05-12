package com.proiect.ProiectIsAeroport.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.proiect.ProiectIsAeroport.companie.Model;
import com.proiect.ProiectIsAeroport.companie.Tip_Zbor;
import com.proiect.ProiectIsAeroport.companie.Zbor;
import com.proiect.ProiectIsAeroport.dto.ZborDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class SerializareController {

    private final List<Zbor> zboruri = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
   // private final File zboruriFile = new File("zboruri.json");
    public static void scriere(List<Zbor> lista) {
        try {
            ObjectMapper mapper=new ObjectMapper();
            File file=new File("src/main/resources/zboruri.json");
            mapper.writeValue(file,lista);
        } catch (IOException e) {
            e.printStackTrace();
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
            String zi=formData.get("zi");
            String oraPlecare=formData.get("oraPlecare");
            String lunaStart=formData.get("lunaStart");
            String lunaEnd=formData.get("lunaEnd");

            Zbor zbor = new Zbor(codCursa, tipZbor, rutaDestinatie, rutaPlecare, pretBusiness, pretClasa1, pretEconomie,modelAvion, locuriBusiness,locuriClasa1,locuriEconomie, esteTurRetur, discount);
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
