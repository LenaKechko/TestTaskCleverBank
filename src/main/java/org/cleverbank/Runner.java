package org.cleverbank;

import org.cleverbank.menu.MainMenu;
import org.cleverbank.operation.CashBack;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Runner {

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в Clever-Bank.\nВыберите операцию:");
        ScheduledExecutorService executor = null;
        ScheduledFuture<?> scheduledFuture = null;
        try {
            InputStream inputStream = new FileInputStream("config.yml");
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            executor = Executors.newScheduledThreadPool(1);
            scheduledFuture = executor.scheduleAtFixedRate(
                    new CashBack((Double) data.get("percent")),
                    2,
                    (Integer) data.get("periodOfSeconds"),
                    TimeUnit.SECONDS);
            MainMenu.start();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            scheduledFuture.cancel(true);
            executor.shutdown();
        }

    }
}