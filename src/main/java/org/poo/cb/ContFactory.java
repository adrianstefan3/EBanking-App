package org.poo.cb;

//=====Factory Method=====
public class ContFactory {
    public Cont creeazaCont(String valuta) {
        switch (valuta) {
            case "EUR":
                return new ContEUR();
            case "GBP":
                return new contGBP();
            case "JPY":
                return new contJPY();
            case "CAD":
                return new contCAD();
            case "USD":
                return new contUSD();
            default:
                return null;
        }
    }
}
