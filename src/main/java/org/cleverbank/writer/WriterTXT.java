package org.cleverbank.writer;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class WriterTXT implements IWriter {

    @Override
    public void createFile(String text, String fileName) {
        try (FileWriter output = new FileWriter(fileName + ".txt", false)) {
            output.write(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
