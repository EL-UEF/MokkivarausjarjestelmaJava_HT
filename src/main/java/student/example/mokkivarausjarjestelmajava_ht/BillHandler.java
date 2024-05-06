package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillHandler extends Application {
    private Lasku lasku;
    private Main main;
    String valittuNimi = "-1";
    BillPDFer billPdfer;

    public BillHandler(Main main, Lasku lasku, BillPDFer billPdfer) {
        this.main = main;
        this.lasku = lasku;
        this.billPdfer=billPdfer;
    }
    protected void laskuMetodi(Stage laskuStage, ResultSet rs){
        BorderPane BPlaskuille = new BorderPane();
        TextArea alueLaskujenTiedoille = new TextArea();
        alueLaskujenTiedoille.setText("Klikkaa laskua nähdäksesi sen tarkemmat tiedot :)");
        alueLaskujenTiedoille.setEditable(false);
        /**
         * Logiikka laskujen indeksien näyttämiselle ListViewissä ja tietojen hakemiselle tietokannasta
         */
        ArrayList<String> laskuNimiLista = new ArrayList<>();
        try {
            while (rs.next())
                laskuNimiLista.add(rs.getString("lasku_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(laskuNimiLista);
        ListView<String> laskuLista = new ListView<>();
        laskuLista.setItems(FXCollections.observableArrayList(laskuNimiLista));
        laskuLista.getSelectionModel().selectedItemProperty().addListener(ov->{
            valittuNimi =(laskuLista.getSelectionModel().getSelectedItem());
            alueLaskujenTiedoille.setText(lasku.SQLToString(valittuNimi));
        });
        Button kotiNappi = main.kotiNappain(laskuStage);
        Button pdfer = new Button("Luo pdf");
        Button lisaysNappi = new Button("Uusi lasku");
        lisaysNappi.setOnAction(e->{
            laskunLisaysMetodi(laskuStage);
        });

        Button maksettuNappi = new Button("Merkkaa lasku maksetuksi");
        maksettuNappi.setOnAction(e->{
            asetaMaksetuksi(laskuStage);
        });
        /*
        Button muokkausNappi = new Button("Muokkaa valittua mökkiä");
        muokkausNappi.setOnAction(e->{
            mokinMuokkausMetodi(laskuStage);
        });
        Button etsintaNappi = new Button("Etsi mökkiä");
        etsintaNappi.setOnAction(e->{
            mokinEtsintaMetodi(laskuStage);
        });
         */
        pdfer.setOnAction(e->{
            billPdfer.createBillPDF(valittuNimi);
        });
        HBox paneeliAlaValikolle = new HBox(10);
        paneeliAlaValikolle.getChildren().addAll(kotiNappi, pdfer, lisaysNappi, maksettuNappi);//, lisaysNappi, muokkausNappi, etsintaNappi
        BPlaskuille.setBottom(paneeliAlaValikolle);
        BPlaskuille.setLeft(laskuLista);
        BPlaskuille.setCenter(alueLaskujenTiedoille);
        Scene scene = new Scene(BPlaskuille);
        laskuStage.setTitle("Laskut");
        laskuStage.setScene(scene);
        laskuStage.show();
    }
    public void laskunLisaysMetodi(Stage alueenLisaysStage){
        BorderPane BPlaskujenLisaamiselle = new BorderPane();
        BPlaskujenLisaamiselle.setPrefSize(400, 400);
        BPlaskujenLisaamiselle.setPadding(new Insets(10, 10, 10, 10));

        VBox paneeliUudenLaskunTiedoille = new VBox(10);
        Text alkuHopina = new Text("Laskun tiedot täytetään automaattisesti. Syötä laskutettavan varauksen id");
        TextField laskutettavaIdTF = new TextField();
        Button lisaysNappi = new Button("Lisää");
        lisaysNappi.setOnAction(e->{
            int uudenLaskunVarausId = Integer.parseInt(laskutettavaIdTF.getText());
            main.connect.executeQuery("CALL create_lasku(" + uudenLaskunVarausId + ");");
        });
        Button kotiNappula = main.kotiNappain(alueenLisaysStage);
        paneeliUudenLaskunTiedoille.setAlignment(Pos.CENTER);
        paneeliUudenLaskunTiedoille.getChildren().addAll(alkuHopina, laskutettavaIdTF, lisaysNappi, kotiNappula);
        BPlaskujenLisaamiselle.setCenter(paneeliUudenLaskunTiedoille);
        Scene scene = new Scene(BPlaskujenLisaamiselle);
        alueenLisaysStage.setScene(scene);
        alueenLisaysStage.setTitle("Lisää uusi alue");
        alueenLisaysStage.show();
    }
    public void asetaMaksetuksi(Stage primaryStage){
        main.connect.updateTable("lasku", "maksettu", "1", ("lasku_id = " + valittuNimi));
        laskuMetodi(primaryStage, main.connect.executeQuery("SELECT lasku_id FROM lasku ORDER BY lasku_id"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}