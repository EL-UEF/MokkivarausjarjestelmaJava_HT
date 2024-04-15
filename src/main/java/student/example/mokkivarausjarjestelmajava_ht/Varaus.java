package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Date;

public class Varaus extends Application {
    int varaus_id=0;
    int asiakas_id = 0;
    int mokki_id = 0;
    Date varattu_pvm = new Date();
    Date vahvistus_pvm = new Date();
    Date varattu_alkupvm = new Date();
    Date varattu_loppupvm = new Date();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
