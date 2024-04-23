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
import java.util.List;

/**
 * Luokka mökkeihin liittyvän käyttöliittymän grafiikalle ja logiikalle
 * Luokka lisää, lukee, muokkaa ja poistaa tietoja suoraan SQL palvelimelta
 */
public class CottageHandler extends Application {
    /**
     * Luotu main instance jotta voidaan käyttää Mainin metodeja
     */
    private Main main;
    /**
     * Luotu Mokki instance jotta voidaan käyttää Mokkin metodeja
     */
    Mokki mokki;

    /**
     * Alustaja jota Main käyttää
     * @param main Main instance
     * @param mokki Mainissa luotu Mokki instance
     */
    public CottageHandler(Main main, Mokki mokki) {
        this.main = main;
        this.mokki=mokki;
    }

    /**
     * Indeksi, joka kertoo mikä mökki on valittuna tällä hetkellä listViewissä.
     * Alustettu olemaan -1 jotta ei voida vahingossa muokata minkään mökin tietoja valitsematta mökkiä
     */
    int valittuIndeksi=-1;

    /**
     * Metodi, jolla voidaan lisätä mökkejä SQL tietokantaan
     * Kaikki kentät on täytettävä jotta tämä metodi toimisi. Tämä tahallista, jotta saamme tarjottua asiakkaille täydelliset tiedot mökeistä
     * @param mokkiStage Stage, jonka sisältö halutaan muuttaa mökkien lisäysvalikoksi
     */
    protected void mokinLisaysMetodi(Stage mokkiStage){
        /**
         * graafisia komponentteja ja niiden sijoittelua
         */
        BorderPane BPMokinLisaamiselle = new BorderPane();
        VBox paneeliUudenMokinTiedoille = new VBox(10);
        Text annaAlue = new Text("Mille alueelle mökki kuuluu? (Alue ID, katuosoite ja postinumero)");
        TextField alueTF = new TextField();
        TextField postinroTF = new TextField();
        TextField katuosoiteTF = new TextField();
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
        /**
         * Toiminnallisuus tallennusnappiin.
         * hakee tiedot kaikista textFieldeistä ja lisää mökin niiden perusteella
         */
        tallennusNappi.setOnAction(e->{
            String mokinAlue = alueTF.getText();
            String mokinPostinumero = postinroTF.getText();
            String lisattavanMokinNimi = nimiTF.getText();
            String lisattavaOsoite = katuosoiteTF.getText();
            String lisattavaHinta = hintaTF.getText();
            String lisattavaKuvaus = kuvausTF.getText();
            String lisattavaHenkilomaara = henkiloTF.getText();
            String lisattavatVarusteet = "";
            if (keittio.isSelected())
                lisattavatVarusteet+="Keittiö";
            if (sauna.isSelected()) {
                if (!lisattavatVarusteet.isEmpty()) {
                    lisattavatVarusteet += ", ";
                }
                lisattavatVarusteet += "Sauna";
            }
            if (latu.isSelected()) {
                if (!lisattavatVarusteet.isEmpty()) {
                    lisattavatVarusteet += ", ";
                }
                lisattavatVarusteet += "Hiihtolatu lähellä mökkiä";
            }
            if (kuivain.isSelected()) {
                if (!lisattavatVarusteet.isEmpty()) {
                    lisattavatVarusteet += ", ";
                }
                lisattavatVarusteet += "Hiustenkuivain";
            }
            /**
             * Käytetään main instanssissa olemassa olevaa connectionia SQL tietojen muokkaamiseen
             */
            main.connect.insertData("mokki", "alue_id, postinro, mokkinimi, katuosoite, hinta, kuvaus, henkilomaara, varustelu",
                    (mokinAlue + ", " + mokinPostinumero + ", \"" + lisattavanMokinNimi + "\", \"" + lisattavaOsoite + "\", " + lisattavaHinta +
                            ", \"" + lisattavaKuvaus + "\", " + lisattavaHenkilomaara + ", \""+ lisattavatVarusteet + "\""));
        });
        BPMokinLisaamiselle.setCenter(paneeliUudenMokinTiedoille);
        Scene lisaysScene = new Scene(BPMokinLisaamiselle);
        mokkiStage.setScene(lisaysScene);
        mokkiStage.show();
    }

