package org.cleverbank.writer;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс, отвещающий за запись информации в TXT файл
 *
 * @author Кечко Елена
 */
public class WriterTXT implements IWriter {

    /**
     * Метод создающий файл с указанным именем
     * по определенному пути
     *
     * @param text     текст для записи
     * @param fileName имя или путь для записи файла
     * @throws FileNotFoundException в случае не верного пути
     * или не возможности записать файл
     */
    @Override
    public void createFile(StringBuilder text, String fileName) {
        try (FileWriter output = new FileWriter(fileName + ".txt", false)) {
            output.write(text.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
