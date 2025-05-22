package com.proiect.ProiectIsAeroport.companie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonTypeName("REGULAT")
public class Zbor_Regulat extends Zbor {
    private String zi;
    private String oraPlecare;

    public Zbor_Regulat() {
        super();
    }

    public Zbor_Regulat(String cod_cursa, Tip_Zbor tip_zbor, String oras_destinatie, String oras_plecare, double tarifeBusiness, double tarifeClasa1, double tarifeEconomie, Model model, int locuriBusiness, int locuriClasa1, int locuriEconomie, boolean esteTurRetur, int discount, String zi, String ora) {
        super(cod_cursa, tip_zbor, oras_destinatie, oras_plecare, tarifeBusiness, tarifeClasa1, tarifeEconomie, model, locuriBusiness, locuriClasa1, locuriEconomie, esteTurRetur, discount);
        this.zi = zi;
        this.oraPlecare = ora;
    }

    @JsonProperty("zi")
    public String getZiua() {
        return zi;
    }

    @JsonProperty("zi")
    public void setZiua(String ziua) {
        this.zi = ziua;
    }

    @JsonProperty("oraPlecare")
    public String getOraPlecare() {
        return oraPlecare;
    }

    @JsonProperty("oraPlecare")
    public void setOraPlecare(String oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    @Override
    public String getType() {
        return "REGULAT";
    }
}