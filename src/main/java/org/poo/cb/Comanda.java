package org.poo.cb;

import java.lang.reflect.Array;
import java.util.ArrayList;

//=====Command=====
public interface Comanda {
    void executa();
}

class CreateUser implements Comanda {
    private String email;
    private String nume, prenume;
    private String adresa;
    ArrayList<Utilizator> utilizatori = null;

    public CreateUser(String email, String prenume, String nume, String adresa, ArrayList<Utilizator> utilizatori) {
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.utilizatori = utilizatori;
    }

    public void executa() {
        boolean exista = false;
        for (Utilizator utilizator : this.utilizatori) {
            if (utilizator.getEmail().equals(email)) {
                exista = true;
                break;
            }
        }
        if (exista == false) {
            Utilizator utilizator = new Utilizator(email, prenume, nume, adresa);
            this.utilizatori.add(utilizator);
        } else {
            System.out.println("User with " + email + " already exists");
        }
    }
}

class AddFriend implements Comanda {
    private String email;
    private String emailPrieten;
    ArrayList<Utilizator> utilizatori = null;

    public AddFriend(String email, String emailPrieten, ArrayList<Utilizator> utilizatori) {
        this.email = email;
        this.emailPrieten = emailPrieten;
        this.utilizatori = utilizatori;
    }

    public void executa() {
        Utilizator userCurent = getUtilizatorByEmail(email);
        Utilizator userPrieten = getUtilizatorByEmail(emailPrieten);

        if (userCurent == null) {
            System.out.println("User with " + email + " doesn't exist");
        }
        if (userPrieten == null) {
            System.out.println("User with " + emailPrieten + " doesn't exist");
        }
        if (userCurent == null || userPrieten == null) {
            return;
        }

        if (userCurent.getPrieteni().contains(emailPrieten)) {
            System.out.println("User with " + emailPrieten + " is already friend");
            return;
        }

        userCurent.adaugaPrieten(emailPrieten, userPrieten);
    }

    private Utilizator getUtilizatorByEmail(String email) {
        for (Utilizator utilizator : this.utilizatori) {
            if (utilizator.getEmail().equals(email)) {
                return utilizator;
            }
        }
        return null;
    }
}

class AddAccount implements Comanda {
    private String email;
    private String valuta;
    ArrayList<Utilizator> utilizatori = null;

    public AddAccount(String email, String valuta, ArrayList<Utilizator> utilizatori) {
        this.email = email;
        this.valuta = valuta;
        this.utilizatori = utilizatori;
    }

    public void executa() {
        Utilizator user = null;
        for (Utilizator utilizator : this.utilizatori) {
            if (utilizator.getEmail().equals(email)) {
                user = utilizator;
                break;
            }
        }

        ArrayList<Cont> conturi = user.getConturi();

        for (Cont cont : conturi) {
            if (cont.getValuta().equals(valuta)) {
                System.out.println("Account in currency " + valuta + " already exists for user");
                return;
            }
        }

        user.adaugaCont(valuta);
    }
}

class AddMoney implements Comanda {
    private String email;
    private String valuta;
    private double valoare;
    ArrayList<Utilizator> utilizatori = null;

    public AddMoney(String email, String valuta, double valoare, ArrayList<Utilizator> utilizatori) {
        this.email = email;
        this.valuta = valuta;
        this.valoare = valoare;
        this.utilizatori = utilizatori;
    }

    public void executa() {
        Utilizator user = null;
        for (Utilizator utilizator : this.utilizatori) {
            if (utilizator.getEmail().equals(email)) {
                user = utilizator;
                break;
            }
        }
        
        for (Cont cont : user.getConturi()) {
            if (cont.getValuta().equals(valuta)) {
                cont.adaugaBani(this.valoare);
                return;
            }
        }
    }
}

class ExchangeMoney implements Comanda {
    private String email;
    private String valutaSursa;
    private String valutaDestinatie;
    private double valoare;
    ArrayList<Utilizator> utilizatori = null;
    private String[][] tabelValutar = null;

    public ExchangeMoney(String email, String valutaSursa, String valutaDestinatie, double valoare, ArrayList<Utilizator> utilizatori, String[][] tabelValutar)  {
        this.email = email;
        this.valutaSursa = valutaSursa;
        this.valutaDestinatie = valutaDestinatie;
        this.valoare = valoare;
        this.utilizatori = utilizatori;
        this.tabelValutar = tabelValutar;
    }

