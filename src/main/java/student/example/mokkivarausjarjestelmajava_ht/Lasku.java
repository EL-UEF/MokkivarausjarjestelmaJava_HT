package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Lasku {
    private Main main;
    int lasku_id=0;
    int varaus_id=0;
    double summa = 0;
    double alv = 24;
    boolean maksettu = false;

    public String SQLToString(String id){
        String criteria = ("lasku_id = " + id);
        int SQLvaraus_id = -1;
        int asiakas_id = -1;
        String asiakasNimi = null;
        String mokkiNimi = null;
        LocalDateTime alkuPaiva = null;
        LocalDateTime loppuPaiva = null;
        String kaytetytPalvelut = null;
        int kokonaisHinta = -1;
        int maksettu = -1;
        String formatoituAlkuPaiva;
        String formatoituLoppuPaiva;

        try {
            ResultSet rs = main.connect.searchForStuff("laskutustiedot", criteria);
            rs.next();
            SQLvaraus_id = rs.getInt("varaus_id");
            asiakas_id = rs.getInt("asiakas_id");
            asiakasNimi = rs.getString("asiakas");
            mokkiNimi = rs.getString("mökki");
            alkuPaiva = rs.getObject("alkupaiva", LocalDateTime.class);
            loppuPaiva = rs.getObject("loppupaiva", LocalDateTime.class);
            kaytetytPalvelut = rs.getString("käytetyt palvelut");
            kokonaisHinta = rs.getInt("kokonaishinta");
            maksettu = rs.getInt("maksettu");
            formatoituAlkuPaiva = alkuPaiva.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            formatoituLoppuPaiva = loppuPaiva.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ("Lasku id: " + id + "\nvaraus id: " + SQLvaraus_id + "\nAsiakas id: " + asiakas_id + "\nAsiakkaan nimi: " +
                asiakasNimi + "\nMökki: " + mokkiNimi + "\nVarauksen alku: " + formatoituAlkuPaiva + "\nVarauksen loppu: " +
                formatoituLoppuPaiva + "\nKäytetyt palvelut: " + kaytetytPalvelut + "\nSumma: " + kokonaisHinta + "\nOnko maksettu: " + maksettu);
    }
    public Lasku(Main main){
        this.main=main;
    }
}
