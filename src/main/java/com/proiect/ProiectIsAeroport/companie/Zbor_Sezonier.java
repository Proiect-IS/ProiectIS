package com.proiect.ProiectIsAeroport.companie;

import java.time.LocalDate;
import java.time.LocalTime;

public class Zbor_Sezonier extends Zbor_Regulat {
    private String inceput;
    private String sfarsit;


    public Zbor_Sezonier() {
        super();
    }

    public Zbor_Sezonier(String cod_cursa, Tip_Zbor tip_zbor, String oras_destinatie, String oras_plecare, int tarifeBusiness, int tarifeClasa1, int tarifeEconomie, Model model, int locuriBusiness, int locuriClasa1, int locuriEconomie, boolean esteTurRetur, int discount, String zi, String ora, String inceput, String sfarsit) {
        super(cod_cursa, tip_zbor, oras_destinatie, oras_plecare, tarifeBusiness, tarifeClasa1, tarifeEconomie, model, locuriBusiness, locuriClasa1, locuriEconomie, esteTurRetur, discount, zi, ora);
        this.inceput = inceput;
        this.sfarsit = sfarsit;
    }

    public String getInceput() {
        return inceput;
    }

    public void setInceput(String inceput) {
        this.inceput = inceput;
    }

    public String getSfarsit() {
        return sfarsit;
    }

    public void setSfarsit(String sfarsit) {
        this.sfarsit = sfarsit;
    }
}
