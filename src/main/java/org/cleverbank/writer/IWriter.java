package org.cleverbank.writer;

/**
 * Интерфейс для определения структуры классов,
 * работающих с вывод данных в файлы различных форматов
 *
 * @author Кечко Елена
 */
public interface IWriter {
    /**
     * Метод для создания файла
     *
     * @param text отвечает за сохраняемый текст в файле
     * @param fileName имя файла и путь (если необходимо)
     */
    void createFile(StringBuilder text, String fileName);
}
