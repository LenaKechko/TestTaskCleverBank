package org.cleverbank.connection;

import java.util.function.Supplier;

public class CallTransaction {

    public static void doSelect(Runnable runnable, TransactionDB transactionDB) {
        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            transactionDB.endTransaction();
        }
    }

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
