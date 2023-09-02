package org.cleverbank;

import org.cleverbank.menu.MainMenu;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Timer;

public class Runner {

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в Clever-Bank.\nВыберите операцию:");
        try {
            InputStream inputStream = new FileInputStream("config.yml");
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            Timer timer = new Timer();
            timer.schedule(new TimerCashBack((Double) data.get("percent")),
                    100, (Integer) data.get("period"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        MainMenu.start();
    }
}