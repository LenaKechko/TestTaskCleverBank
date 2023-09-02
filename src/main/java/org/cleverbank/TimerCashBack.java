package org.cleverbank;

import org.cleverbank.operation.CashBack;

import java.time.LocalDate;
import java.util.TimerTask;

public class TimerCashBack extends TimerTask {
    private final double percent;
    private static boolean isOperationDone = false;

    public TimerCashBack(double percent) {
        this.percent = percent;
    }

    @Override
    public synchronized void run() {
        LocalDate dateNow = LocalDate.now();
        LocalDate dateEndOfMonth = dateNow.withDayOfMonth(dateNow.lengthOfMonth());
        if (!isOperationDone && dateNow.equals(dateEndOfMonth)) {
            isOperationDone = CashBack.getCashBank(percent);
            System.out.println("Work Done");
        }
        if (isOperationDone && !dateNow.equals(dateEndOfMonth)) {
            isOperationDone = false;
        }
    }
}
