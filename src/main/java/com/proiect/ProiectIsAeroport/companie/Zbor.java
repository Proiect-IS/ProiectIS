package com.proiect.ProiectIsAeroport.companie;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type") // Adăugăm această adnotare la clasa de bază
@JsonSubTypes({
        @JsonSubTypes.Type(value = Zbor_Regulat.class, name = "REGULAT"),
        @JsonSubTypes.Type(value = Zbor_Sezonier.class, name = "SEZONIER")
})
public abstract class Zbor {
    private String cod_cursa;
    private Tip_Zbor tip_zbor;
    private String oras_destinatie;
    private String oras_plecare;
    private double tarifeBusiness;
    private double tarifeClasa1;
    private double tarifeEconomie;
    private Model model;
    private int locuriBusiness;
    private int locuriClasa1;
    private int locuriEconomie;
    private boolean esteTurRetur;
    private int discount;

    public Zbor() {}

    public Zbor(String cod_cursa, Tip_Zbor tip_zbor, String oras_destinatie, String oras_plecare, double tarifeBusiness, double tarifeClasa1, double tarifeEconomie, Model model, int locuriBusiness, int locuriClasa1, int locuriEconomie, boolean esteTurRetur, int discount) {
        this.cod_cursa = cod_cursa;
        this.tip_zbor = tip_zbor;
        this.oras_destinatie = oras_destinatie;
        this.oras_plecare = oras_plecare;
        this.tarifeBusiness = tarifeBusiness;
        this.tarifeClasa1 = tarifeClasa1;
        this.tarifeEconomie = tarifeEconomie;
        this.model = model;
        this.locuriBusiness = locuriBusiness;
        this.locuriClasa1 = locuriClasa1;
        this.locuriEconomie = locuriEconomie;
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

    public double getTarifeBusiness() {
        return tarifeBusiness;
    }

    public void setTarifeBusiness(double tarifeBusiness) {
        this.tarifeBusiness = tarifeBusiness;
    }

    public double getTarifeClasa1() {
        return tarifeClasa1;
    }

    public void setTarifeClasa1(double tarifeClasa1) {
        this.tarifeClasa1 = tarifeClasa1;
    }

    public double getTarifeEconomie() {
        return tarifeEconomie;
    }

    public void setTarifeEconomie(double tarifeEconomie) {
        this.tarifeEconomie = tarifeEconomie;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public int getLocuriBusiness() {
        return locuriBusiness;
    }

    public void setLocuriBusiness(int locuriBusiness) {
        this.locuriBusiness = locuriBusiness;
    }

    public int getLocuriClasa1() {
        return locuriClasa1;
    }

    public void setLocuriClasa1(int locuriClasa1) {
        this.locuriClasa1 = locuriClasa1;
    }

    public int getLocuriEconomie() {
        return locuriEconomie;
    }

    public void setLocuriEconomie(int locuriEconomie) {
        this.locuriEconomie = locuriEconomie;
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

    public int numar_locuri_total()
    {
        return locuriBusiness+locuriClasa1+locuriEconomie+locuriBusiness;
    }

    public abstract String getType();
}