    public void executa() {
        Utilizator user = null;
        for (Utilizator utilizator : this.utilizatori) {
            if (utilizator.getEmail().equals(email)) {
                user = utilizator;
                break;
            }
        }

        Cont contSursa = null;
        Cont contDestinatie = null;

        for (Cont cont : user.getConturi()) {
            if (cont.getValuta().equals(valutaSursa)) {
                contSursa = cont;
            }
            if (cont.getValuta().equals(valutaDestinatie)) {
                contDestinatie = cont;
            }
        }

        double procent = 0;

        for (int i = 0; i < tabelValutar.length; i++) {
            if (tabelValutar[i][0].equals(contDestinatie.getValuta())) {
                for (int j = 0; j < tabelValutar[0].length; j++) {
                    if (tabelValutar[0][j].equals(contSursa.getValuta())) {
                        procent = Double.parseDouble(tabelValutar[i][j]);
                        break;
                    }
                }
            }
        }

        double sumaScadenta = valoare * procent;

        if (contSursa.getSoldCont() < valoare) {
            System.out.println("Insufficient amount in account " + this.valutaSursa + " for exchange");
            return;
        }

        if ((contSursa.getSoldCont() / 2 < sumaScadenta) && user.isPremium() == false) {
            contSursa.scadeBani(0.01 * sumaScadenta);
        }

        contSursa.scadeBani(sumaScadenta);
        contDestinatie.adaugaBani(valoare);
    }
}

class TransferMoney implements Comanda {
    private String email;
    private String emailPrieten;
    private String valuta;
    private double valoare;
    ArrayList<Utilizator> utilizatori = null;

    public TransferMoney(String email, String emailPrieten, String valuta, double valoare, ArrayList<Utilizator> utilizatori) {
        this.email = email;
        this.emailPrieten = emailPrieten;
        this.valuta = valuta;
        this.valoare = valoare;
        this.utilizatori = utilizatori;
    }

    public void executa() {
        Utilizator userCurent = null;
        Utilizator userPrieten = null;
        for (Utilizator utilizator : this.utilizatori) {
            if (utilizator.getEmail().equals(email)) {
                userCurent = utilizator;
            } else if (utilizator.getEmail().equals(emailPrieten)) {
                userPrieten = utilizator;
            }
        }
        
        Cont contSursa = null;
        ArrayList<Cont> conturi = userCurent.getConturi();
        for (Cont cont : conturi) {
            if (cont.getValuta().equals(valuta)) {
                contSursa = cont;
                break;
            }
        }

        if (contSursa.getSoldCont() < valoare) {
            System.out.println("Insufficient amount in account " + this.valuta + " for transfer");
        }
        if (userCurent.getPrieteni().contains(emailPrieten) == false) {
            System.out.println("You are not allowed to transfer money to " + emailPrieten);
        }

        Cont contDestinatie = null;
        conturi = userPrieten.getConturi();

        for (Cont cont : conturi) {
            if (cont.getValuta().equals(valuta)) {
                contDestinatie = cont;
                break;
            }
        }

        contSursa.scadeBani(valoare);
        contDestinatie.adaugaBani(valoare);
    }
}

class BuyStocks implements Comanda {
    private String email;
    private String numeActiune;
    private double cantitate;
    ArrayList<Utilizator> utilizatori = null;
    private String[][] tabelActiuni = null;
    ArrayList<String> recommendedStocks = new ArrayList<String>();

    public BuyStocks(String email, String numeActiune, double cantitate, ArrayList<Utilizator> utilizatori, String[][] tabelActiuni, ArrayList<String> recommendedStocks) {
        this.email = email;
        this.numeActiune = numeActiune;
        this.cantitate = cantitate;
        this.utilizatori = utilizatori;
        this.tabelActiuni = tabelActiuni;
        this.recommendedStocks = recommendedStocks;
    }

    public void executa() {
        Utilizator user = null;
        for (Utilizator utilizator : this.utilizatori) {
            if (utilizator.getEmail().equals(email)) {
                user = utilizator;
                break;
            }
        }

        double procent = 0;
        for (int i = 0; i < tabelActiuni.length; i++) {
            if (tabelActiuni[i][0].equals(this.numeActiune)) {
                procent = Double.parseDouble(tabelActiuni[i][10]);
                break;
            }
        }

        ArrayList<Cont> conturi = user.getConturi();
        for (Cont cont : conturi) {
            if (cont.getValuta().equals("USD")) {
                double sumaScadenta = cantitate * procent;
                if (this.recommendedStocks.contains(String.valueOf(this.numeActiune)) && user.isPremium() == true) {
                    sumaScadenta = sumaScadenta * 0.95;
                }
                if (cont.getSoldCont() < sumaScadenta) {
                    System.out.println("Insufficient amount in account for buying stock");
                    return;
                }
                cont.scadeBani(sumaScadenta);
            }
        }

        user.adaugaActiune(this.numeActiune, this.cantitate);
    }
}

