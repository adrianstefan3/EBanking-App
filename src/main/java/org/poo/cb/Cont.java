package org.poo.cb;

public abstract class Cont {
    private String valuta;
    private double soldCont;
    public Cont(String valuta) {
        this.valuta = valuta;
        this.soldCont = 0.0;
    }

    public String getValuta() {
        return this.valuta;
    }

    public void adaugaBani(double valoare) {
        this.soldCont += valoare;
    }

    public double getSoldCont() {
        return this.soldCont;
    }

    public void scadeBani(double valoare) {
        this.soldCont -= valoare;
    }

    public void setSoldCont(double valoare) {
        this.soldCont = valoare;
    }
}

class ContEUR extends Cont {
    public ContEUR() {
        super("EUR");
    }
}

class contGBP extends Cont {
    public contGBP() {
        super("GBP");
    }
}

class contJPY extends Cont {
    public contJPY() {
        super("JPY");
    }
}

class contCAD extends Cont {
    public contCAD() {
        super("CAD");
    }
}

class contUSD extends Cont {
    public contUSD() {
        super("USD");
    }
}

