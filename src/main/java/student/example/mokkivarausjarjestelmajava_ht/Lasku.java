package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Lasku extends Main {
    int lasku_id=0;
    int varaus_id=0;
    double summa = 0;
    double alv = 0.24;
    boolean maksettu = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage=laskutusMetodi(primaryStage);
        primaryStage.show();
    }

    protected Stage laskutusMetodi(Stage laskuStage){
        BorderPane paneeliLaskutukselle = new BorderPane();
        TextArea alueLaskujenTiedoille = new TextArea();
        alueLaskujenTiedoille.setEditable(false);
        ListView<Lasku> laskuLista = new ListView<>();
        //TÄHÄN HAETAAN LASKUT TIETOKANNASTA
        Button koti = kotiNappain();
        paneeliLaskutukselle.setLeft(koti);
        paneeliLaskutukselle.setCenter(laskuLista);
        paneeliLaskutukselle.setRight(alueLaskujenTiedoille);
        Scene sceneLaskuille = new Scene(paneeliLaskutukselle);
        laskuStage.setTitle("Laskut");
        laskuStage.setScene(sceneLaskuille);
        return laskuStage;
    }
}
