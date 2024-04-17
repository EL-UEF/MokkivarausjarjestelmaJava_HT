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

import java.util.ArrayList;

public class Lasku extends Main {
    int lasku_id=0;
    int varaus_id=0;
    double summa = 0;
    double alv = 24;
    boolean maksettu = false;

    public String toString(){
        return ("Lasku id: " + lasku_id + "\nvaraus id: " + varaus_id + "\nSumma: " + summa + "\nalv: " + alv + " % eli " + (summa*alv/100) + " €" + "\nmaksettu: " + maksettu);
    }
    public Lasku(){
        lasku_id=0;
        varaus_id=0;
        summa=0;
        alv=24;
        maksettu=false;
    }

    public Lasku(int lasku_id, int varaus_id, double summa, double alv) {
        this.lasku_id = lasku_id;
        this.varaus_id = varaus_id;
        this.summa = summa;
        this.alv = alv;
        this.maksettu = false;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    }




}
