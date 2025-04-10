package com.example.model;

public class Product {
    private int id;
    private String name;
    private int price;
    private int quantity;

    public Product(int id, String name, int price, int quantity) { // Конструктор изменён
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {return id;}
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }
}