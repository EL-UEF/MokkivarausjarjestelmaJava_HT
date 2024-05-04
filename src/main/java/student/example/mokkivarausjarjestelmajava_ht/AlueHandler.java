package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
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

public class AlueHandler extends Application {
    private Main main;
    Alue alue;
    String valittuNimi;

    public AlueHandler(Main main, Alue alue) {
        this.main = main;
        this.alue = alue;
    }

    public void alueMetodi(Stage alueStage, ResultSet rs){
        BorderPane BPAlueille = new BorderPane();
        TextArea alueAlueidenTiedoille = new TextArea();
        alueAlueidenTiedoille.setText("Klikkaa aluetta nähdäksesi sen tarkemmat tiedot :)");
        alueAlueidenTiedoille.setEditable(false);
        ArrayList<String> alueNimiLista = new ArrayList<>();
        try {
            while (rs.next())
                alueNimiLista.add(rs.getString("nimi"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ListView<String> alueLista = new ListView<>();
        alueLista.setItems(FXCollections.observableArrayList(alueNimiLista));
        alueLista.getSelectionModel().selectedItemProperty().addListener(ov->{
            valittuNimi = alueLista.getSelectionModel().getSelectedItem();
            alueAlueidenTiedoille.setText(alue.SQLToStringAlue(valittuNimi));
        });
        Button uusiAlue = new Button("Lisää uusi alue");
        Button muokkausNappi = new Button("Muokkaa valittua aluetta");
        muokkausNappi.setOnAction(e->{
            alueenMuokkausMetodi(alueStage);
        });
        uusiAlue.setOnAction(e->{
            alueenLisaysMetodi(alueStage);
        });
        Button etsintaNappi = new Button("Etsi alue");
        etsintaNappi.setOnAction(event -> {
            alueenEtsintäMetodi(alueStage);
        });
        Button koti = main.kotiNappain(alueStage);
        Button poistoNappi = new Button("Poista valittu alue");
        poistoNappi.setOnAction(e->{
            alueenPoisto();
        });

        HBox paneeliAlaValikolle = new HBox(10);
        paneeliAlaValikolle.getChildren().addAll(koti, muokkausNappi, uusiAlue, etsintaNappi, poistoNappi);
        BPAlueille.setLeft(alueLista);
        BPAlueille.setCenter(alueAlueidenTiedoille);
        BPAlueille.setBottom(paneeliAlaValikolle);
        Scene scene = new Scene(BPAlueille);
        alueStage.setScene(scene);
        alueStage.setTitle("Alueet");
        alueStage.show();
    }


    public void alueenLisaysMetodi(Stage alueenLisaysStage){
        BorderPane BPAlueidenLisaamiselle = new BorderPane();
        BPAlueidenLisaamiselle.setPrefSize(400, 400);
        BPAlueidenLisaamiselle.setPadding(new Insets(10, 10, 10, 10));

        VBox paneeliUudenAlueenTiedoille = new VBox(10);
        Text alkuHopina = new Text("Alueen ID täytetään automaattisesti. Syötä lisättävän alueen nimi");
        TextField nimiTF = new TextField();
        Button lisaysNappi = new Button("Lisää");
        lisaysNappi.setOnAction(e->{
            String uusiNimi = nimiTF.getText();
            main.connect.insertData("alue", "nimi","\"" + uusiNimi + "\"");
        });
        Button kotiNappula = main.kotiNappain(alueenLisaysStage);
        paneeliUudenAlueenTiedoille.setAlignment(Pos.CENTER);
        paneeliUudenAlueenTiedoille.getChildren().addAll(alkuHopina, nimiTF, lisaysNappi, kotiNappula);
        BPAlueidenLisaamiselle.setCenter(paneeliUudenAlueenTiedoille);
        Scene scene = new Scene(BPAlueidenLisaamiselle);
        alueenLisaysStage.setScene(scene);
        alueenLisaysStage.setTitle("Lisää uusi alue");
        alueenLisaysStage.show();
    }

    public void alueenMuokkausMetodi(Stage muokkausStage){
        BorderPane BPAlueenMuokkaamiselle = new BorderPane();
        BPAlueenMuokkaamiselle.setPrefSize(400, 400);
        VBox paneeliMuokattavilleTiedoille = new VBox(10);
        paneeliMuokattavilleTiedoille.setAlignment(Pos.CENTER);
        Text nimiTeksti = new Text("Anna alueen " +alue.SQLToStringAlue(valittuNimi) + " uusi nimi");
        TextField nimiTF = new TextField();
        Button tallenna = new Button("Tallenna");
        tallenna.setOnAction(e->{
            String uusiNimi = nimiTF.getText();
            main.connect.updateTable("alue","nimi","\"" + uusiNimi + "\"",("nimi = "+"\"" + valittuNimi + "\""));
        });
        Button kotiNappula = main.kotiNappain(muokkausStage);
        paneeliMuokattavilleTiedoille.getChildren().addAll(nimiTeksti, nimiTF, tallenna, kotiNappula);
        paneeliMuokattavilleTiedoille.setAlignment(Pos.CENTER);
        BPAlueenMuokkaamiselle.setCenter(paneeliMuokattavilleTiedoille);

        Scene scene = new Scene(BPAlueenMuokkaamiselle);
        muokkausStage.setScene(scene);
        muokkausStage.setTitle("Muokkaa aluetta");
        muokkausStage.show();
    }
    protected void alueenEtsintäMetodi(Stage etsintaStage){
        BorderPane BPMokinEtsinnalle = new BorderPane();
        VBox paneeliEtsintaKriteereille = new VBox(10);
        paneeliEtsintaKriteereille.setAlignment(Pos.CENTER);
        Text alkuHopina = new Text("Kirjoita haluamasi alueen nimen alla oleviin kenttiin.");
        Text nimiKriteeri = new Text("Alueen nimi");
        TextField nimiTF = new TextField();
        Button etsi = new Button("Etsi");
        //toiminnallisuus
        etsi.setOnAction(e->{
            if (!nimiTF.getText().isEmpty()) {
            String kriteerit = nimiTF.getText();
            alueMetodi(etsintaStage, main.connect.searchForStuff("alue", "LOWER(nimi) LIKE '%" + kriteerit.toLowerCase() + "%'"));
        }});
        Button kotiNappi = main.kotiNappain(etsintaStage);
        paneeliEtsintaKriteereille.getChildren().addAll(alkuHopina, nimiKriteeri, nimiTF, etsi);
        BPMokinEtsinnalle.setCenter(paneeliEtsintaKriteereille);
        BPMokinEtsinnalle.setLeft(kotiNappi);
        Scene scene = new Scene(BPMokinEtsinnalle);
        etsintaStage.setTitle("Alueen haku");
        etsintaStage.setScene(scene);
        etsintaStage.show();
    }
    public void alueenPoisto(){
        VBox varoitusPaneeli = new VBox(30);
        varoitusPaneeli.setPrefSize(300, 300);
        varoitusPaneeli.setPadding(new Insets(10, 10, 10, 10));
        Text varoitusTeksti = new Text("Oletko varma että haluat poistaa alueen\n" + alue.SQLToStringAlue(valittuNimi));
        HBox paneeliValikolle = new HBox(10);
        paneeliValikolle.setAlignment(Pos.CENTER);
        Button haluanPoistaa = new Button("Kyllä");
        Button enHalua = new Button("Ei");
        paneeliValikolle.getChildren().addAll(haluanPoistaa, enHalua);
        varoitusPaneeli.getChildren().addAll(varoitusTeksti, paneeliValikolle);
        Stage popUpStage = new Stage();
        Scene popUpScene = new Scene(varoitusPaneeli);
        haluanPoistaa.setOnAction(e->{
            main.connect.deleteStuff("alue", "nimi", ("'" + valittuNimi + "'"));
            System.out.println("Alue poistettu onnistuneesti");
            popUpStage.close();
        });
        enHalua.setOnAction(e->{
            System.out.println("Aluetta ei poistettu");
            popUpStage.close();
        });

        popUpStage.setScene(popUpScene);
        popUpStage.setTitle("VAROITUS");
        popUpStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
