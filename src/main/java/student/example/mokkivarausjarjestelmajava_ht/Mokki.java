package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Mokki(String identifier) throws SQLException {
        SqlConnect connect = new SqlConnect("Test_user", "1234");
        String query = "SELECT * FROM mokki WHERE alue_id = "+identifier+";";
        ResultSet rs = connect.createConnection(query);
        this.mokki_id = rs.getInt("mokki_id");
        this.alue_id = rs.getInt("alue_id");
        this.postinro = rs.getInt("postinro");
        this.mokkinimi = rs.getString("mokkinimi");
        this.katuosoite = rs.getString("katuosoite");
        this.hinta = rs.getDouble("hinta");
        this.kuvaus = rs.getString("kuvaus");
        this.henkilomaara = rs.getInt("henkilomaara");
        //this.varustelu = rs.getString("varustelu");
    }

    public Mokki(String data, String table, String values) throws SQLException {
        SqlConnect connect = new SqlConnect("Test_user", "1234");
        connect.insertData(data,table,values);

    }


    public static void main(String[] args) {
        try {
            Mokki testi = new Mokki("1");
            System.out.println(testi.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
