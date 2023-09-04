package org.cleverbank.operation;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.dao.BankTransactionDAO;
import org.cleverbank.entities.Account;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * Класс для формирования выписки о
 * потраченных и полученных денежных средствах
 * в определенный промежуток времени
 *
 * @author Кечко Елена
 */
public class MoneyStatement implements IStatement {

    /**
     * Поле для подтраченных денежных средств
     */
    private BigDecimal leavingMoney;
    /**
     * Поле для полученных денежных средств
     */
    private BigDecimal comingMoney;

    /**
     * Метод для формирования выписки с предварительным
     * расчетом потраченных и полученных денежных средствах
     *
     * @param account    объект счета
     * @param startDate  начало временного промежутка
     * @param finishDate окончание временного промежутка
     * @return текст выписки
     */
    @Override
    public StringBuilder generateStatement(Account account, LocalDate startDate, LocalDate finishDate) {
        StringBuilder bill = IStatement.generateStatementHeader(account, startDate, finishDate);
        bill.append("            Приход           |          Расход         \n");
        bill.append("-------------------------------------------------------\n");

        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.initTransaction(bankTransactionDAO);

        CallTransaction.doSelect(() -> {
            comingMoney = bankTransactionDAO.findTotalSumComingMoney(account, startDate, finishDate);
            leavingMoney = bankTransactionDAO.findTotalSumLeavingMoney(account, startDate, finishDate);
        }, transactionDB);

        bill.append(String.format(" %20.2f %s    |    %17.2f %s\n",
                comingMoney, account.getCurrency().getName(),
                leavingMoney, account.getCurrency().getName()));
        return bill;
    }
}
