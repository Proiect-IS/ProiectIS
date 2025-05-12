package com.proiect.ProiectIsAeroport.companie;

import java.time.LocalDate;
import java.time.LocalTime;

public class Zbor_Regulat extends Zbor {
    private String ziua;
    private String ora;

    public Zbor_Regulat(String cod_cursa, Tip_Zbor tip_zbor, String oras_destinatie, String oras_plecare, int tarifeBusiness, int tarifeClasa1, int tarifeEconomie, Avion avion, boolean esteTurRetur, int discount, String ziua, String ora) {
        super(cod_cursa, tip_zbor, oras_destinatie, oras_plecare, tarifeBusiness, tarifeClasa1, tarifeEconomie, avion, esteTurRetur, discount);
        this.ziua = ziua;
        this.ora = ora;
    }

    public String getZiua() {
        return ziua;
    }

    public void setZiua(String ziua) {
        this.ziua = ziua;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }
}
