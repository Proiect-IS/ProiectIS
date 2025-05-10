package com.proiect.ProiectIsAeroport.companie;

public class Avion {
    private Model model;
    private Locuri locuri;

    public Avion(Model model, Locuri locuri) {
        this.model = model;
        this.locuri = locuri;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Locuri getLocuri() {
        return locuri;
    }

    public void setLocuri(Locuri locuri) {
        this.locuri = locuri;
    }
}
