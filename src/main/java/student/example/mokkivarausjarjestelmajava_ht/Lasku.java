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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Lasku extends Main {
    int lasku_id=0;
    int varaus_id=0;
    double summa = 0;
    double alv = 0.24;
    boolean maksettu = false;
    ArrayList<Lasku> olemassaOlevatLaskut = new ArrayList<>();
    public String toString(){
        return ("Lasku id: " + lasku_id + "\nvaraus id: " + varaus_id + "\nSumma: " + summa + "\nalv: " + alv + " % eli " + (summa*alv) + " €" + "\nmaksettu: " + maksettu);
    }
    public Lasku(){
        lasku_id=0;
        varaus_id=0;
        summa=0;
        alv=0.24;
        maksettu=false;
    }

    public Lasku(int lasku_id, int varaus_id, double summa, double alv) {
        this.lasku_id = lasku_id;
        this.varaus_id = varaus_id;
        this.summa = summa;
        this.alv = alv;
        this.maksettu = false;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage=laskutusMetodi(primaryStage);
        primaryStage.show();
    }

    protected void laskujenLuku(){
        //LUON DUMMY LASKUJA, NÄMÄ EIVÄT TULE LOPULLISEEN OHJELMAAN
        //VAAN KORVATAAN SQL:STÄ TULEVILLA LASKUILLA
        Lasku lasku1 = new Lasku(1, 1, 500, 0.24);
        Lasku lasku2 = new Lasku(2, 2, 400, 0.24);
        Lasku lasku3 = new Lasku(3, 3, 500, 0.24);

        olemassaOlevatLaskut.add(lasku1);
        olemassaOlevatLaskut.add(lasku2);
        olemassaOlevatLaskut.add(lasku3);
    }
    protected void laskunLisaaminen(Stage primaryStage){
        BorderPane BPlaskunLisaamiselle = new BorderPane();
        BPlaskunLisaamiselle.setPrefSize(500, 500);
        BPlaskunLisaamiselle.setPadding(new Insets(10, 10, 10, 10));
        VBox paneeliLaskunLisaamiselle = new VBox(15);
        Text varausid = new Text("Mihin varaukseen lasku liittyy?");
        TextField varausTF = new TextField();
        Text laskunSumma = new Text("Anna laskun summa");
        TextField summaTF = new TextField();
        Text laskunALV = new Text("Anna laskun ALV");
        TextField ALVTF = new TextField();
        Button lisaaLasku = new Button("Lisää lasku");
        lisaaLasku.setOnAction(e->{
            int laskunNumero = 1;
            for (int i = 0; i<olemassaOlevatLaskut.size(); i++) {
                laskunNumero++;
                i++;
            }
            int varauksenNumero = Integer.parseInt(varausTF.getText());
            double varauksenSumma = Double.parseDouble(summaTF.getText());
            double varauksenALV = Double.parseDouble(ALVTF.getText());
            olemassaOlevatLaskut.add(new Lasku(laskunNumero, varauksenNumero, varauksenSumma, varauksenALV));
            System.out.println(olemassaOlevatLaskut);
        });
        paneeliLaskunLisaamiselle.getChildren().addAll(varausid, varausTF, laskunSumma, summaTF, laskunALV, ALVTF, lisaaLasku);
        paneeliLaskunLisaamiselle.setAlignment(Pos.CENTER);
        BPlaskunLisaamiselle.setLeft(kotiNappain());
        BPlaskunLisaamiselle.setCenter(paneeliLaskunLisaamiselle);
        Scene scene = new Scene(BPlaskunLisaamiselle);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Laskun lisääminen");
        primaryStage.show();
    }


    protected Stage laskutusMetodi(Stage laskuStage){
        laskujenLuku();
        BorderPane paneeliLaskutukselle = new BorderPane();
        TextArea alueLaskujenTiedoille = new TextArea();
        alueLaskujenTiedoille.setText("Klikkaa laskua nähdäksesi sen tarkemmat tiedot :)");
        alueLaskujenTiedoille.setEditable(false);
        ObservableList<Lasku> luettavaLaskuLista = FXCollections.observableArrayList(olemassaOlevatLaskut);
        ArrayList<Integer> laskuNumeroLista = new ArrayList<>();
        for (int i=0; i<3; i++){
            laskuNumeroLista.add(luettavaLaskuLista.get(i).lasku_id);
        }
        ListView<Integer> laskuLista = new ListView<>();
        laskuLista.setItems(FXCollections.observableArrayList(laskuNumeroLista));

        laskuLista.getSelectionModel().selectedItemProperty().addListener(ov->{
            alueLaskujenTiedoille.setText(
                    luettavaLaskuLista.get(laskuLista.getSelectionModel().getSelectedIndex()).toString());
        });
        Button koti = kotiNappain();
        Button lisays = new Button("Lisää lasku");
        lisays.setOnAction(e->{
            System.out.println("nappi toimii :)");
            laskunLisaaminen(laskuStage);
        });
        paneeliLaskutukselle.setBottom(lisays);
        paneeliLaskutukselle.setLeft(koti);
        paneeliLaskutukselle.setCenter(laskuLista);
        paneeliLaskutukselle.setRight(alueLaskujenTiedoille);
        Scene sceneLaskuille = new Scene(paneeliLaskutukselle);
        laskuStage.setTitle("Laskut");
        laskuStage.setScene(sceneLaskuille);
        return laskuStage;
    }
}
