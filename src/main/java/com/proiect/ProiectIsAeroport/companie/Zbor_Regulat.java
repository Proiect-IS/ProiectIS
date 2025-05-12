package com.proiect.ProiectIsAeroport.companie;

import java.time.LocalDate;
import java.time.LocalTime;

public class Zbor_Regulat extends Zbor {
    private LocalDate ziua;
    private LocalTime ora;

    public Zbor_Regulat(String cod_cursa, Tip_Zbor tip_zbor, String oras_destinatie, String oras_plecare, Tarife tarife, Avion avion, boolean esteTurRetur, int discount, LocalDate ziua, LocalTime ora) {
        super(cod_cursa, tip_zbor, oras_destinatie, oras_plecare, tarife, avion, esteTurRetur, discount);
        this.ziua = ziua;
        this.ora = ora;
    }

    public LocalDate getZiua() {
        return ziua;
    }

    public void setZiua(LocalDate ziua) {
        this.ziua = ziua;
    }

    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }
}
