package com.proiect.ProiectIsAeroport.dto;

import com.proiect.ProiectIsAeroport.companie.Model;
import com.proiect.ProiectIsAeroport.companie.Tarife;
import com.proiect.ProiectIsAeroport.companie.Tip_Zbor;

import java.time.LocalDate;
import java.time.LocalTime;

public class ZborDto {
    public String codSursa;
    public Tip_Zbor tipZbor;
    public String oras_plecare;
    public String oras_destinatie;
    public Tarife tarife;
    public Model model;
    public boolean esteTurRetur;
    public LocalDate ziua;
    public LocalTime ora;
    public LocalDate inceput;
    public LocalDate sfarit;
    public int discount;
}