    /**
     * Luo valikon mökkienhallinnan päänäkymälle ja mökkien etsinnän tulokselle.
     * Toimii SQL kanssa.
     * @param mokkiStage stage, johon valikko halutaan tehdä
     * @param rs resultset joka haetaan SQL tietokannasta, sisältää mökkien tiedot
     */
    protected void mokkiMetodi(Stage mokkiStage, ResultSet rs){
        BorderPane BPmokeille = new BorderPane();
        TextArea alueMokkienTiedoille = new TextArea();
        alueMokkienTiedoille.setText("Klikkaa mökkiä nähdäksesi sen tarkemmat tiedot :)");
        alueMokkienTiedoille.setEditable(false);
        /**
         * Logiikka mökkien indeksien näyttämiselle ListViewissä ja tietojen hakemiselle tietokannasta
         */
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
        });
        Button kotiNappi = main.kotiNappain(mokkiStage);
        Button lisaysNappi = new Button("Lisää uusi mökki");
        lisaysNappi.setOnAction(e->{
            mokinLisaysMetodi(mokkiStage);
        });
        Button poistoNappi = new Button("Poista valittu mökki");
        poistoNappi.setOnAction(e->{
            mokinPoisto();
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
    public void mokinPoisto(){ //TOIMII SQL:SSÄ
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
    protected void mokinEtsintaMetodi(Stage etsintaStage){ //TOIMII SQL:SSÄ
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
        //toiminnallisuus
        etsi.setOnAction(e->{
            List<String> kriteeriLista = new ArrayList<>();
            if (!nimiTF.getText().isEmpty()) {
                kriteeriLista.add("mokkinimi = \"" + nimiTF.getText() + "\"");
            }

            if (!alueTF.getText().isEmpty()) {
                kriteeriLista.add("alue_id = " + alueTF.getText());
            }

            if (!osoiteTF.getText().isEmpty()) {
                kriteeriLista.add("katuosoite = \"" + osoiteTF.getText() + "\"");
            }

            if (!minimiHinta.getText().isEmpty()) {
                kriteeriLista.add("hinta >= " + minimiHinta.getText());
            }

            if (!maksimiHinta.getText().isEmpty()) {
                kriteeriLista.add("hinta <= " + maksimiHinta.getText());
            }

            if (!henkilomaaraTF.getText().isEmpty()) {
                kriteeriLista.add("henkilomaara <= " + henkilomaaraTF.getText());
            }

            if (keittio.isSelected()) {
                kriteeriLista.add("varustelu LIKE '%Keittiö%'");
            }
            if (sauna.isSelected()){
                kriteeriLista.add("varustelu LIKE '%Sauna%'");
            }
            if (latu.isSelected()) {
                kriteeriLista.add("varustelu LIKE '%Hiihtolatu%'");
            }
            if (kuivain.isSelected()) {
                kriteeriLista.add("varustelu LIKE '%Hiustenkuivain%'");
            }
            String kriteerit = String.join(" AND ", kriteeriLista);
            mokkiMetodi(etsintaStage, main.connect.searchForStuff("mokki", kriteerit));
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
    public void mokinMuokkausMetodi(Stage muokkausStage){ //TOIMII SQL:SSÄ
        BorderPane BPMokinMuokkaukselle = new BorderPane();
        VBox paneeliMuokattavilleTiedoille = new VBox(10);
        Text muokattavaMokki = new Text("MUOKATTAVA MÖKKI\n" + mokki.SQLToString(valittuIndeksi));
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
            ArrayList<String> kriteeriLista = new ArrayList<>();
            if (!alueTF.getText().isEmpty())
                main.connect.updateTable("mokki", "alue_id", alueTF.getText(), ("mokki_id = " + valittuIndeksi));
            if (!postinroTF.getText().isEmpty())
                main.connect.updateTable("mokki", "postinro", postinroTF.getText(), ("mokki_id = " + valittuIndeksi));
            if (!nimiTF.getText().isEmpty())
                main.connect.updateTable("mokki", "mokkinimi", ("\"" + nimiTF.getText()) + "\"", ("mokki_id = " + valittuIndeksi));
            if (!osoiteTF.getText().isEmpty())
                main.connect.updateTable("mokki", "katuosoite", ("\"" + osoiteTF.getText()) + "\"", ("mokki_id = " + valittuIndeksi));
            if (!hintaTF.getText().isEmpty())
                main.connect.updateTable("mokki", "hinta", hintaTF.getText(), ("mokki_id = " + valittuIndeksi));
            if (!kuvausTF.getText().isEmpty())
                main.connect.updateTable("mokki", "kuvaus", ("\"" + kuvausTF.getText()) + "\"", ("mokki_id = " + valittuIndeksi));
            if (!henkilomaaraTF.getText().isEmpty())
                main.connect.updateTable("mokki", "henkilomaara", henkilomaaraTF.getText(), ("mokki_id = " + valittuIndeksi));
            if (keittio.isSelected()) {
                kriteeriLista.add("Keittiö");
            }
            if (sauna.isSelected()) {
                kriteeriLista.add("Sauna");
            }
            if (latu.isSelected()) {
                kriteeriLista.add("Hiihtolatu lähellä mökkiä");
            }
            if (kuivain.isSelected()) {
                kriteeriLista.add("Hiustenkuivain");
            }
            String kriteerit = ("\"" + String.join(", ", kriteeriLista) + "\"");
            main.connect.updateTable("mokki", "varustelu", kriteerit, ("mokki_id = " + valittuIndeksi));
            System.out.println("toimii ehkä?");
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