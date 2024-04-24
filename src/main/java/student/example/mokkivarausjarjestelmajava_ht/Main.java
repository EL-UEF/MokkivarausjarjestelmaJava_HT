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
    private Palvelu palvelu = new Palvelu(this);
    private BillHandler billHandler = new BillHandler(this);
    private CottageHandler cottageHandler = new CottageHandler(this, mokki);
    private CustomerHandler customerHandler = new CustomerHandler(this);
    private AlueHandler alueHandler = new AlueHandler(this, alue);
    private PalveluHandler palveluHandler = new PalveluHandler(this, palvelu);

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
        Button palveluNappi = new Button("Palvelut");
        paneeliKeskiNapeille.getChildren().addAll(laskujenKatsomisNappi, mokkiNappi, asiakasNappi, alueNappi, palveluNappi);
        paneeliAloitusNaytolle.setCenter(paneeliKeskiNapeille);
        paneeliAloitusNaytolle.setLeft(kotiNappain(primaryStage));
        laskujenKatsomisNappi.setOnAction(e->{
            billHandler.laskutusMetodi(primaryStage);
        });
        mokkiNappi.setOnAction(e->{
            cottageHandler.mokkiMetodi(primaryStage, connect.executeQuery("SELECT mokki_id FROM mokki ORDER BY mokki_id"));
        });
        asiakasNappi.setOnAction(e->{
            customerHandler.asiakasMetodi(primaryStage);
        });
        alueNappi.setOnAction(e->{
            alueHandler.alueMetodi(primaryStage, connect.executeQuery("SELECT alue_id FROM alue ORDER BY alue_id"));
        });
        palveluNappi.setOnAction(e->{
            palveluHandler.palveluMetodi(primaryStage, connect.executeQuery("SELECT palvelu_id FROM palvelu ORDER BY palvelu_id"));
        });

        Scene scene = new Scene(paneeliAloitusNaytolle);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mökkienvarausohjelma 1.0");
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
