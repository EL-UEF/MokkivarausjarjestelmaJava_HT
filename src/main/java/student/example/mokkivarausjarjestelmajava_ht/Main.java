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
    private BillHandler billHandler = new BillHandler(this);
    private CottageHandler cottageHandler = new CottageHandler(this);
    private CustomerHandler customerHandler = new CustomerHandler(this);

    public Main() {
    }

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
        Button asiakasNappi = new Button("Asiakkaat");
        paneeliKeskiNapeille.getChildren().addAll(laskujenKatsomisNappi, mokkiNappi, asiakasNappi);
        paneeliAloitusNaytolle.setCenter(paneeliKeskiNapeille);
        paneeliAloitusNaytolle.setLeft(kotiNappain(primaryStage));
        laskujenKatsomisNappi.setOnAction(e->{
            billHandler.laskutusMetodi(primaryStage);
        });
        mokkiNappi.setOnAction(e->{
            cottageHandler.mokkiMetodi(primaryStage);
        });
        asiakasNappi.setOnAction(e->{
            customerHandler.asiakasMetodi(primaryStage);
        });

        Scene scene = new Scene(paneeliAloitusNaytolle);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mökkienvaraus ohjelma 1.0");
        primaryStage.show();
    }
    public Button kotiNappain(Stage primaryStage){
        Button kotiNappi = new Button("Koti");
        kotiNappi.setOnAction(e->{
            start(primaryStage);
        });
        return kotiNappi;
    }

}
