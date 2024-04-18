package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CottageHandler extends Application {
    private Main main;

    public CottageHandler(Main main) {
        this.main = main;
    }

    public CottageHandler() {
    }
    int valittuIndeksi=-1;

    ArrayList<Mokki> olemassaolevatMokit = new ArrayList<>();
    protected void mokinLisaysMetodi(Stage mokkiStage){
        BorderPane BPMokinLisaamiselle = new BorderPane();

        VBox paneeliUudenMokinTiedoille = new VBox(10);
        Text annaAlue = new Text("Mille alueelle mökki kuuluu? (Alue ID, katuosoite ja postinumero)");
        TextField alueTF = new TextField("Alue");
        TextField postinroTF = new TextField("Postinumero");
        TextField katuosoiteTF = new TextField("Katuosoite");
        HBox paneeliAlueelle = new HBox(10);
        paneeliAlueelle.getChildren().addAll(alueTF, katuosoiteTF, postinroTF);
        Text mokinNimi = new Text("Anna mökin nimi");
        TextField nimiTF = new TextField();
        Text hintaTeksti = new Text("Anna mökin hinta/yö");
        TextField hintaTF = new TextField();
        Text kuvausTeksti = new Text("Anna mökin kuvaus");
        TextField kuvausTF = new TextField();
        Text henkilomaaraTeksti = new Text("Anna mökin henkilömäärä");
        TextField henkiloTF = new TextField();
        HBox paneeliMokinVarusteille = new HBox(10);
        Text varusteTeksti = new Text("Mitä varusteita mökissä on?");
        VBox paneeliCheckBoxeille = new VBox(10);
        CheckBox keittio = new CheckBox("Keittiö");
        CheckBox sauna = new CheckBox("Sauna");
        CheckBox latu = new CheckBox("Hiihtolatu lähellä");
        CheckBox kuivain = new CheckBox("Hiustenkuivain");
        Button tallennusNappi = new Button("Tallenna");
        tallennusNappi.setAlignment(Pos.CENTER);
        Button kotiNappi = main.kotiNappain(mokkiStage);
        BPMokinLisaamiselle.setBottom(kotiNappi);
        paneeliCheckBoxeille.getChildren().addAll(keittio, sauna, latu, kuivain);
        paneeliMokinVarusteille.getChildren().addAll(varusteTeksti, paneeliCheckBoxeille);
        paneeliUudenMokinTiedoille.getChildren().addAll(annaAlue, paneeliAlueelle, mokinNimi, nimiTF, hintaTeksti, hintaTF, kuvausTeksti, kuvausTF,
                henkilomaaraTeksti, henkiloTF, paneeliMokinVarusteille, tallennusNappi);
        paneeliUudenMokinTiedoille.setAlignment(Pos.CENTER);

        //toiminnallisuus
        tallennusNappi.setOnAction(e->{
            int mokinID = 0;
            for (int i = 0; i<olemassaolevatMokit.size(); i++){
                mokinID=i;
                i++;
            }
            int mokinAlue = Integer.parseInt(alueTF.getText());
            int mokinPostinumero = Integer.parseInt(postinroTF.getText());
            String lisattavanMokinNimi = nimiTF.getText();
            String lisattavaOsoite = katuosoiteTF.getText();
            Double lisattavaHinta = Double.parseDouble(hintaTF.getText());
            String lisattavaKuvaus = kuvausTF.getText();
            int lisattavaHenkilomaara = Integer.parseInt(henkiloTF.getText());
            ArrayList<String> lisattavatVarusteet = new ArrayList<>();
            if (keittio.isSelected())
                lisattavatVarusteet.add("Keittiö");
            if (sauna.isSelected())
                lisattavatVarusteet.add("Sauna");
            if (latu.isSelected())
                lisattavatVarusteet.add("Hiihtolatu lähellä mökkiä");
            if (kuivain.isSelected())
                lisattavatVarusteet.add("Hiustenkuivain");
            Mokki uusiMokki = new Mokki(mokinID, mokinAlue, mokinPostinumero, lisattavanMokinNimi, lisattavaOsoite, lisattavaHinta, lisattavaKuvaus, lisattavaHenkilomaara, lisattavatVarusteet);
            olemassaolevatMokit.add(uusiMokki);
            System.out.println(uusiMokki);
        });

        BPMokinLisaamiselle.setCenter(paneeliUudenMokinTiedoille);
        Scene lisaysScene = new Scene(BPMokinLisaamiselle);
        mokkiStage.setScene(lisaysScene);
        mokkiStage.show();
    }
    protected void mokkienLuku(){
        //TÄMÄN PITÄISI LUKEA MÖKIT SQL:STÄ
        ArrayList<String> mokin1Varustelu = new ArrayList<>();
        mokin1Varustelu.add("Keittiö");
        mokin1Varustelu.add("vessa");
        Mokki mokki1 = new Mokki(1, 1, 70800, "HassuMökki", "Hassunmökintie 7", 200.0, "mökki hassuille asiakkaille", 5,
                mokin1Varustelu);
        Mokki mokki2 = new Mokki(2, 2, 70900, "HöpsöMökki", "Höpsönmökintie 7", 250.0, "mökki höpsöille asiakkaille", 10,
                mokin1Varustelu);
        olemassaolevatMokit.add(mokki1);
        olemassaolevatMokit.add(mokki2);
    }

    protected void mokkiMetodi(Stage mokkiStage){
        BorderPane BPmokeille = new BorderPane();
        //ohjelma lukee tässä aina mökit mökkienluku metodilla, joten sinne tallentamattomat mökit eivät näy listassa!
        mokkienLuku();
        TextArea alueMokkienTiedoille = new TextArea();
        alueMokkienTiedoille.setText("Klikkaa mökkiä nähdäksesi sen tarkemmat tiedot :)");
        alueMokkienTiedoille.setEditable(false);
        ObservableList<Mokki> luettavaMokkiLista = FXCollections.observableArrayList(olemassaolevatMokit);
        ArrayList<String> MokinNimiLista = new ArrayList<>();
        for (int i=0; i<2; i++){
            MokinNimiLista.add(luettavaMokkiLista.get(i).mokkinimi);
        }
        ListView<String> mokkiLista = new ListView<>();
        mokkiLista.setItems(FXCollections.observableArrayList(MokinNimiLista));

        mokkiLista.getSelectionModel().selectedItemProperty().addListener(ov->{
            alueMokkienTiedoille.setText(
                    luettavaMokkiLista.get(mokkiLista.getSelectionModel().getSelectedIndex()).toString());
            valittuIndeksi=mokkiLista.getSelectionModel().getSelectedIndex();
        });

        Button kotiNappi = main.kotiNappain(mokkiStage);
        Button lisaysNappi = new Button("Lisää uusi mökki");
        lisaysNappi.setOnAction(e->{
            mokinLisaysMetodi(mokkiStage);
        });
        Button poistoNappi = new Button("Poista valittu mökki");
        poistoNappi.setOnAction(e->{
            mokinPoisto(valittuIndeksi);
        });
        HBox paneeliAlaValikolle = new HBox(10);
        paneeliAlaValikolle.getChildren().addAll(kotiNappi, lisaysNappi, poistoNappi);
        BPmokeille.setBottom(paneeliAlaValikolle);
        BPmokeille.setLeft(mokkiLista);
        BPmokeille.setCenter(alueMokkienTiedoille);
        Scene scene = new Scene(BPmokeille);
        mokkiStage.setTitle("Mökit");
        mokkiStage.setScene(scene);
        mokkiStage.show();
    }
    public void mokinPoisto(int poistettavaIndeksi){
        System.out.println(olemassaolevatMokit.get(poistettavaIndeksi));

        VBox varoitusPaneeli = new VBox(30);
        varoitusPaneeli.setPrefSize(300, 300);
        varoitusPaneeli.setPadding(new Insets(10, 10, 10, 10));
        Text varoitusTeksti = new Text("Oletko varma että haluat poistaa mökin\n" + olemassaolevatMokit.get(poistettavaIndeksi));
        HBox paneeliValikolle = new HBox(10);
        paneeliValikolle.setAlignment(Pos.CENTER);
        Button haluanPoistaa = new Button("Kyllä");
        Button enHalua = new Button("Ei");
        paneeliValikolle.getChildren().addAll(haluanPoistaa, enHalua);
        varoitusPaneeli.getChildren().addAll(varoitusTeksti, paneeliValikolle);
        Stage popUpStage = new Stage();
        Scene popUpScene = new Scene(varoitusPaneeli);
        haluanPoistaa.setOnAction(e->{
            olemassaolevatMokit.remove(poistettavaIndeksi);
            System.out.println("mökki poistettu onnistuneesti");
            popUpStage.close();
        });
        enHalua.setOnAction(e->{
            System.out.println("Mökkiä ei poistettu");
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
