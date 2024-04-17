package student.example.mokkivarausjarjestelmajava_ht;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;

public class Varaus {
    int varaus_id=0;
    int asiakas_id = 0;
    int mokki_id = 0;
    Date varattu_pvm = new Date();
    Date vahvistus_pvm = new Date();
    Date varattu_alkupvm = new Date();
    Date varattu_loppupvm = new Date();
    ArrayList<Varaus> varausLista = new ArrayList<>();

    public Varaus(int varaus_id, int asiakas_id, int mokki_id, Date varattu_pvm, Date vahvistus_pvm, Date varattu_alkupvm, Date varattu_loppupvm) {
        this.varaus_id = varaus_id;
        this.asiakas_id = asiakas_id;
        this.mokki_id = mokki_id;
        this.varattu_pvm = varattu_pvm;
        this.vahvistus_pvm = vahvistus_pvm;
        this.varattu_alkupvm = varattu_alkupvm;
        this.varattu_loppupvm = varattu_loppupvm;
    }
}
