package student.example.mokkivarausjarjestelmajava_ht;

public class Alue {
    protected int alue_id;
    protected String nimi;
    public String toString(){
        return ("Alue: " + nimi + " id: " + alue_id);
    }

    public Alue() {
    }

    public Alue(int alue_id, String nimi) {
        this.alue_id = alue_id;
        this.nimi = nimi;
    }
}
