package org.cleverbank.operation;

import org.cleverbank.connection.CallTransaction;
import org.cleverbank.connection.TransactionDB;
import org.cleverbank.dao.BankTransactionDAO;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Класс для формирования развернутой выписки
 * по всем проведенным операциям
 * в определенный промежуток времени
 *
 * @author Кечко Елена
 */
public class AccountStatement implements IStatement {

    /**
     * Метод для формирования выписки
     * за конкретный промежуток времени
     *
     * @param account    объект счета
     * @param startDate  начало временного промежутка
     * @param finishDate окончание временного промежутка
     * @return текст выписки
     */
    @Override
    public StringBuilder generateStatement(Account account,
                                           LocalDate startDate, LocalDate finishDate) {
        StringBuilder bill = IStatement.generateStatementHeader(account, startDate, finishDate);
        BankTransactionDAO bankTransactionDAO = new BankTransactionDAO();
        TransactionDB transactionDB = new TransactionDB();
        transactionDB.initTransaction(bankTransactionDAO);
        final List<BankTransaction> bankTransactions =
                CallTransaction.<List<BankTransaction>>doSelect(() ->
                        bankTransactionDAO.findEntityByDate(startDate, finishDate, account), transactionDB);

        bill.append("  Дата     |      Примечание                         |   Сумма         \n");
        bill.append("-----------------------------------------------------------------------\n");
        for (BankTransaction bankTransaction : bankTransactions) {
            String description = "";
            BigDecimal money = bankTransaction.getMoney();
            switch (bankTransaction.getType().getName()) {
                case TRANSFER -> {
                    description = "Пополнение";
                    if (bankTransaction.getAccountOfSender().getId() == account.getId()) {
                        money = money.subtract(money.multiply(BigDecimal.valueOf(2)));
                        description += " счета " +
                                bankTransaction.getAccountOfReceiver().getUser().getLastName();
                    } else {
                        description += " от " + bankTransaction.getAccountOfSender().getUser().getLastName();
                    }
                }
                case WITHDRAWAL -> {
                    description = "Снятие средств";
                    money = money.multiply(BigDecimal.valueOf(-1));
                }
                case REPLENISHMENT -> description = "Пополнение";
            }
            bill.append(String.format("%s | %-40s| %.2f %s\n",
                    formatDate.format(bankTransaction.getTransactionDate()), description, money,
                    account.getCurrency().getName()));
        }
        return bill;
    }
}
