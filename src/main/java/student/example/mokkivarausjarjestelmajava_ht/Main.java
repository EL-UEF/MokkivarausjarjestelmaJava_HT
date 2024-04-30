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
    private Varaus varaus = new Varaus(this);
    private Lasku lasku = new Lasku(this);
    private BillPDFer billPDFer = new BillPDFer(this);
    private VarausHandler varausHandler = new VarausHandler(this, varaus);
    private BillHandler billHandler = new BillHandler(this, lasku, billPDFer);
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
        Button varauksetNappi = new Button("Varaukset");
        paneeliKeskiNapeille.getChildren().addAll(laskujenKatsomisNappi, mokkiNappi, asiakasNappi, alueNappi, palveluNappi,
                varauksetNappi);
        paneeliAloitusNaytolle.setCenter(paneeliKeskiNapeille);
        paneeliAloitusNaytolle.setLeft(kotiNappain(primaryStage));
        laskujenKatsomisNappi.setOnAction(e->{
            billHandler.laskuMetodi(primaryStage, connect.executeQuery("SELECT lasku_id FROM lasku ORDER BY lasku_id"));
        });
        mokkiNappi.setOnAction(e->{
            cottageHandler.mokkiMetodi(primaryStage, connect.executeQuery("SELECT mokki_id, mokkinimi FROM mokki ORDER BY mokki_id"));
        });
        asiakasNappi.setOnAction(e->{
            customerHandler.asiakasMetodi(primaryStage);
        });
        alueNappi.setOnAction(e->{
            alueHandler.alueMetodi(primaryStage, connect.executeQuery("SELECT alue_id, nimi FROM alue ORDER BY alue_id"));
        });
        palveluNappi.setOnAction(e->{
            palveluHandler.palveluMetodi(primaryStage, connect.executeQuery("SELECT palvelu_id, nimi FROM palvelu ORDER BY palvelu_id"));
        });
        varauksetNappi.setOnAction(e->{
            varausHandler.varausMetodi(primaryStage, connect.executeQuery("SELECT varaus_id FROM varaus ORDER BY varaus_id"));
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
