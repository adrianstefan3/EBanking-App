package org.poo.cb;

public class Actiune {
    private String nume;
    private double cantitate;

    public Actiune(String nume, double cantitate) {
        this.nume = nume;
        this.cantitate = cantitate;
    }

    public String getNume() {
        return this.nume;
    }

    public double getCantitate() {
        return this.cantitate;
    }
}
