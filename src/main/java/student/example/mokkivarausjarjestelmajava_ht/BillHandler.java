package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
        /*
        Button lisaysNappi = new Button("Lisää uusi mökki");
        lisaysNappi.setOnAction(e->{
            mokinLisaysMetodi(laskuStage);
        });
        Button poistoNappi = new Button("Poista valittu mökki");
        poistoNappi.setOnAction(e->{
            mokinPoisto();
        });
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
        paneeliAlaValikolle.getChildren().addAll(kotiNappi, pdfer);//, lisaysNappi, muokkausNappi, etsintaNappi, poistoNappi);
        BPlaskuille.setBottom(paneeliAlaValikolle);
        BPlaskuille.setLeft(laskuLista);
        BPlaskuille.setCenter(alueLaskujenTiedoille);
        Scene scene = new Scene(BPlaskuille);
        laskuStage.setTitle("Laskut");
        laskuStage.setScene(scene);
        laskuStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}