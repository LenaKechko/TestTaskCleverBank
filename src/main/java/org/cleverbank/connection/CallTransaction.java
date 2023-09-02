package org.cleverbank.connection;

public class CallTransaction {
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
}
