package student.example.mokkivarausjarjestelmajava_ht;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Alue {
    Main main;
     int alue_id;
     String nimi;
    public String toString(){
        return ("Alue: " + nimi + " id: " + alue_id);
    }

    public Alue(Main main) {this.main = main;}

    public String SQLToStringAlue(String valittuId){
        String query = ("SELECT * FROM alue WHERE nimi = " + "\"" + valittuId + "\"");
        int SQLalue_id = 0;
        String SQLtoimipaikka = null;
        try {
            ResultSet rs = main.connect.executeQuery(query);
            rs.next();
            SQLalue_id = rs.getInt("alue_id");
            SQLtoimipaikka = rs.getString("nimi");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String kokoTeksti = "Alue_id "+ SQLalue_id + "\nnimi "+ SQLtoimipaikka;
        return kokoTeksti;
    }

}
