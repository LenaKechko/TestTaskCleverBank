package org.cleverbank;

import org.cleverbank.menu.MainMenu;
import org.cleverbank.operation.CashBack;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class Runner {

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в Clever-Bank.\nВыберите операцию:");
//        try {
//            InputStream inputStream = new FileInputStream("config.yml");
//            Yaml yaml = new Yaml();
//            Map<String, Object> data = yaml.load(inputStream);
//            System.out.println(data.get("periodOfTime"));
//            boolean checkOperation = false;
//            if (!checkOperation) {
//                checkOperation = CashBack.getCashBank((Double) data.get("percent"));
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//        }

        MainMenu.start();
    }
}