package com.proiect.ProiectIsAeroport.companie;

public class Tarife {
    private double business;
    private double clasa1;
    private double economie;

    public Tarife(double business, double clasa1, double economie) {
        this.business = business;
        this.clasa1 = clasa1;
        this.economie = economie;
    }

    public double getBusiness() {
        return business;
    }

    public double getClasa1() {
        return clasa1;
    }

    public double getEconomie() {
        return economie;
    }

    public void setBusiness(double business) {
        this.business = business;
    }

    public void setClasa1(double clasa1) {
        this.clasa1 = clasa1;
    }

    public void setEconomie(double economie) {
        this.economie = economie;
    }
}
