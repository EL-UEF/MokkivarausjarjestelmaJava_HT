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

public class CustomerHandler extends Application {
    private Main main;
    private Asiakas asiakas;
    String valittuNimi;

    public CustomerHandler(Main main, Asiakas asiakas) {
        this.main = main;
        this.asiakas=asiakas;
    }

    protected void asiakasMetodi(Stage mokkiStage, ResultSet rs){
        BorderPane BPasiakkaille = new BorderPane();
        TextArea alueAsiakkaidenTiedoille = new TextArea();
        alueAsiakkaidenTiedoille.setText("Klikkaa asiakasta nähdäksesi sen tarkemmat tiedot :)");
        alueAsiakkaidenTiedoille.setEditable(false);
        /**
         * Logiikka asiakkaiden indeksien näyttämiselle ListViewissä ja tietojen hakemiselle tietokannasta
         */
        ArrayList<String> asiakasNimiLista = new ArrayList<>();
        try {
            while (rs.next())
                asiakasNimiLista.add(rs.getString("sukunimi"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(asiakasNimiLista);
        ListView<String> asiakasLista = new ListView<>();
        asiakasLista.setItems(FXCollections.observableArrayList(asiakasNimiLista));
        asiakasLista.getSelectionModel().selectedItemProperty().addListener(ov->{
            valittuNimi =("'" + asiakasLista.getSelectionModel().getSelectedItem() + "'");
            alueAsiakkaidenTiedoille.setText(asiakas.SQLToString(valittuNimi));
        });
        Button kotiNappi = main.kotiNappain(mokkiStage);
        Button lisaysNappi = new Button("Lisää uusi mökki");
        /*
        lisaysNappi.setOnAction(e->{
            mokinLisaysMetodi(mokkiStage);
        });
        Button poistoNappi = new Button("Poista valittu mökki");
        poistoNappi.setOnAction(e->{
            mokinPoisto();
        });
        Button muokkausNappi = new Button("Muokkaa valittua mökkiä");
        muokkausNappi.setOnAction(e->{
            mokinMuokkausMetodi(mokkiStage);
        });
        Button etsintaNappi = new Button("Etsi mökkiä");
        etsintaNappi.setOnAction(e->{
            mokinEtsintaMetodi(mokkiStage);
        });
         */
        HBox paneeliAlaValikolle = new HBox(10);
        paneeliAlaValikolle.getChildren().addAll(kotiNappi, lisaysNappi);//, muokkausNappi, etsintaNappi, poistoNappi);
        BPasiakkaille.setBottom(paneeliAlaValikolle);
        BPasiakkaille.setLeft(asiakasLista);
        BPasiakkaille.setCenter(alueAsiakkaidenTiedoille);
        Scene scene = new Scene(BPasiakkaille);
        mokkiStage.setTitle("Mökit");
        mokkiStage.setScene(scene);
        mokkiStage.show();
    }
    /*public void asiakkaanLisaysMetodi(Stage lisaysStage){
        BorderPane paneeliAsiakkaidenLisääntymiselle = new BorderPane();
        paneeliAsiakkaidenLisääntymiselle.setPrefSize(500, 500);
        paneeliAsiakkaidenLisääntymiselle.setPadding(new Insets(10, 10, 10, 10));

        VBox paneeliAsiakkaanTiedoille = new VBox(10);
        Text uusiPostiNumero = new Text("Anna asiakkaan postinumero");
        TextField postiNroTF = new TextField();
        Text uusiEtunimi = new Text("Asiakkaan etunimi");
        TextField etunimiTF = new TextField();
        Text uusiSukunimi = new Text("Asiakkaan sukunimi");
        TextField sukunimiTF = new TextField();
        Text uusiOsoite = new Text("Asiakkaan lähiosoite");
        TextField osoiteTF = new TextField();
        Text uusiSposti = new Text("Asiakkaan sähköpostiosoite");
        TextField spostiTF = new TextField();
        Text uusiPuhNro = new Text("Asiakkaan puhelinnumero");
        TextField puhelinnumeroTF = new TextField();
        Button tallennusNappi = new Button("Tallenna");

        tallennusNappi.setOnAction(e->{
            int uusiID = olemassaolevatAsiakkaat.size()+1;
            int uusiPostinro = Integer.parseInt(postiNroTF.getText());
            String lisattavaEtunimi = etunimiTF.getText();
            String lisattavaSukunimi = sukunimiTF.getText();
            String lisattavaOsoite = osoiteTF.getText();
            String lisattavaSposti = spostiTF.getText();
            String lisattavaNro = puhelinnumeroTF.getText();
            Asiakas uusiLisattavaAsiakas = new Asiakas(uusiID, uusiPostinro, lisattavaEtunimi, lisattavaSukunimi, lisattavaOsoite, lisattavaSposti, lisattavaNro);
            olemassaolevatAsiakkaat.add(uusiLisattavaAsiakas);
            System.out.println(uusiLisattavaAsiakas);
        });
        Button kotiNappi = main.kotiNappain(lisaysStage);
        paneeliAsiakkaidenLisääntymiselle.setLeft(kotiNappi);
        paneeliAsiakkaanTiedoille.getChildren().addAll(uusiPostiNumero, postiNroTF, uusiEtunimi, etunimiTF, uusiSukunimi, sukunimiTF, uusiOsoite, osoiteTF, uusiSposti, spostiTF, uusiPuhNro, puhelinnumeroTF, tallennusNappi);
        paneeliAsiakkaanTiedoille.setAlignment(Pos.CENTER);
        paneeliAsiakkaidenLisääntymiselle.setCenter(paneeliAsiakkaanTiedoille);
        Scene scene = new Scene(paneeliAsiakkaidenLisääntymiselle);
        lisaysStage.setScene(scene);
        lisaysStage.setTitle("Uuden asiakkaan lisääminen");
        lisaysStage.show();
    }


     */



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
