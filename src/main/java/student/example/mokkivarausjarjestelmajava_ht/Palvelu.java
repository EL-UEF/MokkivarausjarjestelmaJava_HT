package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Palvelu {
    private Main main;
    int palvelu_id;
    int alue_id;
    String nimi;
    String kuvaus;
    Double hinta;
    Double alv = 0.1;
    public String SQLToString(String nimi){
        String query = ("SELECT * FROM palvelu WHERE nimi = '" + nimi + "'");
        int SQLalue_id = 0;
        String SQLnimi = null;
        String SQLkuvaus = null;
        Double SQLhinta = null;
        Double SQLalv = null;
        try {
            ResultSet rs = main.connect.executeQuery(query);
            rs.next();
            SQLalue_id = rs.getInt("alue_id");
            SQLnimi = rs.getString("nimi");
            SQLkuvaus = rs.getString("kuvaus");
            SQLhinta = rs.getDouble("hinta");
            SQLalv = rs.getDouble("alv");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String kokoTeksti = ("Palvelu id: " + nimi + "\nAlue id: " + SQLalue_id + "\nNimi: " + SQLnimi +
                "\nKuvaus: " + SQLkuvaus + "\nHinta: " + SQLhinta + "\nALV: " + SQLalv + " %");
        return kokoTeksti;
    }
    public Palvelu(Main main){
        this.main=main;
    }
}
