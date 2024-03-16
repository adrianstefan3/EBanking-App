package org.poo.cb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args == null) {
            System.out.println("Running Main");
        } else {
            AplicatieBanca aplicatie = AplicatieBanca.Instanta();
            String currencyPath = "src/main/resources/" + args[0];
            String stockPath = "src/main/resources/" + args[1];
            String commandPath = "src/main/resources/" + args[2];
            aplicatie.seteazaTabelValutar(currencyPath);
            aplicatie.seteazaTabelActiuni(stockPath);
            
            try {
                File fisier = new File(commandPath);
                Scanner scan = new Scanner(fisier);
                while(scan.hasNextLine()) {
                    String comanda = scan.nextLine();
                    ArrayList<String> comandaSplit = new ArrayList<>(List.of(comanda.split(" ")));
                    if (comandaSplit.get(0).equals("CREATE")) {
                        String email = comandaSplit.get(2).replaceAll("\n", "");
                        String prenume = comandaSplit.get(3).replaceAll("\n", "");
                        String nume = comandaSplit.get(4).replaceAll("\n", "");
                        String adresa = comandaSplit.get(5).replaceAll("\n", "");
                        for (int i = 6; i < comandaSplit.size(); i++) {
                            adresa += " " + comandaSplit.get(i).replaceAll("\n", "");
                        }

                        CreateUser createUser = new CreateUser(email, prenume, nume, adresa, aplicatie.utilizatori);
                        createUser.executa();
                    } else if (comandaSplit.get(0).equals("ADD") && comandaSplit.get(1).equals("FRIEND")) {
                        String email = comandaSplit.get(2).replaceAll("\n", "");
                        String emailPrieten = comandaSplit.get(3).replaceAll("\n", "");

                        AddFriend addFriend = new AddFriend(email, emailPrieten, aplicatie.utilizatori);
                        addFriend.executa();
                    } else if (comandaSplit.get(0).equals("ADD") && comandaSplit.get(1).equals("ACCOUNT")) {
                        String email = comandaSplit.get(2).replaceAll("\n", "");
                        String valuta = comandaSplit.get(3).replaceAll("\n", "");

                        AddAccount addAccount = new AddAccount(email, valuta, aplicatie.utilizatori);
                        addAccount.executa();
                    } else if(comandaSplit.get(0).equals("ADD") && comandaSplit.get(1).equals("MONEY")) {
                        String email = comandaSplit.get(2).replaceAll("\n", "");
                        String valuta = comandaSplit.get(3).replaceAll("\n", "");
                        double valoare = Double.parseDouble(comandaSplit.get(4).replaceAll("\n", ""));

                        AddMoney addMoney = new AddMoney(email, valuta, valoare, aplicatie.utilizatori);
                        addMoney.executa();
                    } else if (comandaSplit.get(0).equals("EXCHANGE") && comandaSplit.get(1).equals("MONEY")) {
                        String email = comandaSplit.get(2).replaceAll("\n", "");
                        String valutaSursa = comandaSplit.get(3).replaceAll("\n", "");
                        String valutaDestinatie = comandaSplit.get(4).replaceAll("\n", "");
                        double valoare = Double.parseDouble(comandaSplit.get(5).replaceAll("\n", ""));

                        ExchangeMoney exchangeMoney = new ExchangeMoney(email, valutaSursa, valutaDestinatie, valoare, aplicatie.utilizatori, aplicatie.getTabelValutar());
                        exchangeMoney.executa();
                    } else if(comandaSplit.get(0).equals("TRANSFER") && comandaSplit.get(1).equals("MONEY")) {
                        String email = comandaSplit.get(2).replaceAll("\n", "");
                        String emailPrieten = comandaSplit.get(3).replaceAll("\n", "");
                        String valuta = comandaSplit.get(4).replaceAll("\n", "");
                        double valoare = Double.parseDouble(comandaSplit.get(5).replaceAll("\n", ""));

                        TransferMoney transferMoney = new TransferMoney(email, emailPrieten, valuta, valoare, aplicatie.utilizatori);
                        transferMoney.executa();
                    } else if (comandaSplit.get(0).equals("BUY") && comandaSplit.get(1).equals("STOCKS")) {
                        String email = comandaSplit.get(2).replaceAll("\n", "");
                        String numeActiune = comandaSplit.get(3).replaceAll("\n", "");
                        double cantitate = Double.parseDouble(comandaSplit.get(4).replaceAll("\n", ""));

                        BuyStocks buyStocks = new BuyStocks(email, numeActiune, cantitate, aplicatie.utilizatori, aplicatie.getTabelActiuni(), aplicatie.recommendedStocks);
                        buyStocks.executa();
                    } else if(comandaSplit.get(0).equals("RECOMMEND") && comandaSplit.get(1).equals("STOCKS")) {
                        RecommendStocks recommendStocks = new RecommendStocks(aplicatie.getTabelActiuni(), aplicatie.recommendedStocks);
                        recommendStocks.executa();
                    } else if (comandaSplit.get(0).equals("LIST") && comandaSplit.get(1).equals("USER")) {
                        String email = comandaSplit.get(2).replaceAll("\n", "");

                        ListUser listUser = new ListUser(email, aplicatie.utilizatori);
                        listUser.executa();
                    } else if (comandaSplit.get(0).equals("LIST") && comandaSplit.get(1).equals("PORTFOLIO")) {
                        String email = comandaSplit.get(2).replaceAll("\n", "");

                        ListPortfolio listPortofolio = new ListPortfolio(email, aplicatie.utilizatori);
                        listPortofolio.executa();
                    } else if (comandaSplit.get(0).equals("BUY") && comandaSplit.get(1).equals("PREMIUM")) {
                        String email = comandaSplit.get(2).replaceAll("\n", "");

                        BuyPremium buyPremium = new BuyPremium(email, aplicatie.utilizatori);
                        buyPremium.executa();
                    }
                }
                scan.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            aplicatie.Distruge();
        }
    }
}