class RecommendStocks implements Comanda {
    private String[][] tabelActiuni = null;
    private ArrayList<String> recommendedStocks = new ArrayList<String>();

    public RecommendStocks(String[][] tabelActiuni, ArrayList<String> recommendedStocks) {
        this.tabelActiuni = tabelActiuni;
        this.recommendedStocks = recommendedStocks;
    }

    public void executa() {
        String output = "{\"stocksToBuy\":[";
        ArrayList<String> recommendedStocks = new ArrayList<String>();

        for (int i = 1; i < tabelActiuni.length; i++) {
            double SMA_5 = 0;
            double SMA_10 = 0;

            for (int j = 1; j <= 10; j++) {
                if (j > 5)
                    SMA_5 += Double.parseDouble(tabelActiuni[i][j]);
                SMA_10 += Double.parseDouble(tabelActiuni[i][j]);
            }
            SMA_10 /= 10.0;
            SMA_5 /= 5.0;
            if (SMA_5 > SMA_10) {
                recommendedStocks.add(tabelActiuni[i][0]);
            }
        }
        this.recommendedStocks.addAll(recommendedStocks);
        for (int i = 0; i < recommendedStocks.size(); i++) {
            output += "\"" + recommendedStocks.get(i) + "\"";
            if (i != recommendedStocks.size() - 1) {
                output += ",";
            }
        }
        output += "]}";
        System.out.println(output);
    }
}

class ListPortfolio implements Comanda {
    private String email;
    ArrayList<Utilizator> utilizatori = null;

    public ListPortfolio(String email, ArrayList<Utilizator> utilizatori) {
        this.email = email;
        this.utilizatori = utilizatori;
    }

    public void executa() {
        Utilizator user = null;
        for (Utilizator utilizator : this.utilizatori) {
            if (utilizator.getEmail().equals(email)) {
                user = utilizator;
                break;
            }
        }

        if (user == null) {
            System.out.println("User with " + email + " doesn't exist");
            return;
        }

        String output = "{\"stocks\":[";
        ArrayList<Actiune> actiuni = user.getActiuni();
        for (int i = 0; i < actiuni.size(); i++) {
            String cantitate = String.valueOf((int)actiuni.get(i).getCantitate());
            output += "{\"stockName\":\"" + actiuni.get(i).getNume() + "\",\"amount\":" + cantitate + "}";
            if (i != actiuni.size() - 1) {
                output += ",";
            }
        }
        output += "],\"accounts\":[";
        ArrayList<Cont> conturi = user.getConturi();
        for (int i = 0; i < conturi.size(); i++) {
            String sold = String.format("%.2f", conturi.get(i).getSoldCont());
            output += "{\"currencyName\":\"" + conturi.get(i).getValuta() + "\",\"amount\":\"" + sold + "\"}";
            if (i != conturi.size() - 1) {
                output += ",";
            }
        }
        output += "]}";
        System.out.println(output);
    }
}

class ListUser implements Comanda {
    private String email;
    ArrayList<Utilizator> utilizatori = null;

    ListUser(String email, ArrayList<Utilizator> utilizatori) {
        this.email = email;
        this.utilizatori = utilizatori;
    }

    public void executa() {
        Utilizator userCurent = null;
        for (Utilizator utilizator : this.utilizatori) {
            if (utilizator.getEmail().equals(email)) {
                userCurent = utilizator;
                break;
            }
        }

        if (userCurent == null) {
            System.out.println("User with " + email + " doesn't exist");
            return;
        }
        
        System.out.println(userCurent.toString());
    }
}

class BuyPremium implements Comanda {
    private String email;
    ArrayList<Utilizator> utilizatori = null;

    public BuyPremium(String email, ArrayList<Utilizator> utilizatori) {
        this.email = email;
        this.utilizatori = utilizatori;
    }

    public void executa() {
        Utilizator user = null;
        for (Utilizator utilizator : this.utilizatori) {
            if (utilizator.getEmail().equals(email)) {
                user = utilizator;
                break;
            }
        }

        if (user == null) {
            System.out.println("User with " + email + " doesn't exist");
            return;
        }

        ArrayList<Cont> conturi = user.getConturi();
        for (Cont cont : conturi) {
            if (cont.getValuta().equals("USD")) {
                if (cont.getSoldCont() < 100) {
                    System.out.println("Insufficient amount in account for buying premium option");
                    return;
                }
                cont.scadeBani(100);
            }
        }
        user.setPremium();
    }
}