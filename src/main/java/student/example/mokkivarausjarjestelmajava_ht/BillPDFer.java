package student.example.mokkivarausjarjestelmajava_ht;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
HIEMAN SCUFFED TAPA SAADA ITEXT
MENE OSOITTEESEEN https://bit.ly/3v5ZumI JA SINUN PITÄISI LADATA JAR TIEDOSTO ITEXTIÄ VARTEN
LAITA TIEDOSTO PAIKKAAN, JOSTA LÖYDÄT SEN
INTELLIJ:SSÄ
FILE->PROJECT STRUCTURE->LIBRARIES->+ NAPPI->JAVA->ITEXT JAR FILE JONKA LATASIT
JA ADD TO PROJECT

TÄMÄN JÄLKEEN TÄMÄN LUOKAN PITÄISI TEHDÄ PDF JOKA SANOO "HELLO WORLD"
 */

public class BillPDFer {
    Main main;
    public void createBillPDF(String lasku_id){
        String laskuString = luoPdfTeksti(lasku_id);
        Document lasku = new Document();
        FileOutputStream fileOutputStream = null;
        PdfWriter pdfWriter = null;

        try {
            // Creating FileOutputStream for output file
            fileOutputStream = new FileOutputStream("lasku.pdf");

            // Creating PdfWriter that links the Document to the FileOutputStream
            pdfWriter = PdfWriter.getInstance(lasku, fileOutputStream);

            // Opening the Document
            lasku.open();

            // Adding content to the Document
            lasku.add(new Paragraph(laskuString));

            System.out.println("PDF created successfully at: lasku.pdf");

        } catch (FileNotFoundException | DocumentException e) {
            throw new RuntimeException("Failed to create PDF: ", e);
        } finally {
            // Ensure Document is closed and resources are released
            if (lasku.isOpen()) {
                lasku.close();
            }
            if (pdfWriter != null) {
                pdfWriter.close();
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    System.err.println("Error closing FileOutputStream: " + e.getMessage());
                }
            }
        }
    }

    private String luoPdfTeksti(String lasku_id) {
        int laskuID = -1;
        int varausID = -1;
        int asiakasID = -1;
        String enimi;
        String snimi;
        String mokkinimi;
        LocalDateTime SQLalkupvm = null;
        LocalDateTime SQLloppupvm = null;
        Double palveluidenHinta = -1.0;
        Double mokinHinta = -1.0;
        Double kokonaisHinta = -1.0;
        int maksettu = -1;
        //Haetaan laskuun laitettavat tiedot:
        try {
            ResultSet rs = main.connect.searchForStuff("laskutustiedot", ("lasku_id = " + lasku_id));
            rs.next();
            laskuID = rs.getInt("lasku_id");
            varausID = rs.getInt("varaus_id");
            asiakasID = rs.getInt("asiakas_id");
            enimi = rs.getString("etunimi");
            snimi = rs.getString("sukunimi");
            mokkinimi = rs.getString("mokkinimi");
            SQLalkupvm = rs.getObject("alkupaiva", LocalDateTime.class);
            SQLloppupvm = rs.getObject("loppupaiva", LocalDateTime.class);
            palveluidenHinta = rs.getDouble("käytettyjen_palveluiden_hinta");
            mokinHinta = rs.getDouble("mökin hinta");
            kokonaisHinta = rs.getDouble("yht");
            maksettu = rs.getInt("maksettu");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String formatoituAlkupvm = SQLalkupvm.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String formatoituloppupvm = SQLloppupvm.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        String laskuString = ("Lasku id: " + laskuID + "\nvaraus id: " + varausID + "\nAsiakas_id: " + asiakasID + "\nAsiakkaan nimi: " + enimi + " " + snimi +
                "\nvuokrattu mökki: " + mokkinimi + "\nVuokrausaika: " + formatoituAlkupvm + " - " + formatoituloppupvm + "\nLisäpalveluiden hinta: " +
                palveluidenHinta + "\nMökin hinta: " + mokinHinta + "\nYht: " + kokonaisHinta);
        return laskuString;
    }

    public void createHelloWorldPDF(String outputPath) {
        Document document = new Document(PageSize.A4);
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(outputPath);

            document.open();
            document.add(new Paragraph("Hello World"));

            System.out.println("PDF created successfully at: " + outputPath);

        } catch (IOException e) {
            System.err.println("Error creating PDF: " + e.getMessage());
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.err.println("Error closing FileOutputStream: " + e.getMessage());
                }
            }
        }
    }

    public BillPDFer(Main main) {
        this.main = main;
    }
}
