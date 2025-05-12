package com.proiect.ProiectIsAeroport.dto;

import com.proiect.ProiectIsAeroport.companie.Model;
import com.proiect.ProiectIsAeroport.companie.Tip_Zbor;

import java.time.LocalDate;
import java.time.LocalTime;

public class ZborDto {
    public String codCursa;
    public Tip_Zbor tipZbor;
    public String orasPlecare;
    public String orasDestinatie;
    public int tarifeBusiness;
    public int tarifeClasa1;
    public int tarifeEconomie;
    public Model model;
    public boolean esteTurRetur;
    public String ziua;
    public String ora;
    public String inceput;
    public String sfarsit;
    public int discount;
}
