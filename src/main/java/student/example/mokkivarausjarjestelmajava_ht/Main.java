package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;


public class Main extends Application {
    private Mokki mokki = new Mokki(this);
    private Alue alue = new Alue(this);
    private BillHandler billHandler = new BillHandler(this);
    private CottageHandler cottageHandler = new CottageHandler(this, mokki);
    private CustomerHandler customerHandler = new CustomerHandler(this);
    private AlueHandler alueHandler = new AlueHandler(this, alue);

    public SqlConnect connect = new SqlConnect("Test_user", "1234");
    public Main() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        connect.createConnection();
        //TEEN ALOITUSNÄYTÖN KÄYTTÖLIITTYMÄN TÄHÄN
        BorderPane paneeliAloitusNaytolle = new BorderPane();
        paneeliAloitusNaytolle.setPrefSize(500, 500);
        paneeliAloitusNaytolle.setPadding(new Insets(10, 10, 10, 10));
        VBox paneeliKeskiNapeille = new VBox(10);
        paneeliKeskiNapeille.setAlignment(Pos.CENTER);
        Button laskujenKatsomisNappi = new Button("Laskut");
        Button mokkiNappi = new Button("Mökit");
        Button asiakasNappi = new Button("Asiakkaat");
        Button alueNappi = new Button("Alueet");
        paneeliKeskiNapeille.getChildren().addAll(laskujenKatsomisNappi, mokkiNappi, asiakasNappi, alueNappi);
        paneeliAloitusNaytolle.setCenter(paneeliKeskiNapeille);
        paneeliAloitusNaytolle.setLeft(kotiNappain(primaryStage));
        laskujenKatsomisNappi.setOnAction(e->{
            billHandler.laskutusMetodi(primaryStage);
        });
        mokkiNappi.setOnAction(e->{
            cottageHandler.mokkiMetodi(primaryStage, connect.executeQuery("SELECT mokkinimi, mokki_id FROM mokki"));
        });
        asiakasNappi.setOnAction(e->{
            customerHandler.asiakasMetodi(primaryStage);
        });
        alueNappi.setOnAction(e->{
            alueHandler.alueMetodi(primaryStage, connect.executeQuery("SELECT nimi, alue_id FROM alue"));
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
