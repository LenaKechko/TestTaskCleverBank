package org.cleverbank.writer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriterPDF implements IWriter {

    @Override
    public void createFile(String text, String fileName) {

        try {
            BaseFont baseFont = BaseFont.createFont(
                    "c:/windows/fonts/times.ttf",
                    "cp1251",
                    BaseFont.EMBEDDED
            );
            Font font = new Font(baseFont, 10, Font.NORMAL, BaseColor.BLACK);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();
            Paragraph paragraph = new Paragraph(text, font);
            document.add(paragraph);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
