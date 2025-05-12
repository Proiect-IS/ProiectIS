package com.proiect.ProiectIsAeroport.companie;

import java.time.LocalDate;
import java.time.LocalTime;

public class Zbor_Sezonier extends Zbor_Regulat {
    private LocalDate inceput;
    private LocalDate sfasit;

    public Zbor_Sezonier(String cod_cursa, Tip_Zbor tip_zbor, String oras_destinatie, String oras_plecare, Tarife tarife, Avion avion, boolean esteTurRetur, int discount, LocalDate ziua, LocalTime ora, LocalDate inceput, LocalDate sfasit) {
        super(cod_cursa, tip_zbor, oras_destinatie, oras_plecare, tarife, avion, esteTurRetur, discount, ziua, ora);
        this.inceput = inceput;
        this.sfasit = sfasit;
    }

    public LocalDate getInceput() {
        return inceput;
    }

    public void setInceput(LocalDate inceput) {
        this.inceput = inceput;
    }

    public LocalDate getSfasit() {
        return sfasit;
    }

    public void setSfasit(LocalDate sfasit) {
        this.sfasit = sfasit;
    }
}
