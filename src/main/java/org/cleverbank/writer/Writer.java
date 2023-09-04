package org.cleverbank.writer;

/**
 * Класс запускающий конктерную реализацию интерфейса IWriter на работу
 *
 * @author Кечко Елена
 */
public class Writer {
    /**
     * Поле типа интерфейса
     */
    private final IWriter writer;

    /**
     * Конструктор иниацилизующий поле класса
     *
     * @param writer объект типа реализующий интерфейс IWriter
     */
    public Writer(IWriter writer) {
        this.writer = writer;
    }

    /**
     * Метод запускающий запись в файлы различного типа
     *
     * @param text текст для записи
     * @param fileName имя или путь для записи файла
     */
    public void runWriter(StringBuilder text, String fileName) {
        writer.createFile(text, fileName);
    }
}
