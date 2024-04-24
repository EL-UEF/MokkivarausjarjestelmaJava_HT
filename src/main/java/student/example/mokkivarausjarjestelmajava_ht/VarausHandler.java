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

public class VarausHandler extends Application {
    private Main main;
    private Varaus varaus;
    int valittuIndeksi=-1;
    public VarausHandler(Main main, Varaus varaus){
        this.main=main;
        this.varaus=varaus;
    }

    protected void varausMetodi(Stage palveluStage, ResultSet rs){
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
        Button kotiNappi = main.kotiNappain(palveluStage);
        Button lisaysNappi = new Button("Lisää uusi palvelu");
        /*
        lisaysNappi.setOnAction(e->{
            palvelunLisaysMetodi(palveluStage);
        });
        Button poistoNappi = new Button("Poista valittu palvelu");
        poistoNappi.setOnAction(e->{
            palvelunPoisto();
        });
        Button muokkausNappi = new Button("Muokkaa valittua palvelua");
        muokkausNappi.setOnAction(e->{
            palvelunMuokkausMetodi(palveluStage);
        });

        Button etsintaNappi = new Button("Etsi palvelua");
        etsintaNappi.setOnAction(e->{
            palvelunEtsintaMetodi(palveluStage);
        });
         */
        HBox paneeliAlaValikolle = new HBox(10);
        paneeliAlaValikolle.getChildren().addAll(kotiNappi, lisaysNappi);//,muokkausNappi, etsintaNappi, poistoNappi);
        BPvarauksille.setBottom(paneeliAlaValikolle);
        BPvarauksille.setLeft(varausLista);
        BPvarauksille.setCenter(alueVaraustenTiedoille);
        Scene scene = new Scene(BPvarauksille);
        palveluStage.setTitle("Palvelut");
        palveluStage.setScene(scene);
        palveluStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
