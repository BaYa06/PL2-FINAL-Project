package com.example;

import com.example.ui.MainFrame;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
public class Main {
    public static void main(String[] args) {
        try {
            // 1. Загрузка драйвера PostgreSQL
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер PostgreSQL загружен");

            // 2. Подключение к БД
            Properties props = new Properties();
            try (var inStream = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (inStream == null) {
                    throw new RuntimeException("Файл application.properties не найден!");
                }
                props.load(inStream);
                System.out.println("Свойства из application.properties загружены");
            }

            Connection conn = DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
            );
            System.out.println("Подключение к базе данных установлено");

            // 3. Запуск GUI
            SwingUtilities.invokeLater(() -> {
                MainFrame frame = new MainFrame(conn);
                frame.setVisible(true);
                System.out.println("MainFrame создан и отображен");
            });

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Ошибка запуска: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}