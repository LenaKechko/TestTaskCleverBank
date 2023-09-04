package org.cleverbank.writer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Класс, отвещающий за запись информации в PDF файл
 *
 * @author Кечко Елена
 */

public class WriterPDF implements IWriter {

    /**
     * Метод создающий файл с указанным именем
     * по определенному пути
     *
     * @param text     текст для записи
     * @param fileName имя или путь для записи файла
     * @throws RuntimeException в случае не верного пути
     * или не возможности записать файл
     */
    @Override
    public void createFile(StringBuilder text, String fileName) {

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
            Paragraph paragraph = new Paragraph(text.toString(), font);
            document.add(paragraph);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
