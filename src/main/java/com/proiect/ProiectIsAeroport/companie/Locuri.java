package com.proiect.ProiectIsAeroport.companie;

public class Locuri {
    private int business;
    private int clasa1;
    private int economie;

    public Locuri(int business, int clasa1, int economie) {
        this.business = business;
        this.clasa1 = clasa1;
        this.economie = economie;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public int getClasa1() {
        return clasa1;
    }

    public void setClasa1(int clasa1) {
        this.clasa1 = clasa1;
    }

    public int getEconomie() {
        return economie;
    }

    public void setEconomie(int economie) {
        this.economie = economie;
    }

    public int nr_locuri_totale()
    {
        return business+clasa1+economie;
    }
}
