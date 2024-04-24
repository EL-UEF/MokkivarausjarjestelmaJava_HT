package student.example.mokkivarausjarjestelmajava_ht;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Varaus {
    private Main main;
    int varaus_id=0;
    int asiakas_id = 0;
    int mokki_id = 0;
    /**
     * Käytän formaattina LocalDateTime, jonka pitäisi olla SQL yhteensopiva
     */
    LocalDateTime varattu_pvm = LocalDateTime.now();
    LocalDateTime vahvistus_pvm = LocalDateTime.now();
    LocalDateTime varattu_alkupvm = null;
    LocalDateTime varattu_loppupvm = null;
    public String SQLToString(int indeksi){
        String query = ("SELECT * FROM varaus WHERE varaus_id = " + indeksi);
        int SQLasiakas = -1;
        int SQLmokki = -1;
        LocalDateTime SQLvarattupvm = null;
        LocalDateTime SQLvahvistuspvm = null;
        LocalDateTime SQLalkupvm = null;
        LocalDateTime SQLloppupvm = null;
        try {
            ResultSet rs = main.connect.executeQuery(query);
            rs.next();
            SQLasiakas = rs.getInt("asiakas_id");
            SQLmokki = rs.getInt("mokki_id");
            SQLvarattupvm = rs.getObject("varattu_pvm", LocalDateTime.class);
            SQLvahvistuspvm = rs.getObject("vahvistus_pvm", LocalDateTime.class);
            SQLalkupvm = rs.getObject("varattu_alkupvm", LocalDateTime.class);
            SQLloppupvm = rs.getObject("varattu_loppupvm", LocalDateTime.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String kokoTeksti = ("Varaus id: " + indeksi + "\nAsiakas id: " + SQLasiakas + "\nMökki id: " + SQLmokki +
                "\nVarausaika: " + SQLvarattupvm + "\nVahvistusaika: " + SQLvahvistuspvm + "\nVarauksen alku: " +
                SQLalkupvm + "\nVarauksen loppu: " + SQLloppupvm);
        return kokoTeksti;
    }

    public Varaus(Main main) {
        this.main=main;
    }
}
