package org.cleverbank.writer;

public class Writer {
    private final IWriter writer;

    public Writer (IWriter writer) {
        this.writer = writer;
    }

    public void runWriter(StringBuilder text, String fileName) {
        writer.createFile(text, fileName);
    }
}
