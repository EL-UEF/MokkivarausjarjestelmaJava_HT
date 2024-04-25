package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.collections.FXCollections;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class VarausHandler extends Application {
    private Main main;
    private Varaus varaus;
    int valittuIndeksi=-1;
    public VarausHandler(Main main, Varaus varaus){
        this.main=main;
        this.varaus=varaus;
    }

    protected void varausMetodi(Stage varausStage, ResultSet rs){
        BorderPane BPvarauksille = new BorderPane();
        TextArea alueVaraustenTiedoille = new TextArea();
        alueVaraustenTiedoille.setText("Klikkaa varausta nähdäksesi sen tarkemmat tiedot :)");
        alueVaraustenTiedoille.setEditable(false);
        /**
         * Logiikka palveluiden indeksien näyttämiselle ListViewissä ja tietojen hakemiselle tietokannasta
         */
        ArrayList<String> varausNimiLista = new ArrayList<>();
        try {
            while (rs.next())
                varausNimiLista.add(rs.getString("varaus_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(varausNimiLista);
        ListView<String> varausLista = new ListView<>();
        varausLista.setItems(FXCollections.observableArrayList(varausNimiLista));
        varausLista.getSelectionModel().selectedItemProperty().addListener(ov->{
            valittuIndeksi=Integer.parseInt(varausLista.getSelectionModel().getSelectedItem());
            alueVaraustenTiedoille.setText(varaus.SQLToString(valittuIndeksi));
        });
        Button kotiNappi = main.kotiNappain(varausStage);
        Button lisaysNappi = new Button("Tee uusi varaus");

        lisaysNappi.setOnAction(e->{
            varauksenLisaysMetodi(varausStage);
        });
        /*
        Button poistoNappi = new Button("Poista valittu palvelu");
        poistoNappi.setOnAction(e->{
            palvelunPoisto();
        });
        Button muokkausNappi = new Button("Muokkaa valittua palvelua");
        muokkausNappi.setOnAction(e->{
            palvelunMuokkausMetodi(varausStage);
        });

        Button etsintaNappi = new Button("Etsi palvelua");
        etsintaNappi.setOnAction(e->{
            palvelunEtsintaMetodi(varausStage);
        });
         */
        HBox paneeliAlaValikolle = new HBox(10);
        paneeliAlaValikolle.getChildren().addAll(kotiNappi, lisaysNappi);//,muokkausNappi, etsintaNappi, poistoNappi);
        BPvarauksille.setBottom(paneeliAlaValikolle);
        BPvarauksille.setLeft(varausLista);
        BPvarauksille.setCenter(alueVaraustenTiedoille);
        Scene scene = new Scene(BPvarauksille);
        varausStage.setTitle("Palvelut");
        varausStage.setScene(scene);
        varausStage.show();
    }
    protected void varauksenLisaysMetodi(Stage palveluStage){
        /**
         * graafisia komponentteja ja niiden sijoittelua
         */
        BorderPane BPvarauksenLisaamiselle = new BorderPane();
        BPvarauksenLisaamiselle.setPrefSize(400, 400);
        VBox paneeliUudenVarauksenTiedoille = new VBox(10);
        Text annaAsiakas = new Text("Asiakkaan id");
        TextField asiakasidTF = new TextField();
        Text varattavaMokki = new Text("Varattavan mökin id");
        TextField mokkiTF = new TextField();
        HBox paneeliVarauksenAlulle = new HBox(10);
        Text alkuPrompt = new Text("Syötä varauksen alkupäivä muodossa pp.kk.vvvv");
        TextField TFAlkuVuosille = new TextField();
        TextField TFAlkuKuukausille = new TextField();
        TextField TFAlkuPaivalle = new TextField();
        paneeliVarauksenAlulle.getChildren().addAll(TFAlkuPaivalle, TFAlkuKuukausille, TFAlkuVuosille);
        HBox paneeliVarauksenLopulle = new HBox(10);
        Text loppuPrompt = new Text("Syötä varauksen loppumispäivä muodossa pp.kk.vvvv");
        TextField TFLoppuVuosille = new TextField();
        TextField TFLoppuKuukausille = new TextField();
        TextField TFLoppuPaivalle = new TextField();
        paneeliVarauksenLopulle.getChildren().addAll(TFLoppuPaivalle, TFLoppuKuukausille, TFLoppuVuosille);


        Button tallennusNappi = new Button("Tallenna");
        tallennusNappi.setAlignment(Pos.CENTER);
        Button kotiNappi = main.kotiNappain(palveluStage);
        BPvarauksenLisaamiselle.setBottom(kotiNappi);
        paneeliUudenVarauksenTiedoille.getChildren().addAll(annaAsiakas, asiakasidTF, varattavaMokki, mokkiTF, alkuPrompt, paneeliVarauksenAlulle,
                loppuPrompt, paneeliVarauksenLopulle, tallennusNappi);
        paneeliUudenVarauksenTiedoille.setAlignment(Pos.CENTER);
        /**
         * Toiminnallisuus tallennusnappiin.
         * hakee tiedot kaikista textFieldeistä ja lisää palvelun niiden perusteella
         * pelleilty DateTime formattien kanssa oikein huolella mutta nyt toimii
         */
        DateTimeFormatter userInputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        tallennusNappi.setOnAction(e->{
            String asiakkaanID = asiakasidTF.getText();
            String varattavanMokinID = mokkiTF.getText();
            LocalDateTime alkupvm = LocalDateTime.parse(TFAlkuVuosille.getText() + "-" + TFAlkuKuukausille.getText() + "-" + TFAlkuPaivalle.getText() + " 15:00:00", userInputFormatter);
            LocalDateTime loppupvm = LocalDateTime.parse(TFLoppuVuosille.getText() + "-" + TFLoppuKuukausille.getText() + "-" + TFLoppuPaivalle.getText() + " 12:00:00", userInputFormatter);
            DateTimeFormatter mysqlDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatoituAlkuPaiva = alkupvm.format(mysqlDateFormatter);
            String formatoituLoppuPaiva = loppupvm.format(mysqlDateFormatter);
            /**
             * Käytetään main instanssissa olemassa olevaa connectionia SQL tietojen muokkaamiseen
             */
            main.connect.insertData("varaus", "asiakas_id, mokki_id, varattu_pvm, vahvistus_pvm, varattu_alkupvm, varattu_loppupvm",
                    (asiakkaanID + ", " + varattavanMokinID + ", " + "NOW()" + ", NOW(), " + "'" + formatoituAlkuPaiva + "'" + ", " + "'" + formatoituLoppuPaiva + "'"));
        });
        BPvarauksenLisaamiselle.setCenter(paneeliUudenVarauksenTiedoille);
        Scene lisaysScene = new Scene(BPvarauksenLisaamiselle);
        palveluStage.setScene(lisaysScene);
        palveluStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
