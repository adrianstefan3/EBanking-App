package org.poo.cb;

import java.util.ArrayList;

public class Utilizator {
    private String email;
    private String nume, prenume;
    private String adresa;
    private ArrayList<String> prieteni = null;
    private ArrayList<Cont> conturi = null;
    private ArrayList<Actiune> actiuni = null;
    private ContFactory factory = null;
    boolean premium = false;
    public ObservatorUtilizator observator;

    Utilizator(String email, String prenume, String nume, String adresa) {
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.prieteni = new ArrayList<String>();
        this.conturi = new ArrayList<Cont>();
        this.actiuni = new ArrayList<Actiune>();
        this.factory = new ContFactory();
        this.premium = false;
        this.observator = new ObservatorUtilizator(this);
    }

    public boolean isPremium() {
        return this.premium;
    }
    
    public void setPremium() {
        this.premium = true;
    }
    public String getEmail() {
        return this.email;
    }

    public String getNume() {
        return this.nume;
    }

    public String getPrenume() {
        return this.prenume;
    }

    public String getAdresa() {
        return this.adresa;
    }

    public ArrayList<String> getPrieteni() {
        return this.prieteni;
    }

    public void adaugaCont(String valuta) {
        Cont cont = factory.creeazaCont(valuta);
        conturi.add(cont);
    }

    public void adaugaActiune(String nume, double cantitate) {
        Actiune actiune = new Actiune(nume, cantitate);
        actiuni.add(actiune);
    }

    //=====Observer=====
    public void adaugaPrieten(String email, Utilizator prieten) {
        prieteni.add(email);
        notificaObservatorPrieten(prieten, this.email);
    }

    public void notificaObservatorPrieten(Utilizator prieten, String email) {
        observator.actualizeazaListaPrieteni(prieten, email);
    }
    //=====Observer=====
    public ArrayList<Cont> getConturi() {
        return this.conturi;
    }

    public ArrayList<Actiune> getActiuni() {
        return this.actiuni;
    }

    public String toString() {
        String output = "{\"email\":\"" + this.email + "\",\"firstname\":\"" + this.prenume + "\",\"lastname\":\"" + this.nume + "\",\"address\":\"" + this.adresa + "\",";
        output += "\"friends\":[";
        for (int i = 0; i < this.prieteni.size(); i++) {
            output += "\"" + this.prieteni.get(i) + "\"";
            if (i != this.prieteni.size() - 1) {
                output += ",";
            }
        }
        output += "]}";
        return output;
    }
}
