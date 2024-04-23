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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CottageHandler extends Application {
    private Main main;
    Mokki mokki;

    public CottageHandler(Main main, Mokki mokki) {
        this.main = main;
        this.mokki=mokki;
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

        });

        BPMokinLisaamiselle.setCenter(paneeliUudenMokinTiedoille);
        Scene lisaysScene = new Scene(BPMokinLisaamiselle);
        mokkiStage.setScene(lisaysScene);
        mokkiStage.show();
    }

    protected void mokkiMetodi(Stage mokkiStage){
        BorderPane BPmokeille = new BorderPane();
        TextArea alueMokkienTiedoille = new TextArea();
        alueMokkienTiedoille.setText("Klikkaa mökkiä nähdäksesi sen tarkemmat tiedot :)");
        alueMokkienTiedoille.setEditable(false);
        String query = "SELECT mokkinimi, mokki_id FROM mokki";
        ResultSet rs = main.connect.createConnection(query);
        ArrayList<String> mokkiNimiLista = new ArrayList<>();
        try {
            while (rs.next())
                mokkiNimiLista.add(rs.getString("mokki_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(mokkiNimiLista);
        ListView<String> mokkiLista = new ListView<>();
        mokkiLista.setItems(FXCollections.observableArrayList(mokkiNimiLista));
        mokkiLista.getSelectionModel().selectedItemProperty().addListener(ov->{
            valittuIndeksi=Integer.parseInt(mokkiLista.getSelectionModel().getSelectedItem());
            alueMokkienTiedoille.setText(mokki.SQLToString(valittuIndeksi));
            System.out.println("tää toimii");
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
        Button muokkausNappi = new Button("Muokkaa valittua mökkiä");
        muokkausNappi.setOnAction(e->{
            mokinMuokkausMetodi(mokkiStage);
        });
        Button etsintaNappi = new Button("Etsi mökkiä");
        etsintaNappi.setOnAction(e->{
            mokinEtsintaMetodi(mokkiStage);
        });
        HBox paneeliAlaValikolle = new HBox(10);
        paneeliAlaValikolle.getChildren().addAll(kotiNappi, lisaysNappi, muokkausNappi, etsintaNappi, poistoNappi);
        BPmokeille.setBottom(paneeliAlaValikolle);
        BPmokeille.setLeft(mokkiLista);
        BPmokeille.setCenter(alueMokkienTiedoille);
        Scene scene = new Scene(BPmokeille);
        mokkiStage.setTitle("Mökit");
        mokkiStage.setScene(scene);
        mokkiStage.show();
    }
    public void mokinPoisto(int poistettavaIndeksi){
        VBox varoitusPaneeli = new VBox(30);
        varoitusPaneeli.setPrefSize(300, 300);
        varoitusPaneeli.setPadding(new Insets(10, 10, 10, 10));
        Text varoitusTeksti = new Text("Oletko varma että haluat poistaa mökin\n" + mokki.SQLToString(valittuIndeksi)); //TÄHÄN SQL:STÄ MÖKIN TIEDOT
        HBox paneeliValikolle = new HBox(10);
        paneeliValikolle.setAlignment(Pos.CENTER);
        Button haluanPoistaa = new Button("Kyllä");
        Button enHalua = new Button("Ei");
        paneeliValikolle.getChildren().addAll(haluanPoistaa, enHalua);
        varoitusPaneeli.getChildren().addAll(varoitusTeksti, paneeliValikolle);
        Stage popUpStage = new Stage();
        Scene popUpScene = new Scene(varoitusPaneeli);
        haluanPoistaa.setOnAction(e->{
            main.connect.deleteStuff("mokki", "mokki_id", Integer.toString(valittuIndeksi));
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
    public void mokinEtsintaMetodi(Stage etsintaStage){
        BorderPane BPMokinEtsinnalle = new BorderPane();

        VBox paneeliEtsintaKriteereille = new VBox(10);
        paneeliEtsintaKriteereille.setAlignment(Pos.CENTER);
        Text alkuHopina = new Text("Kirjoita haluamasi kriteerit alla oleviin kenttiin.\nVoit jättää kentän tyhjäksi jos et halua käyttää kyseistä kriteeriä");
        Text nimiKriteeri = new Text("Mökin nimi");
        TextField nimiTF = new TextField();
        Text alueKriteeri = new Text("Alueen ID, jolla mökki sijaitsee");
        TextField alueTF = new TextField();
        Text osoiteKriteeri = new Text("Mökin katuosoite");
        TextField osoiteTF = new TextField();
        Text hinnat = new Text("Mökin minimi ja maksimi hinta");
        HBox paneeliHinnoille = new HBox(10);
        TextField minimiHinta = new TextField();
        TextField maksimiHinta = new TextField();
        Label minimiLabel = new Label("Min", minimiHinta);
        minimiLabel.setContentDisplay(ContentDisplay.RIGHT);
        Label maksimiLabel = new Label("Max", maksimiHinta);
        maksimiLabel.setContentDisplay(ContentDisplay.RIGHT);
        paneeliHinnoille.getChildren().addAll(minimiLabel, minimiHinta, maksimiLabel, maksimiHinta);
        Text henkilomaaraKriteeri = new Text("Minimi henkilömäärä");
        TextField henkilomaaraTF = new TextField();
        Text varusteetKriteeri = new Text("Varusteet");
        VBox paneeliCheckBoxeille = new VBox(10);
        paneeliCheckBoxeille.setAlignment(Pos.CENTER);
        CheckBox keittio = new CheckBox("Keittiö");
        CheckBox sauna = new CheckBox("Sauna");
        CheckBox latu = new CheckBox("Hiihtolatu lähellä");
        CheckBox kuivain = new CheckBox("Hiustenkuivain");
        paneeliCheckBoxeille.getChildren().addAll(keittio, sauna, latu, kuivain);
        Button etsi = new Button("Etsi");
        etsi.setOnAction(e->{
            ArrayList<Mokki> nimiKay = new ArrayList<>();
            ArrayList<Mokki> alueKay = new ArrayList<>();
            ArrayList<Mokki> osoiteKay = new ArrayList<>();
            ArrayList<Mokki> tarpeeksiKallis = new ArrayList<>();
            ArrayList<Mokki> tarpeeksiHalpa = new ArrayList<>();
            ArrayList<Mokki> tarpeeksiIso = new ArrayList<>();
            ArrayList<Mokki> kaikkiKay = new ArrayList<>();
            if (!nimiTF.getText().isEmpty()){
                for (int i=0; i < olemassaolevatMokit.size(); i++){
                    if (olemassaolevatMokit.get(i).mokkinimi.equalsIgnoreCase(nimiTF.getText())){
                        nimiKay.add(olemassaolevatMokit.get(i));
                    }
                }
            } else nimiKay.addAll(olemassaolevatMokit);
            if (!alueTF.getText().isEmpty()){
                for (int i=0; i < nimiKay.size(); i++){
                    if (nimiKay.get(i).alue_id==Integer.parseInt(alueTF.getText())){
                        alueKay.add(nimiKay.get(i));
                    }
                }
            } else alueKay.addAll(nimiKay);
            if (!osoiteTF.getText().isEmpty()){
                for (int i=0; i < alueKay.size(); i++){
                    if (alueKay.get(i).katuosoite.equalsIgnoreCase(osoiteTF.getText())){
                        osoiteKay.add(alueKay.get(i));
                    }
                }
            } else osoiteKay.addAll(alueKay);
            if (!minimiHinta.getText().isEmpty()){
                for (int i=0; i < osoiteKay.size(); i++){
                    if (osoiteKay.get(i).hinta>=Double.parseDouble(minimiHinta.getText())){
                        tarpeeksiKallis.add(osoiteKay.get(i));
                    }
                }
            } else tarpeeksiKallis.addAll(osoiteKay);
            if (!maksimiHinta.getText().isEmpty()){
                for (int i=0; i < tarpeeksiKallis.size(); i++){
                    if (tarpeeksiKallis.get(i).hinta<=Double.parseDouble(maksimiHinta.getText())){
                        tarpeeksiHalpa.add(tarpeeksiKallis.get(i));
                    }
                }
            } else tarpeeksiHalpa.addAll(tarpeeksiKallis);
            if (!henkilomaaraTF.getText().isEmpty()) {
                for (int i = 0; i < tarpeeksiHalpa.size(); i++){
                    if (tarpeeksiHalpa.get(i).henkilomaara>=Integer.parseInt(henkilomaaraTF.getText())){
                        tarpeeksiIso.add(tarpeeksiHalpa.get(i));
                    }
                }
            } else tarpeeksiIso.addAll(tarpeeksiHalpa);
            ArrayList<String> mokinVarusteet = new ArrayList<>();
            if (keittio.isSelected())
                mokinVarusteet.add("Keittiö");
            if (sauna.isSelected())
                mokinVarusteet.add("Sauna");
            if (latu.isSelected())
                mokinVarusteet.add("Hiihtolatu lähellä mökkiä");
            if (kuivain.isSelected())
                mokinVarusteet.add("Hiustenkuivain");
            if (keittio.isSelected()||sauna.isSelected()||latu.isSelected()||kuivain.isSelected()){
                for (int i = 0; i < tarpeeksiIso.size(); i++){
                    if (tarpeeksiIso.get(i).varustelu.containsAll(mokinVarusteet)){
                        kaikkiKay.add(tarpeeksiIso.get(i));
                    }
                }
            } else kaikkiKay.addAll(tarpeeksiIso);
            System.out.println(kaikkiKay);
        });

        Button kotiNappi = main.kotiNappain(etsintaStage);
        paneeliEtsintaKriteereille.getChildren().addAll(alkuHopina, nimiKriteeri, nimiTF, alueKriteeri, alueTF, osoiteKriteeri, osoiteTF, hinnat, paneeliHinnoille, henkilomaaraKriteeri, henkilomaaraTF, varusteetKriteeri, paneeliCheckBoxeille, etsi);
        BPMokinEtsinnalle.setCenter(paneeliEtsintaKriteereille);
        BPMokinEtsinnalle.setLeft(kotiNappi);
        Scene scene = new Scene(BPMokinEtsinnalle);
        etsintaStage.setTitle("Mökin haku");
        etsintaStage.setScene(scene);
        etsintaStage.show();
    }
    public void mokinMuokkausMetodi(Stage muokkausStage){
        BorderPane BPMokinMuokkaukselle = new BorderPane();
        VBox paneeliMuokattavilleTiedoille = new VBox(10);
        Text muokattavaMokki = new Text("Muokattava mökki: id: " + olemassaolevatMokit.get(valittuIndeksi).mokki_id + " nimi: " + olemassaolevatMokit.get(valittuIndeksi).mokkinimi);
        Text alueMuokkausTeksti = new Text("Uusi alue id (numero)");
        TextField alueTF = new TextField();
        Text postinroTeksti = new Text("Uusi postinumero");
        TextField postinroTF = new TextField();
        Text nimiTeksti = new Text("Uusi nimi");
        TextField nimiTF = new TextField();
        Text osoiteTeksti = new Text("Haluatko oikeasti muokata osoitetta? Go for it champ...");
        TextField osoiteTF = new TextField();
        Text hintaTeksti = new Text("Uusi hinta");
        TextField hintaTF = new TextField();
        Text kuvausTeksti = new Text("Uusi kuvaus");
        TextField kuvausTF = new TextField();
        Text henkilomaaraTeksti = new Text("Uusi henkilömäärä");
        TextField henkilomaaraTF = new TextField();
        Text varusteetTeksti = new Text("Uudet varusteet (nämä merkattava aina)");
        VBox paneeliCheckBoxeille = new VBox(10);
        paneeliCheckBoxeille.setAlignment(Pos.CENTER);
        CheckBox keittio = new CheckBox("Keittiö");
        CheckBox sauna = new CheckBox("Sauna");
        CheckBox latu = new CheckBox("Hiihtolatu lähellä");
        CheckBox kuivain = new CheckBox("Hiustenkuivain");
        paneeliCheckBoxeille.getChildren().addAll(keittio, sauna, latu, kuivain);
        Button tallennusNappi = new Button("Tallenna");
        tallennusNappi.setOnAction(e->{
            if (!alueTF.getText().isEmpty())
                olemassaolevatMokit.get(valittuIndeksi).alue_id=Integer.parseInt(alueTF.getText());
            if (!postinroTF.getText().isEmpty())
                olemassaolevatMokit.get(valittuIndeksi).postinro=Integer.parseInt(postinroTF.getText());
            if (!nimiTF.getText().isEmpty())
                olemassaolevatMokit.get(valittuIndeksi).mokkinimi=nimiTF.getText();
            if (!osoiteTF.getText().isEmpty())
                olemassaolevatMokit.get(valittuIndeksi).katuosoite=osoiteTF.getText();
            if (!hintaTF.getText().isEmpty())
                olemassaolevatMokit.get(valittuIndeksi).hinta=Double.parseDouble(hintaTF.getText());
            if (!kuvausTF.getText().isEmpty())
                olemassaolevatMokit.get(valittuIndeksi).kuvaus=kuvausTF.getText();
            if (!henkilomaaraTF.getText().isEmpty())
                olemassaolevatMokit.get(valittuIndeksi).henkilomaara=Integer.parseInt(henkilomaaraTF.getText());
            if (keittio.isSelected()||sauna.isSelected()||latu.isSelected()||kuivain.isSelected()){
                olemassaolevatMokit.get(valittuIndeksi).varustelu.clear();
                if (keittio.isSelected())
                    olemassaolevatMokit.get(valittuIndeksi).varustelu.add("Keittiö");
                if (sauna.isSelected())
                    olemassaolevatMokit.get(valittuIndeksi).varustelu.add("Sauna");
                if (latu.isSelected())
                    olemassaolevatMokit.get(valittuIndeksi).varustelu.add("Hiihtolatu lähellä mökkiä");
                if (kuivain.isSelected())
                    olemassaolevatMokit.get(valittuIndeksi).varustelu.add("Hiustenkuivain");
            }
            System.out.println(olemassaolevatMokit.get(valittuIndeksi));
        });
        paneeliMuokattavilleTiedoille.getChildren().addAll(muokattavaMokki, alueMuokkausTeksti, alueTF, postinroTeksti,
                postinroTF, nimiTeksti, nimiTF, osoiteTeksti, osoiteTF, hintaTeksti,
                hintaTF, kuvausTeksti, kuvausTF, henkilomaaraTeksti, henkilomaaraTF, varusteetTeksti, paneeliCheckBoxeille, tallennusNappi);
        Button kotiNappula = main.kotiNappain(muokkausStage);
        BPMokinMuokkaukselle.setLeft(kotiNappula);
        paneeliMuokattavilleTiedoille.setAlignment(Pos.CENTER);
        paneeliMuokattavilleTiedoille.setPadding(new Insets(10, 10, 10, 10));
        BPMokinMuokkaukselle.setCenter(paneeliMuokattavilleTiedoille);
        Scene scene = new Scene(BPMokinMuokkaukselle);
        muokkausStage.setTitle("Mökin tietojen muokkaus");
        muokkausStage.setScene(scene);
        muokkausStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    }
}
