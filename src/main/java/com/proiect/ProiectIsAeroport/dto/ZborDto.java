package com.proiect.ProiectIsAeroport.dto;

import com.proiect.ProiectIsAeroport.companie.Model;
import com.proiect.ProiectIsAeroport.companie.Tip_Zbor;

import java.time.LocalDate;
import java.time.LocalTime;

public class ZborDto {
    public String codCursa;
    public Tip_Zbor tipZbor;
    public String rutaPlecare;
    public String rutaDestinatie;
    public int pretBusiness;
    public int pretClasa1;
    public int pretEconomie;
    public Model modelAvion;
    public boolean esteTurRetur;
    public String zi;
    public String oraPlecare;
    public String lunaStart;
    public String lunaEnd;
    public int discount;
}
