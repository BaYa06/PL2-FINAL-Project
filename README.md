# Product Manager

A simple desktop application for managing products using Java, Swing, and PostgreSQL. It allows users to add, update, delete, and view products in a database via a graphical interface.

## Overview

The application consists of the following main components:
- **Main.java**: Entry point, loads the PostgreSQL driver, establishes a database connection, and launches the GUI.
- **MainFrame.java**: Graphical interface (Swing), displays input fields, control buttons, and a product list.
- **Product.java**: Data model for a product (id, name, price, quantity).
- **ProductDaoImpl.java**: DAO class for database interaction (CRUD operations).

## Features
- **Add**: Adds a new product to the database with an auto-incrementing `id`.
- **Update**: Updates a selected product by `id`.
- **Delete**: Removes a selected product from the list.
- **Clear**: Clears input fields and resets the selection.
- **Product List**: Displays all products from the database with clickable selection for editing or deletion.

## Dependencies
- Java 17+
- PostgreSQL JDBC Driver (`org.postgresql:postgresql:42.7.3`)
- Maven (for building and managing dependencies)

## Setup

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd product-manager
   ```

2. **Set up the database:**
   - Install PostgreSQL and create a database:
     ```sql
     CREATE DATABASE product_db;
     ```
   - Create the `products` table:
     ```sql
     CREATE TABLE products (
         id SERIAL PRIMARY KEY,
         name VARCHAR(100) NOT NULL,
         price INTEGER NOT NULL,
         quantity INTEGER NOT NULL
     );
     ```

3. **Configure `application.properties`:**
   - Create a file `src/main/resources/application.properties` with the following content:
     ```
     db.url=jdbc:postgresql://localhost:5432/product_db
     db.user=your_username
     db.password=your_password
     ```
   - Replace `your_username` and `your_password` with your PostgreSQL credentials.

4. **Build the project:**
   ```bash
   mvn clean package
   ```

## Running the Application
Run the JAR file:
```bash
java -jar target/online-shop-1.0-jar-with-dependencies.jar
```

## Usage
1. Launch the application.
2. Use the input fields to enter product details (Name, Price, Quantity).
3. Click "Add" to save a new product.
4. Click on a product in the list to select it (highlighted in light orange).
5. Use "Update" to modify the selected product, "Delete" to remove it, or "Clear" to reset the form.

## Project Structure
```
product-manager/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/
│   │   │   │   └── Main.java
│   │   │   ├── com/example/ui/
│   │   │   │   └── MainFrame.java
│   │   │   ├── com/example/model/
│   │   │   │   └── Product.java
│   │   │   ├── com/example/dao/
│   │   │   │   └── ProductDaoImpl.java
│   │   ├── resources/
│   │   │   └── application.properties
├── pom.xml
└── README.md
```

## Notes
- Ensure the PostgreSQL server is running before launching the application.
- Price and Quantity must be positive integers; invalid input will trigger an error message.