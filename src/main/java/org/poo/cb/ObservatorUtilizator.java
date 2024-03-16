package org.poo.cb;

//=====Observer=====
public class ObservatorUtilizator {
    Utilizator utilizator;

    public ObservatorUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    public void actualizeazaListaPrieteni(Utilizator user, String email) {
        user.getPrieteni().add(email);
    }

}
