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
        Double SQLsumma = -1.0;
        int SQLmaksettu = -1;

        try {
            ResultSet rs = main.connect.searchForStuff("lasku", criteria);
            rs.next();
            SQLvaraus_id = rs.getInt("varaus_id");
            SQLsumma = rs.getDouble("summa");
            SQLmaksettu = rs.getInt("maksettu");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ("Lasku id: " + id + "\nvaraus id: " + SQLvaraus_id + "\nSumma: " + SQLsumma + "\nmaksettu: " + SQLmaksettu);
    }
    public Lasku(Main main){
        this.main=main;
    }
}
