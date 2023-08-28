package org.cleverbank;

import org.cleverbank.Menu.MainMenu;

public class Main {

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в приложение Clever-Bank.\nВыберите операцию:");
        MainMenu.start();
//        System.out.println("Testing connection");
//        UserDAO userDAO = new UserDAO();
//        System.out.println(userDAO.findAll());
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            return;
//        }
//        System.out.println("Connection successful");
//        Connection connection = null;
//        try {
//            connection = ConnectorDB.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        if (connection != null) {
//            System.out.println("Connection with database");
//            try {
//                Statement statement = connection.createStatement();
//                ResultSet rs = statement.executeQuery("select * from banks");
//                while (rs.next()) {
//                    System.out.println(rs.getInt("id"));
//                    System.out.println(rs.getString("name"));
//
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//        } else {
//            System.out.println("Fail connection");
//        }

    }
}