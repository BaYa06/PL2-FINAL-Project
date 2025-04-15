package com.example;
import java.sql.*;
import java.io.File;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.io.IOException;

public class JsonExporter {
    public static void exportData() {
        String url = "jdbc:postgresql://localhost:5432/shop";
        String user = "admin";
        String password = "admin";
        String query = "SELECT * FROM products";

        // Открытие диалога для выбора папки
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select folder to save JSON");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            File fileToSave = new File(selectedFolder, "Products.json");

            System.out.println("File will be saved to: " + fileToSave.getAbsolutePath());

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                List<Map<String, Object>> rows = new ArrayList<>();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i);
                        if (!columnName.equalsIgnoreCase("id")) { // исключаем id
                            row.put(columnName, rs.getObject(i));
                        }
                    }
                    rows.add(row);
                }

                // Запись в файл
                ObjectMapper mapper = new ObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(fileToSave, rows);

                JOptionPane.showMessageDialog(null, "Файл успешно сохранен в " + fileToSave.getAbsolutePath(), "Успех", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("JSON с данными (без id) был успешно создан!");

            } catch (SQLException | IOException e) {
                JOptionPane.showMessageDialog(null, "Ошибка при сохранении данных: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            System.out.println("Сохранение файла отменено");
        }
    }
}
