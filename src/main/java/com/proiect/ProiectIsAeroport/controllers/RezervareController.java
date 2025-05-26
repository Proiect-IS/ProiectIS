package com.proiect.ProiectIsAeroport.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proiect.ProiectIsAeroport.companie.Rezervare;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rezervari")
public class RezervareController {

    private List<Rezervare> rezervari = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File rezervariFile = new File("src/main/resources/rezervari.json");

    public RezervareController() {
        try {
            if (rezervariFile.exists() && rezervariFile.length() > 0) {
                rezervari = objectMapper.readValue(rezervariFile, new TypeReference<List<Rezervare>>() {});
            } else {
                rezervariFile.createNewFile();
                rezervari = new ArrayList<>();
            }
        } catch (IOException e) {
            rezervari = new ArrayList<>();
        }
    }

    public void salveazaRezervari() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(rezervariFile, rezervari);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/adauga")
    public String adaugaRezervare(@RequestParam("codCursa") String codCursa,
                                  @RequestParam("nume") String nume,
                                  @RequestParam("telefon") String telefon,
                                  @RequestParam("adulti") int nrAdulti,
                                  @RequestParam("copii") int nrCopii,
                                  @RequestParam("seniori") int nrSeniori,
                                  @RequestParam(value = "masaInclusa", required = false) Boolean masaInclusa,
                                  @RequestParam(value = "bagajSuplimentar", required = false) Boolean bagajSuplimentar,
                                  @RequestParam("clasa") String clasa,
                                  @RequestParam("esteTurRetur") boolean turRetur,
                                  @RequestParam("metodaPlata") String plataCuCard,
                                  @RequestParam("tarifeBusiness") double tarifeBusiness,
                                  @RequestParam("tarifeEconomie") double tarifeEconomie,
                                  @RequestParam("tarifeClasa1") double tarifeClasa1,
                                  org.springframework.ui.Model model) {
        boolean plata;
        if(plataCuCard.equals("cash")) {
            plata=false;
        }else plata=true;
        double pretTotal=0;
        if(clasa.equalsIgnoreCase("business")) {
            pretTotal=nrAdulti*tarifeBusiness+nrCopii*tarifeBusiness+nrSeniori*tarifeBusiness;
        } else if (clasa.equalsIgnoreCase("economie")) {
            pretTotal=nrAdulti*tarifeEconomie+nrCopii*tarifeEconomie+nrSeniori*tarifeEconomie;

        }else if(clasa.equalsIgnoreCase("clasa 1")) {
            pretTotal=nrAdulti*tarifeClasa1+nrCopii*tarifeClasa1+nrSeniori*tarifeClasa1;
        }
        if(masaInclusa && bagajSuplimentar) {
            pretTotal=pretTotal*1.1;
        } else if (masaInclusa) {
            pretTotal=pretTotal*1.05;

        }else if(bagajSuplimentar) {
            pretTotal=pretTotal*1.05;
        }


        Rezervare rezervare=new Rezervare(codCursa,nume,telefon,nrAdulti,nrCopii,nrSeniori,masaInclusa,bagajSuplimentar,clasa,turRetur,plata,pretTotal);

        rezervari.add(rezervare);
        salveazaRezervari();

        model.addAttribute("mesajRezervare", "Rezervarea a fost înregistrată cu succes!");
        return "client_web/confirmareRezervare";
    }
    @PostMapping("/preconfirmare")
    public String preconfiramerRezervare(@RequestParam("codCursa") String codCursa,
                                  @RequestParam("nume") String nume,
                                  @RequestParam("telefon") String telefon,
                                  @RequestParam("adulti") int nrAdulti,
                                  @RequestParam("copii") int nrCopii,
                                  @RequestParam("seniori") int nrSeniori,
                                  @RequestParam(value = "masaInclusa", required = false) Boolean masaInclusa,
                                  @RequestParam(value = "bagajSuplimentar", required = false) Boolean bagajSuplimentar,
                                  @RequestParam("clasa") String clasa,
                                  @RequestParam("esteTurRetur") boolean turRetur,
                                  @RequestParam("metodaPlata") String plataCuCard,
                                  @RequestParam("tarifeBusiness") double tarifeBusiness,
                                  @RequestParam("tarifeEconomie") double tarifeEconomie,
                                  @RequestParam("tarifeClasa1") double tarifeClasa1,
                                  org.springframework.ui.Model model) {
        boolean plata;
        if(plataCuCard.equals("cash")) {
            plata=false;
        }else plata=true;
        double pretTotal=0;
        if(clasa.equalsIgnoreCase("business")) {
            pretTotal=nrAdulti*tarifeBusiness+nrCopii*tarifeBusiness+nrSeniori*tarifeBusiness;
        } else if (clasa.equalsIgnoreCase("economie")) {
            pretTotal=nrAdulti*tarifeEconomie+nrCopii*tarifeEconomie+nrSeniori*tarifeEconomie;

        }else if(clasa.equalsIgnoreCase("clasa 1")) {
            pretTotal=nrAdulti*tarifeClasa1+nrCopii*tarifeClasa1+nrSeniori*tarifeClasa1;
        }
        if(masaInclusa && bagajSuplimentar) {
            pretTotal=pretTotal*1.1;
        } else if (masaInclusa) {
            pretTotal=pretTotal*1.05;

        }else if(bagajSuplimentar) {
            pretTotal=pretTotal*1.05;
        }


        Rezervare rezervare=new Rezervare(codCursa,nume,telefon,nrAdulti,nrCopii,nrSeniori,masaInclusa,bagajSuplimentar,clasa,turRetur,plata,pretTotal);

        //rezervari.add(rezervare);
        //salveazaRezervari();

        //model.addAttribute("mesajRezervare", "Rezervarea a fost înregistrată cu succes!");
        model.addAttribute("detaliiZbor",rezervare);
        return "client_web/preconfirmareRezervare";
    }

    // ✅ Afișează toate rezervările
    @GetMapping("/toate")
    @ResponseBody
    public List<Rezervare> getRezervari() {
        return rezervari;
    }

    // ✅ Calculează locuri ocupate pentru un zbor și o clasă
    @GetMapping("/locuri-ocupate")
    @ResponseBody
    public int getLocuriOcupate(@RequestParam("codCursa") String codCursa,
                                @RequestParam("clasa") String clasa) {
        return rezervari.stream()
                .filter(r -> r.getCodCursa().equals(codCursa) && r.getClasa().equalsIgnoreCase(clasa))
                .mapToInt(r -> r.getNrAdulti() + r.getNrCopii() + r.getNrSeniori())
                .sum();
    }
}