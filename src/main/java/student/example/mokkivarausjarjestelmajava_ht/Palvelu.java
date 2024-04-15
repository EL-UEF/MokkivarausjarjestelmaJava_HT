package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.stage.Stage;

public class Palvelu extends Application {
    int palvelu_id;
    int alue_id;
    String nimi;
    String kuvaus;
    Double hinta;
    Double alv = 0.1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
