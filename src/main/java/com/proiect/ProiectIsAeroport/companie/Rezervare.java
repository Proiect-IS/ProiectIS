package com.proiect.ProiectIsAeroport.companie;

public class Rezervare {
    private String codCursa;
    private String numePasager;
    private String telefon;
    private int nrAdulti;
    private int nrCopii;
    private int nrSeniori;
    private boolean masaInclusa;
    private boolean bagajSuplimentar;
    private String clasa;
    private boolean turRetur;
    private boolean plataCuCard;

    public Rezervare() {
    }

    public Rezervare(String codCursa, String numePasager, String telefon, int nrAdulti, int nrCopii, int nrSeniori, boolean masaInclusa, boolean bagajSuplimentar, String clasa, boolean turRetur, boolean plataCuCard) {
        this.codCursa = codCursa;
        this.numePasager = numePasager;
        this.telefon = telefon;
        this.nrAdulti = nrAdulti;
        this.nrCopii = nrCopii;
        this.nrSeniori = nrSeniori;
        this.masaInclusa = masaInclusa;
        this.bagajSuplimentar = bagajSuplimentar;
        this.clasa = clasa;
        this.turRetur = turRetur;
        this.plataCuCard = plataCuCard;
    }

    public String getCodCursa() {
        return codCursa;
    }

    public void setCodCursa(String codCursa) {
        this.codCursa = codCursa;
    }

    public String getNumePasager() {
        return numePasager;
    }

    public void setNumePasager(String numePasager) {
        this.numePasager = numePasager;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public int getNrAdulti() {
        return nrAdulti;
    }

    public void setNrAdulti(int nrAdulti) {
        this.nrAdulti = nrAdulti;
    }

    public int getNrCopii() {
        return nrCopii;
    }

    public void setNrCopii(int nrCopii) {
        this.nrCopii = nrCopii;
    }

    public int getNrSeniori() {
        return nrSeniori;
    }

    public void setNrSeniori(int nrSeniori) {
        this.nrSeniori = nrSeniori;
    }

    public boolean isMasaInclusa() {
        return masaInclusa;
    }

    public void setMasaInclusa(boolean masaInclusa) {
        this.masaInclusa = masaInclusa;
    }

    public boolean isBagajSuplimentar() {
        return bagajSuplimentar;
    }

    public void setBagajSuplimentar(boolean bagajSuplimentar) {
        this.bagajSuplimentar = bagajSuplimentar;
    }

    public String getClasa() {
        return clasa;
    }

    public void setClasa(String clasa) {
        this.clasa = clasa;
    }

    public boolean isTurRetur() {
        return turRetur;
    }

    public void setTurRetur(boolean turRetur) {
        this.turRetur = turRetur;
    }

    public boolean isPlataCuCard() {
        return plataCuCard;
    }

    public void setPlataCuCard(boolean plataCuCard) {
        this.plataCuCard = plataCuCard;
    }
}
