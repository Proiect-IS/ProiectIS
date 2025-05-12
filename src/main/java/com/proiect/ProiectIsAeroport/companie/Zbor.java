package com.proiect.ProiectIsAeroport.companie;

public class Zbor {
    private String cod_cursa;
    private Tip_Zbor tip_zbor;
    private String oras_destinatie;
    private String oras_plecare;
    private int tarifeBusiness;
    private int tarifeClasa1;
    private int tarifeEconomie;
    private Avion avion;
    private boolean esteTurRetur;
    private int discount;

    public Zbor(String cod_cursa, Tip_Zbor tip_zbor, String oras_destinatie, String oras_plecare, int tarifeBusiness, int tarifeClasa1, int tarifeEconomie, Avion avion, boolean esteTurRetur, int discount) {
        this.cod_cursa = cod_cursa;
        this.tip_zbor = tip_zbor;
        this.oras_destinatie = oras_destinatie;
        this.oras_plecare = oras_plecare;
        this.tarifeBusiness = tarifeBusiness;
        this.tarifeClasa1 = tarifeClasa1;
        this.tarifeEconomie = tarifeEconomie;
        this.avion = avion;
        this.esteTurRetur = esteTurRetur;
        this.discount = discount;
    }

    public String getCod_cursa() {
        return cod_cursa;
    }

    public void setCod_cursa(String cod_cursa) {
        this.cod_cursa = cod_cursa;
    }

    public Tip_Zbor getTip_zbor() {
        return tip_zbor;
    }

    public void setTip_zbor(Tip_Zbor tip_zbor) {
        this.tip_zbor = tip_zbor;
    }

    public String getOras_destinatie() {
        return oras_destinatie;
    }

    public void setOras_destinatie(String oras_destinatie) {
        this.oras_destinatie = oras_destinatie;
    }

    public String getOras_plecare() {
        return oras_plecare;
    }

    public void setOras_plecare(String oras_plecare) {
        this.oras_plecare = oras_plecare;
    }

    public int getTarifeBusiness() {
        return tarifeBusiness;
    }

    public void setTarifeBusiness(int tarifeBusiness) {
        this.tarifeBusiness = tarifeBusiness;
    }

    public int getTarifeClasa1() {
        return tarifeClasa1;
    }

    public void setTarifeClasa1(int tarifeClasa1) {
        this.tarifeClasa1 = tarifeClasa1;
    }

    public int getTarifeEconomie() {
        return tarifeEconomie;
    }

    public void setTarifeEconomie(int tarifeEconomie) {
        this.tarifeEconomie = tarifeEconomie;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public boolean isEsteTurRetur() {
        return esteTurRetur;
    }

    public void setEsteTurRetur(boolean esteTurRetur) {
        this.esteTurRetur = esteTurRetur;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}