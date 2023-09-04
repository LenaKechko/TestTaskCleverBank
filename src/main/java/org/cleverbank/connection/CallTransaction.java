package org.cleverbank.connection;

import java.util.function.Supplier;

/**
 * Класс для выполнения операций в транзакции
 *
 * @author Кечко Елена
 */

public class CallTransaction {

    /**
     * Метод для работы с запросами типа select
     *
     * @param runnable      лямбда-выражение, с выполняемыми действиями во время транзакции
     * @param transactionDB объект транзакции для работы
     */
    public static void doSelect(Runnable runnable, TransactionDB transactionDB) {
        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            transactionDB.endTransaction();
        }
    }

    /**
     * Метод для работы с запросами типа select
     * и для передачи данных объекту параметризированного типа
     *
     * @param supplier лямбда-выражение, с выполняемыми действиями во время транзакции
     * @param transactionDB объект транзакции для работы
     * @return объект параметризированного типа T
     */
    public static <T> T doSelect(Supplier<T> supplier, TransactionDB transactionDB) {
        try {
            T result = supplier.get();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            transactionDB.endTransaction();
        }
        return null;
    }

    /**
     * Метод для выполнения транзакций с запросами на изменения данных в бд
     *
     * @param runnable      лямбда-выражение, с выполняемыми действиями во время транзакции
     * @param transactionDB объект транзакции для работы
     */

    public static void doTransaction(Runnable runnable, TransactionDB transactionDB) {
        try {
            runnable.run();
            transactionDB.commit();
        } catch (Exception e) {
            transactionDB.rollback();
            e.printStackTrace();
        } finally {
            transactionDB.endTransaction();
        }
    }

    /**
     * Метод для выполнения транзакций с запросами на изменения данных в бд
     * и для передачи данных объекту параметризированного типа
     *
     * @param supplier лямбда-выражение, с выполняемыми действиями во время транзакции
     * @param transactionDB объект транзакции для работы
     * @return объект параметризированного типа T
     */
    public static <T> T doTransaction(Supplier<T> supplier, TransactionDB transactionDB) {
        try {
            T result = supplier.get();
            transactionDB.commit();
            return result;
        } catch (Exception e) {
            transactionDB.rollback();
            e.printStackTrace();
        } finally {
            transactionDB.endTransaction();
        }
        return null;
    }
}
