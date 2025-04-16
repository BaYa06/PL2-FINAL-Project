package com.example.ui;

import com.example.JsonExporter;
import com.example.dao.ProductDaoImpl;
import com.example.model.Product;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.List;

import com.example.ImportJson;

public class MainFrame extends JFrame {
    private ProductDaoImpl productDao;
    private JPanel bottomPanel;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private int selectedProductId = -1;

    public MainFrame(Connection conn) {
        setTitle("Product Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);

        // Горячая клавиша Ctrl + E
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_E, 
            System.getProperty("os.name").toLowerCase().contains("mac") ? KeyEvent.META_DOWN_MASK : KeyEvent.CTRL_DOWN_MASK);

        KeyStroke keyStroke2 = KeyStroke.getKeyStroke(KeyEvent.VK_I, 
            System.getProperty("os.name").toLowerCase().contains("mac") ? KeyEvent.META_DOWN_MASK : KeyEvent.CTRL_DOWN_MASK);


        // Получаем input и action map для root панели
        JRootPane rootPane = this.getRootPane();
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();

        // Привязываем клавишу к действию "export"
        inputMap.put(keyStroke, "export");
        actionMap.put("export", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(">>> Ctrl+E");
                // Здесь вызываем твой метод
                JsonExporter.exportData();
            }
        });

        inputMap.put(keyStroke2, "import");
        actionMap.put("import", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(">>> Ctrl+I");
                // Здесь вызываем твой метод
                ImportJson.importData(MainFrame.this);
                refreshBottomPanel();
            }
        });

        

        System.out.println("MainFrame конструктор: Начало инициализации");
        if (conn == null) {
            System.out.println("MainFrame: Connection is null!");
            JOptionPane.showMessageDialog(null, "Нет подключения к базе данных!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                productDao = new ProductDaoImpl(conn);
                System.out.println("MainFrame: ProductDaoImpl успешно инициализирован");
            } catch (Exception e) {
                System.out.println("MainFrame: Ошибка при создании ProductDaoImpl: " + e.getMessage());
                e.printStackTrace();
            }
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(251, 175, 22), 3), "Add new Item"));

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        fieldPanel.setAlignmentY(TOP_ALIGNMENT);

        JPanel nameRow = createFieldRow("Name", 250, 40);
        nameField = (JTextField) nameRow.getComponent(2);
        fieldPanel.add(nameRow);

        JPanel priceRow = createFieldRow("Price", 250, 40);
        priceField = (JTextField) priceRow.getComponent(2);
        fieldPanel.add(priceRow);

        JPanel quantityRow = createFieldRow("Quantity", 250, 40);
        quantityField = (JTextField) quantityRow.getComponent(2);
        fieldPanel.add(quantityRow);

        topPanel.add(fieldPanel);
        topPanel.add(Box.createHorizontalStrut(20));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        Dimension buttonSize = new Dimension(120, 30);
        JButton addBut = createStyledButton("Add", buttonSize, new Color(251, 175, 22));
        JButton updateBut = createStyledButton("Update", buttonSize, new Color(251, 175, 22));
        JButton deleteBut = createStyledButton("Delete", buttonSize, new Color(251, 175, 22));
        JButton clsBut = createStyledButton("Clear", buttonSize, new Color(251, 159, 22));
        JButton countBut = createStyledButton("Count", buttonSize, new Color(251, 175, 22));

        addBut.addActionListener(e -> {
            System.out.println("Нажата кнопка Add");
            if (productDao == null) {
                System.out.println("MainFrame: productDao is null в обработчике Add!");
                JOptionPane.showMessageDialog(this, "DAO не инициализирован!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String name = nameField.getText();
                String check = name.replace(" ", "");
                if (check.equals("")) {
                    JOptionPane.showMessageDialog(this, "Name field is empty", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int price = Integer.parseInt(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                if (price <= 0 || quantity <= 0 ) {
                    JOptionPane.showMessageDialog(this, "Price and Quantity should be bigger then 0", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Product product = new Product(0, name, price, quantity);
                productDao.addProduct(product);
                refreshBottomPanel();

                nameField.setText("");
                priceField.setText("");
                quantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price and Quantity should be INTEGER", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        countBut.addActionListener(e -> {

            List<Product> products = productDao.getAllProducts();
            /* 
                int count = 0;
                for (int i = 0; i < products.size(); i++) {
                    count += products.get(i).getQuantity();
                }
            */
            long count = products.stream().mapToInt(p -> p.getQuantity()).sum();

            JOptionPane.showMessageDialog(this, "Total quantity: " + count, "Info", JOptionPane.INFORMATION_MESSAGE);
        });


        updateBut.addActionListener(e -> {
            if (productDao == null) {
                System.out.println("MainFrame: productDao is null в обработчике Add!");
                JOptionPane.showMessageDialog(this, "DAO is not working!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedProductId == -1) {
                JOptionPane.showMessageDialog(this, "Please select the product!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String name = nameField.getText();
                String check = name.replace(" ", "");
                if (check.equals("")) {
                    JOptionPane.showMessageDialog(this, "Name field is empty", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int price = Integer.parseInt(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                if (price <= 0 || quantity <= 0 ) {
                    JOptionPane.showMessageDialog(this, "Price and Quantity should be bigger then 0", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Product product = new Product(0, name, price, quantity);
                productDao.updateProduct(selectedProductId, product);
                refreshBottomPanel();

                nameField.setText("");
                priceField.setText("");
                quantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price and Quantity should be INTEGER!", "Error",  JOptionPane.ERROR_MESSAGE);
            }
        }); 

        deleteBut.addActionListener(e -> {
            if (productDao == null) {
                System.out.println("MainFrame: productDao is null в обработчике Add!");
                JOptionPane.showMessageDialog(this, "DAO не инициализирован!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedProductId == -1) {
                JOptionPane.showMessageDialog(this, "Choose product to delete!", "Eror", JOptionPane.WARNING_MESSAGE);
                return;
            }
            productDao.deleteProduct(selectedProductId);
            refreshBottomPanel();
            selectedProductId = -1;

            nameField.setText("");
            priceField.setText("");
            quantityField.setText("");
        });

        clsBut.addActionListener(e -> {
            nameField.setText("");
            priceField.setText("");
            quantityField.setText("");

            Component[] components = bottomPanel.getComponents();
            for (Component comp : components) {
                if (comp instanceof JPanel && !comp.getBackground().equals(Color.WHITE)) {
                    comp.setBackground(new Color(251, 175, 22)); // Сбрасываем только не белые
                }
            }

            selectedProductId = -1;
        });

        buttonsPanel.add(Box.createVerticalGlue());
        buttonsPanel.add(addBut);
        buttonsPanel.add(Box.createVerticalStrut(11));
        buttonsPanel.add(updateBut);
        buttonsPanel.add(Box.createVerticalStrut(11));
        buttonsPanel.add(deleteBut);
        buttonsPanel.add(Box.createVerticalStrut(11));
        buttonsPanel.add(clsBut);
        buttonsPanel.add(Box.createVerticalStrut(11));
        buttonsPanel.add(countBut);
        buttonsPanel.add(Box.createVerticalStrut(5));
        buttonsPanel.add(Box.createVerticalGlue());

        topPanel.add(buttonsPanel);
        topPanel.add(Box.createHorizontalStrut(20));

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.WHITE);

        if (productDao != null) {
            refreshBottomPanel();
        } else {
            System.out.println("MainFrame: Не удалось обновить bottomPanel, productDao is null");
        }

        JScrollPane scrollPane = new JScrollPane(bottomPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(251, 175, 22), 3), "All items"));
        scrollPane.setPreferredSize(new Dimension(400, 200));

        panel.add(topPanel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(scrollPane);
        panel.add(Box.createVerticalGlue());

        add(panel);
        pack();
        setMinimumSize(new Dimension(400, 300));
        setVisible(true);
        System.out.println("MainFrame: Конструктор завершён");
    }

    private void refreshBottomPanel() {
        bottomPanel.removeAll();
        bottomPanel.add(allItemsPanel("ID", "NAME", "PRICE", "QUANTITY", Color.WHITE)); // Заголовок
        for (Product product : productDao.getAllProducts()) {
            JPanel productPanel = allItemsPanel(
                String.valueOf(product.getId()),
                product.getName(),
                String.valueOf(product.getPrice()),
                String.valueOf(product.getQuantity()),
                new Color(251, 175, 22)
            );
    
            // Select product
            productPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedProductId = product.getId();
                    System.out.println("Selected product with id: " + selectedProductId);
    
                    Component[] components = bottomPanel.getComponents();
                    for (Component comp : components) {
                        if (comp instanceof JPanel && !comp.getBackground().equals(Color.WHITE)) {
                            comp.setBackground(new Color(251, 175, 22)); // Сбрасываем только не белые
                        }
                    }
                    productPanel.setBackground(new Color(255, 200, 100)); // Подсвечиваем выбранный
    
                    Product product = productDao.getProduct(selectedProductId);
                    nameField.setText(product.getName());
                    priceField.setText(String.valueOf(product.getPrice()));
                    quantityField.setText(String.valueOf(product.getQuantity()));
                }
            });
            bottomPanel.add(Box.createVerticalStrut(5));
            bottomPanel.add(productPanel);
        }
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    private JPanel createFieldRow(String labelText, int fieldWidth, int fieldHeight) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(80, fieldHeight));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        textField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));

        row.add(label);
        row.add(Box.createHorizontalStrut(5));
        row.add(textField);
        row.add(Box.createHorizontalGlue());

        return row;
    }

    private JButton createStyledButton(String text, Dimension size, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(color);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private JPanel allItemsPanel(String _id, String _name, String _price, String _quantity, Color color) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setBackground(color);
        row.setOpaque(true);

        JLabel id = new JLabel(_id);
        id.setPreferredSize(new Dimension(70, 30));
        id.setMaximumSize(new Dimension(70, 30));
        id.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel name = new JLabel(_name);
        name.setPreferredSize(new Dimension(120, 30));
        name.setMaximumSize(new Dimension(120, 30));
        name.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel price = new JLabel(_price);
        price.setPreferredSize(new Dimension(100, 30));
        price.setMaximumSize(new Dimension(100, 30));
        price.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel quantity = new JLabel(_quantity);
        quantity.setPreferredSize(new Dimension(100, 30));
        quantity.setMaximumSize(new Dimension(100, 30));
        quantity.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(id);
        row.add(Box.createHorizontalStrut(10));
        row.add(name);
        row.add(Box.createHorizontalStrut(10));
        row.add(price);
        row.add(Box.createHorizontalStrut(10));
        row.add(quantity);
        row.add(Box.createHorizontalGlue());

        return row;
    }
}