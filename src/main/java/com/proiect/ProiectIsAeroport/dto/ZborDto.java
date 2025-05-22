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
    public double pretBusiness;
    public double pretClasa1;
    public double pretEconomie;
    public Model modelAvion;
    public int locuriBusiness;
    public int locuriClasa1;
    public int locuriEconomie;
    public boolean esteTurRetur;
    public String zi;
    public String oraPlecare;
    public String lunaStart;
    public String lunaEnd;
    public int discount;
}
