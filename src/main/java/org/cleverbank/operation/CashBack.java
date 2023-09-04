package org.cleverbank.operation;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.dao.AccountDAO;
import org.cleverbank.entities.Account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Класс для выполнения процедуры cashBack:
 * начисление в конце месяца заданного процента
 * от остатка на счет пользователя
 *
 * @author Кечко Елена
 */
public class CashBack implements Runnable {
    /**
     * Поле для установленного процента
     */
    private final double percent;
    /**
     * Поле для проверки была ли уже
     * произведена операция в конце месяца или нет
     */
    private static boolean isOperationDone = false;

    /**
     * Конструктор класса для занесения процента
     *
     * @param percent процент
     */

    public CashBack(double percent) {
        this.percent = percent;
    }

    /**
     * Метод для запуска операции. Проверяет день
     * и запускает начисление процентов
     * */
    @Override
    public synchronized void run() {
        LocalDate dateNow = LocalDate.now();
        LocalDate dateEndOfMonth = dateNow.withDayOfMonth(dateNow.lengthOfMonth());
        if (!isOperationDone && dateNow.equals(dateEndOfMonth)) {
            isOperationDone = getCashBank(percent);
        }
        if (isOperationDone && !dateNow.equals(dateEndOfMonth)) {
            isOperationDone = false;
        }
    }

    /** Метод для вычисления cash back
     * и изменения данных в БД
     *
     * @param percent процент процедуры
     * @return true/false информация об успешности выполнения
     * */
    public static boolean getCashBank(double percent) {
        AccountDAO accountDAO = new AccountDAO();
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.initTransaction(accountDAO);
        CallTransaction.doTransaction(() -> {
            List<Account> accounts = accountDAO.findAll();
            accounts.stream()
                    .map(account -> {
                        BigDecimal v = account.getRemainder().multiply(
                                BigDecimal.valueOf((1 + percent / 100.0)));
                        account.setRemainder(v);
                        return account;
                    })
                    .forEach(accountDAO::updateRemainder);
        }, transactionDB);
        return true;
    }
}
