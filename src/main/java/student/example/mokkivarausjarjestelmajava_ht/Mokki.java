package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Mokki {
    int mokki_id;
    int alue_id;
    int postinro;
    String mokkinimi;
    String katuosoite;
    Double hinta;
    String kuvaus;
    int henkilomaara;
    ArrayList<String> varustelu = new ArrayList<>();
    public String toString(){
        return ("Mökki: " + mokkinimi + "\nmökin id: " + mokki_id + "\nalue: " + alue_id + "\nPostinumero: " + postinro + "\nosoite: " + katuosoite +
                "\nhinta/yö: " + hinta + "\nmökin kuvaus: " + kuvaus + "\nhenkilömäärä: " + henkilomaara + "\nmökin varustelu: " + varustelu);
    }

    public Mokki() {
    }

    public Mokki(int mokki_id, int alue_id, int postinro, String mokkinimi, String katuosoite, Double hinta, String kuvaus, int henkilomaara, ArrayList<String> varustelu) {
        this.mokki_id = mokki_id;
        this.alue_id = alue_id;
        this.postinro = postinro;
        this.mokkinimi = mokkinimi;
        this.katuosoite = katuosoite;
        this.hinta = hinta;
        this.kuvaus = kuvaus;
        this.henkilomaara = henkilomaara;
        this.varustelu = varustelu;
    }

    public static void main(String[] args) {

    }
}
