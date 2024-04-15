package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Mokki extends Application {
    int mokki_id;
    int alue_id;
    int postinro;
    String mokkinimi;
    String katuosoite;
    Double hinta;
    String kuvaus;
    int henkilomaara;
    ArrayList<String> varustelu = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
