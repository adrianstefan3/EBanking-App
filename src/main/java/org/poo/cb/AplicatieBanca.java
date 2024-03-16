package org.poo.cb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

//==========SINGLETON==========
public class AplicatieBanca {
    private static AplicatieBanca instantaUnica;
    ArrayList<Utilizator> utilizatori = new ArrayList<Utilizator>();
    private String[][] tabelValutar = null;
    private String[][] tabelActiuni = null;
    ArrayList<String> recommendedStocks = new ArrayList<String>();

    private AplicatieBanca() {
        this.utilizatori = new ArrayList<Utilizator>();
        this.recommendedStocks = new ArrayList<String>();
    }

    public static AplicatieBanca Instanta() {
        if (instantaUnica == null) {
            instantaUnica = new AplicatieBanca();
        }
        return instantaUnica;
    }

    public void seteazaTabelValutar(String path) {
        ArrayList<String[]> liniiValute = new ArrayList<>();
        try {
            File fisier = new File(path);
            Scanner scan = new Scanner(fisier);

            while (scan.hasNextLine()) {
                String linie = scan.nextLine();
                String[] valuta = linie.split(",");
                liniiValute.add(valuta);
            }

            String[][] tabel = new String[liniiValute.size()][];
            for (int i = 0; i < liniiValute.size(); i++) {
                tabel[i] = liniiValute.get(i);
            }
            this.tabelValutar = tabel;
            scan.close();
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void seteazaTabelActiuni(String path) {
        ArrayList<String[]> liniiActiuni = new ArrayList<>();
        try {
            File fisier = new File(path);
            Scanner scan = new Scanner(fisier);

            while (scan.hasNextLine()) {
                String linie = scan.nextLine();
                String[] actiune = linie.split(",");
                liniiActiuni.add(actiune);
            }

            String tabel[][] = new String[liniiActiuni.size()][];
            for (int i = 0; i < liniiActiuni.size(); i++) {
                tabel[i] = liniiActiuni.get(i);
            }
            this.tabelActiuni = tabel;
            scan.close();
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[][] getTabelValutar() {
        return this.tabelValutar;
    }

    public String[][] getTabelActiuni() {
        return this.tabelActiuni;
    }

    public void Distruge() {
        this.utilizatori.clear();
        this.recommendedStocks.clear();
        AplicatieBanca.instantaUnica = null;
    }
}
