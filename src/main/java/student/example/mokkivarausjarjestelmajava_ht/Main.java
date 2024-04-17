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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    private BillHandler billHandler = new BillHandler();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //TEEN ALOITUSNÄYTÖN KÄYTTÖLIITTYMÄN TÄHÄN
        BorderPane paneeliAloitusNaytolle = new BorderPane();
        paneeliAloitusNaytolle.setPrefSize(500, 500);
        paneeliAloitusNaytolle.setPadding(new Insets(10, 10, 10, 10));
        VBox paneeliKeskiNapeille = new VBox(10);
        paneeliKeskiNapeille.setAlignment(Pos.CENTER);
        Button laskujenKatsomisNappi = new Button("Laskut");
        Button mokkiNappi = new Button("Mökit");
        paneeliKeskiNapeille.getChildren().addAll(laskujenKatsomisNappi, mokkiNappi);
        paneeliAloitusNaytolle.setCenter(paneeliKeskiNapeille);
        paneeliAloitusNaytolle.setLeft(kotiNappain());
        laskujenKatsomisNappi.setOnAction(e->{
            billHandler.laskutusMetodi(primaryStage);
        });

        Scene scene = new Scene(paneeliAloitusNaytolle);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mökkienvaraus ohjelma 1.0");
        primaryStage.show();
    }
    public Button kotiNappain(){
        Button kotiNappi = new Button("Koti");
        kotiNappi.setOnAction(e->{
            //PALAA ALOITUSNÄYTTÖÖN
        });
        return kotiNappi;
    }

}
