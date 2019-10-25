package wct.fileprocessing;

import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author xuanlinhha
 */
public class PDFEditor {

    static void changeInfo(File file, String s) throws IOException {
        PDDocument document = PDDocument.load(file);
        document.getDocumentInformation().setSubject(s);
//        PDPage newPage = new PDPage();
//        PDPageContentStream contentStream = new PDPageContentStream(document, newPage);
//        contentStream.beginText();
//        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
//        contentStream.newLineAtOffset(25, 500);
//        contentStream.showText(s);
//        contentStream.endText();
//        contentStream.close();
//        document.addPage(newPage);
        document.save(file);
    }
}
