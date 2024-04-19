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

import java.util.ArrayList;

public class AlueHandler extends Application {
    ArrayList<Alue> olemassaolevatAlueet = new ArrayList<>();
    private Main main;
    Alue valittuAlue;

    public AlueHandler() {
    }

    public AlueHandler(Main main) {
        this.main = main;
    }

    public void alueMetodi(Stage alueStage){
        BorderPane BPAlueille = new BorderPane();
        alueenLuontiMetodi();

        TextArea alueAlueidenTiedoille = new TextArea();
        alueAlueidenTiedoille.setText("Klikkaa aluetta nähdäksesi sen tarkemmat tiedot :)");
        alueAlueidenTiedoille.setEditable(false);
        ObservableList<Alue> luettavaAlueLista = FXCollections.observableArrayList(olemassaolevatAlueet);
        ArrayList<String> alueNimiLista = new ArrayList<>();
        for (int i=0; i<2; i++){
            alueNimiLista.add(luettavaAlueLista.get(i).nimi);
        }
        ListView<String> alueLista = new ListView<>();
        alueLista.setItems(FXCollections.observableArrayList(alueNimiLista));

        alueLista.getSelectionModel().selectedItemProperty().addListener(ov->{
            alueAlueidenTiedoille.setText(
                    luettavaAlueLista.get(alueLista.getSelectionModel().getSelectedIndex()).toString());
            valittuAlue=luettavaAlueLista.get(alueLista.getSelectionModel().getSelectedIndex());
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
    public void alueenLuontiMetodi(){
        Alue alue1 = new Alue(1, "Tahko");
        Alue alue2 = new Alue(2, "Ruka");

        olemassaolevatAlueet.add(alue1);
        olemassaolevatAlueet.add(alue2);
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
            int uusiID = olemassaolevatAlueet.size() + 1;
            String uusiNimi = nimiTF.getText();
            olemassaolevatAlueet.add(new Alue(uusiID, uusiNimi));
            System.out.println(olemassaolevatAlueet);
        });
        paneeliUudenAlueenTiedoille.setAlignment(Pos.CENTER);
        paneeliUudenAlueenTiedoille.getChildren().addAll(alkuHopina, nimiTF, lisaysNappi);
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
        Text nimiTeksti = new Text("Anna alueen " + valittuAlue.nimi + " uusi nimi");
        TextField nimiTF = new TextField();
        Button tallenna = new Button("Tallenna");
        tallenna.setOnAction(e->{
            valittuAlue.nimi=nimiTF.getText();
            System.out.println(valittuAlue);
        });
        paneeliMuokattavilleTiedoille.getChildren().addAll(nimiTeksti, nimiTF, tallenna);
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
