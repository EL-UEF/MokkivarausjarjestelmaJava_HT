package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
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
        BPAsiakkaille.setLeft(asiakasLista);
        BPAsiakkaille.setCenter(alueAsiakkaidenTiedoille);
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




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
