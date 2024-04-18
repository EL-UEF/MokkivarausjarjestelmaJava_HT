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

public class CustomerHandler extends Application {
    private Main main;
    ArrayList<Asiakas> olemassaolevatAsiakkaat = new ArrayList<>();

    public CustomerHandler(Main main) {
        this.main = main;
    }

    public void asiakasMetodi(Stage asiakasStage){
        BorderPane BPAsiakkaille = new BorderPane();
        //ohjelma lukee tässä aina asiakkaat asiakkaidenluku metodilla, joten sinne tallentamattomat asiakkaat eivät näy listassa!
        asiakkaidenLuku();
        TextArea alueAsiakkaidenTiedoille = new TextArea();
        alueAsiakkaidenTiedoille.setText("Klikkaa asiakasta nähdäksesi sen tarkemmat tiedot :)");
        alueAsiakkaidenTiedoille.setEditable(false);
        ObservableList<Asiakas> luettavaAsiakasLista = FXCollections.observableArrayList(olemassaolevatAsiakkaat);
        ArrayList<String> asiakasNimiLista = new ArrayList<>();
        for (int i=0; i<2; i++){
            asiakasNimiLista.add(luettavaAsiakasLista.get(i).etunimi + " " + luettavaAsiakasLista.get(i).sukunimi);
        }
        ListView<String> asiakasLista = new ListView<>();
        asiakasLista.setItems(FXCollections.observableArrayList(asiakasNimiLista));

        asiakasLista.getSelectionModel().selectedItemProperty().addListener(ov->{
            alueAsiakkaidenTiedoille.setText(
                    luettavaAsiakasLista.get(asiakasLista.getSelectionModel().getSelectedIndex()).toString());
        });
        Button uusiAsiakas = new Button("Lisää uusi asiakas");
        uusiAsiakas.setOnAction(e->{
            asiakkaanLisaysMetodi(asiakasStage);
        });
        Button koti = main.kotiNappain(asiakasStage);
        HBox paneeliAlaValikolle = new HBox(10);
        paneeliAlaValikolle.getChildren().addAll(koti, uusiAsiakas);
        BPAsiakkaille.setLeft(asiakasLista);
        BPAsiakkaille.setCenter(alueAsiakkaidenTiedoille);
        BPAsiakkaille.setBottom(paneeliAlaValikolle);
        Scene scene = new Scene(BPAsiakkaille);
        asiakasStage.setScene(scene);
        asiakasStage.setTitle("Asiakkaat");
        asiakasStage.show();
    }
    public void asiakkaidenLuku(){
        Asiakas asiakas1 = new Asiakas(1, 70200, "Nuutti", "Niiranen", "Kelkkailijantie 4", "Nuutti23@gmail.com", "+358407330654");
        Asiakas asiakas2 = new Asiakas(2, 70200, "Saaga", "Turtiainen", "Kelkkailijantie 4", "sähköposti", "numeroita");

        olemassaolevatAsiakkaat.add(asiakas1);
        olemassaolevatAsiakkaat.add(asiakas2);
    }
    public void asiakkaanLisaysMetodi(Stage lisaysStage){
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




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
