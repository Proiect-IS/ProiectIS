package com.proiect.ProiectIsAeroport.companie;

import java.time.LocalDate;
import java.time.LocalTime;

public class Zbor_Sezonier extends Zbor_Regulat{
    private LocalDate inceput;
    private LocalDate sfasit;
    private LocalDate luna;

    public Zbor_Sezonier(String cod_cursa, Tip_Zbor tip_zbor, String oras_destinatie, String oras_plecare, Tarife tarife, Avion avion, boolean esteTurRetur, LocalDate ziua, LocalTime ora, LocalDate inceput, LocalDate sfasit, LocalDate luna) {
        super(cod_cursa, tip_zbor, oras_destinatie, oras_plecare, tarife, avion, esteTurRetur, ziua, ora);
        this.inceput = inceput;
        this.sfasit = sfasit;
        this.luna = luna;
    }

    public LocalDate getLuna() {
        return luna;
    }

    public void setLuna(LocalDate luna) {
        this.luna = luna;
    }

    public LocalDate getSfasit() {
        return sfasit;
    }

    public void setSfasit(LocalDate sfasit) {
        this.sfasit = sfasit;
    }

    public LocalDate getInceput() {
        return inceput;
    }

    public void setInceput(LocalDate inceput) {
        this.inceput = inceput;
    }
}
