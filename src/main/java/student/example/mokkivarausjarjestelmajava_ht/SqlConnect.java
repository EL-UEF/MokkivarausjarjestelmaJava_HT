package student.example.mokkivarausjarjestelmajava_ht;

import java.sql.*;


/**
 * TARVITSEE JCDB AJURIN EXTERNAL LIBRARYYN!!!!
 * 1. Lataa täältä ajuri: https://dev.mysql.com/downloads/connector/j/ (Platfrom independent)
 * 2. Pura ajuri sellaiseen paikkaan mistä löydät
 * 3. Mene ylhäältä intelliJ File -> project structure... -> project settings -> libraries
 * 4. Paina pientä + symbolia ( tai Alt + insert) valitse siitä syntyvästä valikosta "Java"
 * 5. Etsi purkamasi tiedostoa ja valitse mysql-connector-j.jar tiedosto ja klikkaa siitä
 * 6. Nyt pitäis toimia : )
 *
 */
public class SqlConnect {
    private String user;
    private String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    /**
     *
     * @param query sql kieltä, mieluiten SELECT jotta ei ruveta rikkomaan tietokantaa : )
     * @return palauttaa Resultset, eli se pitää sen jälkeen rikkoa rs.getString(int colum) metodilla,
     * int colum tarkoittaa mitä kohtaa taulukosta haluaa ulos
     * ---------------------------------------------------------------------------------------------------
     * Tuo on tällä hetkellä pistetty mulla olevaan random traffic tietokantaan, pitää lisätä dataa
     * vn tietokantaa että pääsee testaamaan, pyritään pitämään portti, salasana ja user samana kaikilla.
     */
    public ResultSet createConnection(String query) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/traffic", this.user, this.password);
            this.stmt = con.createStatement();
            this.rs = stmt.executeQuery(query);
            while(rs.next()) return rs;
            this.con.close();
        } catch (ClassNotFoundException | SQLException e) {throw new RuntimeException(e);
        }
        System.out.println("Connection succesfull!");
        try {
            this.con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    /**
     *
     * @param user Käyttäjä, jos haluaa pelleillä niiden kanssa niin eiku vaan, default "root"
     * @param password :I mulla default "1234", pitää olla Stringinä
     */
    public  SqlConnect(String user, String password){
        this.user = user;
        this.password = password;
    }
}