package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class ImportJson {

    public static void importData(JFrame parentFrame) {
        String url = "jdbc:postgresql://localhost:5432/shop";
        String user = "admin";
        String password = "admin";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите JSON файл для импорта");

        int userSelection = fileChooser.showOpenDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Файл для импорта: " + selectedFile.getAbsolutePath());

            ObjectMapper mapper = new ObjectMapper();

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                List<Map<String, Object>> data = mapper.readValue(
                        selectedFile,
                        new TypeReference<List<Map<String, Object>>>() {}
                );

                if (data.isEmpty()) {
                    System.out.println("Файл пустой.");
                    return;
                }

                String table = "products";
                Map<String, Object> firstRow = data.get(0);
                String columns = String.join(", ", firstRow.keySet());
                String placeholders = String.join(", ", firstRow.keySet().stream().map(k -> "?").toArray(String[]::new));

                String sql = "INSERT INTO " + table + " (" + columns + ") VALUES (" + placeholders + ")";
                System.out.println("SQL: " + sql);

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    for (Map<String, Object> row : data) {
                        int index = 1;
                        for (String col : firstRow.keySet()) {
                            stmt.setObject(index++, row.get(col));
                        }
                        stmt.addBatch();
                    }

                    stmt.executeBatch();
                    JOptionPane.showMessageDialog(parentFrame, "Data was succesfully imported!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(parentFrame, "Ошибка при импорте: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            System.out.println("Импорт отменён.");
        }
    }
}
