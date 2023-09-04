package org.cleverbank.operation;

import org.cleverbank.connection.TransactionDB;
import org.cleverbank.entities.Account;
import org.cleverbank.entities.BankTransaction;
import org.cleverbank.entities.TypeTransaction;
import org.cleverbank.writer.IWriter;
import org.cleverbank.writer.Writer;
import org.cleverbank.writer.WriterTXT;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;

/**
 * Интерфейс для операций над счетом таких, как
 * пополнение, снятие и перевод денежных средств,
 * а также генерация чека о транзакции и
 * запись изменений в таблицы БД
 *
 * @author Кечко Елена
 */
public interface IOperationWithAccount {

    /**
     * Метод выполняющие операции с денежными средствами
     *
     * @param transactionDB   соединение для проведение транзакции
     * @param senderAccount   счет отправителя среств (если есть или null)
     * @param receiverAccount счет получателя среств (если есть или null)
     * @param money           сумма транзакции
     */
    TransactionDB startOperation(TransactionDB transactionDB, Account senderAccount, Account receiverAccount, BigDecimal money);

    /**
     * Метод для создания транзакции для дальнейшей ее записи в таблицу transactions БД
     *
     * @param senderAccount   счет отправителя среств (если есть или null)
     * @param receiverAccount счет получателя среств (если есть или null)
     * @param money           сумма транзакции
     * @param type            тип транзакции
     */
    default BankTransaction generateBankTransaction(Account senderAccount, Account receiverAccount, BigDecimal money, TypeTransaction type) {
        BankTransaction check = new BankTransaction();
        check.setTransactionDate(Date.from(Instant.now()));
        check.setMoney(money);
        check.setType(type);
        check.setAccountOfSender(senderAccount);
        check.setAccountOfReceiver(receiverAccount);
        return check;
    }

    /**
     * Метод для формирования шапки чека (для всех операций единый)
     *
     * @param bankTransaction данные о транзакции
     */
    static StringBuilder generateCheckHeader(BankTransaction bankTransaction) {
        StringBuilder bill = new StringBuilder("-------------------------------------------\n");
        bill.append("|            Банковский чек               |\n");
        bill.append(String.format("| Чек: %34d |\n", bankTransaction.getNumberCheck()));
        SimpleDateFormat formatterForDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatterForTime = new SimpleDateFormat("HH:mm:ss");
        bill.append(String.format("| %10s                     %8s |\n",
                formatterForDate.format(bankTransaction.getTransactionDate()),
                formatterForTime.format(bankTransaction.getTransactionDate())));
        bill.append(String.format("| Тип транзакции: %23s |\n", bankTransaction.getType().getName().getType()));
        return bill;
    }

    /**
     * Метод для формаирования чека индивидуально под операцию
     *
     * @param bankTransaction данные о транзакции
     */
    StringBuilder generateCheck(BankTransaction bankTransaction);

    /**
     * Метод для формаирования чека в формате TXT
     *
     * @param fileName имя файла
     * @param bill     текст чека
     */
    default void printCheck(StringBuilder bill, String fileName) {
        IWriter writerTXT = new WriterTXT();
        Writer writer = new Writer(writerTXT);
        writer.runWriter(bill, "check\\" + fileName);
    }
}
