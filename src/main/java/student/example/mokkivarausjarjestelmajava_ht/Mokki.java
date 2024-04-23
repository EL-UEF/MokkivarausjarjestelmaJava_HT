package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Mokki {
    Main main;
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

    /**
     * etsii mökki_id:n perusteella mökin tiedot SQL tietokannasta ja palauttaa ne String tyyppisenä
     * @param valittuID mökin id, jonka tiedot halutaan palauttaa
     * @return String, jossa valitun mökin tiedot
     */
    public String SQLToString(int valittuID){
        String query = ("SELECT * FROM mokki WHERE mokki_id = " + valittuID);
        int SQLalue_id = 0;
        int SQLpostinro = 0;
        String SQLnimi = null;
        String SQLkatuosoite = null;
        Double SQLhinta = null;
        String SQLkuvaus = null;
        int SQLhenkilomaara = 0;
        String SQLvarustelu = null;
        try {
            ResultSet rs = main.connect.createConnection(query);
            SQLalue_id = rs.getInt("alue_id");
            SQLpostinro = rs.getInt("postinro");
            SQLnimi = rs.getString("mokkinimi");
            SQLkatuosoite = rs.getString("katuosoite");
            SQLhinta = rs.getDouble("hinta");
            SQLkuvaus = rs.getString("kuvaus");
            SQLhenkilomaara = rs.getInt("henkilomaara");
            SQLvarustelu = rs.getString("varustelu");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String kokoTeksti = ("Mokki " + SQLnimi + "\nmökin id: " + valittuID + "\nalue: " + SQLalue_id + "\nPostinumero: " + SQLpostinro + "\nosoite: " + SQLkatuosoite +
                "\nhinta/yö: " + SQLhinta + "\nmökin kuvaus: " + SQLkuvaus + "\nhenkilömäärä: " + SQLhenkilomaara + "\nmökin varustelu: " + SQLvarustelu);
        return kokoTeksti;
    }

    public Mokki() {
    }
    public Mokki(Main main){
        this.main=main;
    }

    public Mokki(String identifier, Main main) throws SQLException {
        this.main=main;
        String query = "SELECT * FROM mokki WHERE alue_id = "+identifier+";";
        try (ResultSet rs = main.connect.createConnection(query)) {
            this.mokki_id = rs.getInt("mokki_id");
            this.alue_id = rs.getInt("alue_id");
            this.postinro = rs.getInt("postinro");
            this.mokkinimi = rs.getString("mokkinimi");
            this.katuosoite = rs.getString("katuosoite");
            this.hinta = rs.getDouble("hinta");
            this.kuvaus = rs.getString("kuvaus");
            this.henkilomaara = rs.getInt("henkilomaara");
        }
        //this.varustelu = rs.getString("varustelu");
    }

    public Mokki(String data, String table, String values, Main main) throws SQLException {
        main.connect.insertData(data,table,values);

    }


    public static void main(String[] args) {
    }
}
