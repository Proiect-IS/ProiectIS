package com.proiect.ProiectIsAeroport.companie;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class Zbor_Regulat extends Zbor {
    private String zi;
    private String ora;

    public Zbor_Regulat() {
        super();
    }

    public Zbor_Regulat(String cod_cursa, Tip_Zbor tip_zbor, String oras_destinatie, String oras_plecare, int tarifeBusiness, int tarifeClasa1, int tarifeEconomie, Model model, int locuriBusiness, int locuriClasa1, int locuriEconomie, boolean esteTurRetur, int discount, String ziua, String ora) {
        super(cod_cursa, tip_zbor, oras_destinatie, oras_plecare, tarifeBusiness, tarifeClasa1, tarifeEconomie, model, locuriBusiness, locuriClasa1, locuriEconomie, esteTurRetur, discount);
        this.zi = zi;
        this.ora = ora;
    }

    @JsonProperty("ziua")
    public String getZiua() {
        return zi;
    }

    @JsonProperty("ziua")
    public void setZiua(String ziua) {
        this.zi = ziua;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }
}