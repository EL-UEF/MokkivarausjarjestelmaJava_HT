package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    protected void asiakasMetodi(Stage asiakasStage, ResultSet rs){
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
        Button kotiNappi = main.kotiNappain(asiakasStage);
        Button lisaysNappi = new Button("Lisää uusi asiakas");
        lisaysNappi.setOnAction(e->{
            asiakkaanLisaysMetodi(asiakasStage);
        });
        /*
        Button poistoNappi = new Button("Poista valittu mökki");
        poistoNappi.setOnAction(e->{
            mokinPoisto();
        });
        Button muokkausNappi = new Button("Muokkaa valittua mökkiä");
        muokkausNappi.setOnAction(e->{
            mokinMuokkausMetodi(asiakasStage);
        });
        Button etsintaNappi = new Button("Etsi mökkiä");
        etsintaNappi.setOnAction(e->{
            mokinEtsintaMetodi(asiakasStage);
        });
         */
        HBox paneeliAlaValikolle = new HBox(10);
        paneeliAlaValikolle.getChildren().addAll(kotiNappi, lisaysNappi);//, muokkausNappi, etsintaNappi, poistoNappi);
        BPasiakkaille.setBottom(paneeliAlaValikolle);
        BPasiakkaille.setLeft(asiakasLista);
        BPasiakkaille.setCenter(alueAsiakkaidenTiedoille);
        Scene scene = new Scene(BPasiakkaille);
        asiakasStage.setTitle("Asiakkaat");
        asiakasStage.setScene(scene);
        asiakasStage.show();
    }
    protected void asiakkaanLisaysMetodi(Stage asiakasStage){
        /**
         * graafisia komponentteja ja niiden sijoittelua
         */
        BorderPane BPAsiakkaanLisaamiselle = new BorderPane();
        VBox paneeliUudenAsiakkaanTiedoille = new VBox(10);
        Text annaAlue = new Text("Asiakkaan osoite ja postinumero");
        TextField postinroTF = new TextField();
        TextField katuosoiteTF = new TextField();
        HBox paneeliAlueelle = new HBox(10);
        paneeliAlueelle.getChildren().addAll(katuosoiteTF, postinroTF);
        Text etuNimi = new Text("Etunimi");
        TextField etunimiTF = new TextField();
        Text sukuNimi = new Text("Sukunimi");
        TextField sukunimiTF = new TextField();
        Text spostiOsoite = new Text("Email");
        TextField spostiTF = new TextField();
        Text puhelinnumeroTeksti = new Text("Puhelinnumero");
        TextField puhnroTF = new TextField();
        Button tallennusNappi = new Button("Tallenna");
        tallennusNappi.setAlignment(Pos.CENTER);
        Button kotiNappi = main.kotiNappain(asiakasStage);
        BPAsiakkaanLisaamiselle.setBottom(kotiNappi);
        paneeliUudenAsiakkaanTiedoille.getChildren().addAll(annaAlue, paneeliAlueelle, etuNimi, etunimiTF, sukuNimi, sukunimiTF, spostiOsoite, spostiTF,
                puhelinnumeroTeksti, puhnroTF, tallennusNappi);
        paneeliUudenAsiakkaanTiedoille.setAlignment(Pos.CENTER);
        /**
         * Toiminnallisuus tallennusnappiin.
         * hakee tiedot kaikista textFieldeistä ja lisää mökin niiden perusteella
         */
        tallennusNappi.setOnAction(e->{
            String lisattavanKatuosoite = katuosoiteTF.getText();
            String lisattavanPostinro = postinroTF.getText();
            String lisattavanEtunimi = etunimiTF.getText();
            String lisattavanEmail = spostiTF.getText();
            String lisattavanSukunimi = sukunimiTF.getText();
            String lisattavanSposti = spostiTF.getText();
            String lisattavanPuhnro = puhnroTF.getText();
            /**
             * Käytetään main instanssissa olemassa olevaa connectionia SQL tietojen muokkaamiseen
             */
            main.connect.insertData("asiakas",
                    "postinro, etunimi, sukunimi, lahiosoite, email, puhelinnro",
                    ("\"" + lisattavanPostinro + "\", \"" + lisattavanEtunimi + "\", \"" +
                            lisattavanSukunimi + "\", \"" + lisattavanKatuosoite + "\", \"" +
                            lisattavanEmail + "\", \"" + lisattavanPuhnro + "\""));
        });
        BPAsiakkaanLisaamiselle.setCenter(paneeliUudenAsiakkaanTiedoille);
        Scene lisaysScene = new Scene(BPAsiakkaanLisaamiselle);
        asiakasStage.setScene(lisaysScene);
        asiakasStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
