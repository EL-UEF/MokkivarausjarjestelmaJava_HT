package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.stage.Stage;

import java.security.PublicKey;

public class Asiakas {
    Main main;
    int asiakas_id = 0;
    int postinro = 0;
    String etunimi;
    String sukunimi;
    String lahiosoite;
    String email;
    String puhelinnumero;

    public String toString(){
        return ("Asiakas id: " + asiakas_id + "\npostinumero: " + postinro + "\nEtunimi: " + etunimi + "\nSukunimi: " + sukunimi + "\nOsoite: " + lahiosoite + "\nSähköposti: " + email + "\nPuhelinnumero: " + puhelinnumero);
    }
    public String SQLToString(String etunimi){
        return "lol";
    }

    public Asiakas(int asiakas_id, int postinro, String etunimi, String sukunimi, String lahiosoite, String email, String puhelinnumero) {
        this.asiakas_id = asiakas_id;
        this.postinro = postinro;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.lahiosoite = lahiosoite;
        this.email = email;
        this.puhelinnumero = puhelinnumero;
    }

    public Asiakas(Main main) {
        this.main = main;
    }
}
