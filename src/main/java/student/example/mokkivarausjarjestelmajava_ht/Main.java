package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
    public Button kotiNappain(){
        Button kotiNappi = new Button("Koti");
        kotiNappi.setOnAction(e->{
            //PALAA ALOITUSNÄYTTÖÖN
        });
        return kotiNappi;
    }
}
