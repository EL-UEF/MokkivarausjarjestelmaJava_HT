package student.example.mokkivarausjarjestelmajava_ht;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

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
    // Method to create a PDF that says "Hello World"
    public void createHelloWorldPDF(String outputPath) {
        Document document = new Document(PageSize.A4);
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(outputPath);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);

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

    public static void main(String[] args) {
        // Path to output PDF
        String outputFilePath = "HelloWorld.pdf";

        // Create instance of BillPDFer and generate the PDF
        BillPDFer pdfGenerator = new BillPDFer();
        pdfGenerator.createHelloWorldPDF(outputFilePath);
    }
}
