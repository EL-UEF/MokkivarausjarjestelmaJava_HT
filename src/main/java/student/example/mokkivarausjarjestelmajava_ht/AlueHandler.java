package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class AlueHandler extends Application {
    private Main main;
    Alue alue;
    int valittuIndeksi=-1;

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
                alueNimiLista.add(rs.getString("alue_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ListView<String> alueLista = new ListView<>();
        alueLista.setItems(FXCollections.observableArrayList(alueNimiLista));
        alueLista.getSelectionModel().selectedItemProperty().addListener(ov->{
            valittuIndeksi=Integer.parseInt(alueLista.getSelectionModel().getSelectedItem());
            alueAlueidenTiedoille.setText(alue.SQLToStringAlue(valittuIndeksi));
        });
        Button uusiAlue = new Button("Lisää uusi alue");
        Button muokkausNappi = new Button("Muokkaa valittua aluetta");
        muokkausNappi.setOnAction(e->{
            alueenMuokkausMetodi(alueStage);
        });
        uusiAlue.setOnAction(e->{
            alueenLisaysMetodi(alueStage);
        });

        Button koti = main.kotiNappain(alueStage);
        HBox paneeliAlaValikolle = new HBox(10);
        paneeliAlaValikolle.getChildren().addAll(koti, muokkausNappi, uusiAlue);
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
        Text nimiTeksti = new Text("Anna alueen " +alue.SQLToStringAlue(valittuIndeksi) + " uusi nimi");
        TextField nimiTF = new TextField();
        Button tallenna = new Button("Tallenna");
        tallenna.setOnAction(e->{
            String uusiNimi = nimiTF.getText();
            main.connect.updateTable("alue","nimi","\"" + uusiNimi + "\"",("alue_id = "+valittuIndeksi));
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
