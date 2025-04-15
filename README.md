Product Manager Application
Project Overview
The Product Manager Application is a Java-based desktop application designed to manage product information stored in a PostgreSQL database. It features a graphical user interface (GUI) built with Java Swing, allowing users to add, update, delete, and view products. The application supports JSON data export (Ctrl+E) and import (Ctrl+I) functionalities, enabling seamless data transfer. The system ensures robust error handling, input validation, and a user-friendly experience.

Key Features
CRUD Operations: Add, update, delete, and view products with fields for ID, name, price, and quantity.
Database Integration: Connects to a PostgreSQL database to store and retrieve product data.
JSON Import/Export:
Export product data (excluding IDs) to a JSON file.
Import product data from a JSON file into the database.
GUI:
Displays products in a scrollable panel.
Allows selection of products to update or delete.
Provides input fields for product details with validation.
Includes buttons for Add, Update, Delete, and Clear actions.
Hotkeys:
Ctrl+E: Export data to JSON.
Ctrl+I: Import data from JSON.
Error Handling: Validates inputs (e.g., positive integers for price/quantity, non-empty names) and displays user-friendly error messages via dialogs.
Sorting: Products are sorted by ID in the display panel.
Prerequisites
To run this application, ensure you have the following installed:

Java Development Kit (JDK): Version 11 or higher (developed with JDK 17).
PostgreSQL: Version 12 or higher.
PostgreSQL JDBC Driver: Included in the project dependencies (e.g., postgresql-42.7.3.jar).
Maven (optional): For dependency management, though dependencies can be manually included.
Git: To clone the repository.
Setup Instructions
1. Clone the Repository
Clone the project to your local machine:

bash

Copy
git clone https://github.com/your-username/product-manager.git
cd product-manager
2. Configure PostgreSQL Database
Install PostgreSQL if not already installed (e.g., via apt, brew, or official installer).
Create a Database:
sql

Copy
CREATE DATABASE shop;
Create the Products Table: Connect to the shop database and run:
sql

Copy
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price INTEGER NOT NULL,
    quantity INTEGER NOT NULL
);
Verify Database Access: Ensure the database is running on localhost:5432 (default) or update the connection URL accordingly.
3. Configure Application Properties
Create application.properties: In the src/main/resources directory, create a file named application.properties with the following content:
properties

Copy
db.url=jdbc:postgresql://localhost:5432/shop
db.user=admin
db.password=admin
Replace admin/admin with your PostgreSQL username and password if different.
Place the File: Ensure application.properties is in the classpath (e.g., src/main/resources for Maven projects).
4. Add PostgreSQL JDBC Driver
Manual Setup: Download the PostgreSQL JDBC driver (postgresql-42.7.3.jar) from Maven Repository or the official site. Add it to your project’s classpath (e.g., place it in a lib folder and include it in your IDE or build path).
Maven Setup (recommended): If using Maven, add the following dependency to your pom.xml:
xml

Copy
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.3</version>
</dependency>
Additionally, include Jackson for JSON processing:
xml

Copy
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.2</version>
</dependency>
5. Compile the Project
Using an IDE (e.g., IntelliJ IDEA, Eclipse):
Import the project as a Java project.
Ensure the JDBC driver and application.properties are in the classpath.
Build the project (usually via the IDE’s build/run option).
Using Command Line: Navigate to the project root and compile:
bash

Copy
javac -cp ".:lib/postgresql-42.7.3.jar:lib/jackson-databind-2.17.2.jar" -d bin src/main/java/com/example/*.java src/main/java/com/example/ui/*.java src/main/java/com/example/dao/*.java src/main/java/com/example/model/*.java
Note: Adjust the -cp path to include Jackson JARs and use ; instead of : on Windows.
6. Run the Application
Using an IDE: Run the Main class (com.example.Main).
Using Command Line:
bash

Copy
java -cp "bin:lib/postgresql-42.7.3.jar:lib/jackson-databind-2.17.2.jar" com.example.Main
Ensure application.properties is in the classpath (e.g., copy it to the directory where you run the command).
Project Structure
text

Copy
product-manager/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           ├── Main.java              # Entry point, initializes DB connection and GUI
│       │           ├── JsonExporter.java      # Handles JSON export (Ctrl+E)
│       │           ├── ImportJson.java        # Handles JSON import (Ctrl+I)
│       │           ├── dao/
│       │           │   └── ProductDaoImpl.java # Data Access Object for CRUD operations
│       │           ├── model/
│       │           │   └── Product.java       # Product model class
│       │           └── ui/
│       │               └── MainFrame.java     # Swing GUI implementation
│       └── resources/
│           └── application.properties         # Database configuration
├── lib/
│   ├── postgresql-42.7.3.jar                  # PostgreSQL JDBC driver
│   └── jackson-databind-2.17.2.jar            # Jackson library for JSON
├── README.md                                  # Project documentation
└── pom.xml                                    # (Optional) Maven configuration
Usage
Launch the Application: Run the Main class. The application will attempt to connect to the PostgreSQL database using credentials from application.properties. If successful, the GUI (MainFrame) will appear.
GUI Layout:
Top Panel:
Input Fields: Enter product name (text), price (integer), and quantity (integer).
Buttons:
Add: Adds a new product to the database.
Update: Updates the selected product.
Delete: Deletes the selected product.
Clear: Resets input fields and deselects products.
Bottom Panel:
Displays all products in a scrollable list, sorted by ID.
Each product row shows ID, name, price, and quantity.
Click a product row to select it (highlights in yellow) and populate input fields for updating.
Hotkeys:
Ctrl+E: Opens a folder chooser to export products to Products.json (excludes IDs).
Ctrl+I: Opens a file chooser to import products from a JSON file.
Input Validation:
Name cannot be empty or whitespace-only.
Price and quantity must be positive integers.
Errors (e.g., invalid input, database issues) display in dialog boxes.
JSON Import/Export:
Export: Saves product data (name, price, quantity) to a JSON file in the selected folder.
Import: Reads a JSON file with product data and inserts it into the database. The JSON must match the expected format (e.g., [{ "name": "Item", "price": 10, "quantity": 5 }, ...]).
Example Workflow
Start the application.
Enter Laptop, 1000, 5 in the name, price, and quantity fields, then click Add.
The bottom panel updates to show the new product.
Click the product row to select it, change the price to 1200, and click Update.
Press Ctrl+E to export products to a JSON file.
Delete the product by selecting it and clicking Delete.
Press Ctrl+I to import products from the previously exported JSON file.
Sample JSON File Format
For import/export:

json

Copy
[
    {
        "name": "Laptop",
        "price": 1000,
        "quantity": 5
    },
    {
        "name": "Phone",
        "price": 500,
        "quantity": 10
    }
]
Troubleshooting
"Driver not found" Error: Ensure the PostgreSQL JDBC driver is in the classpath (lib/postgresql-42.7.3.jar).
"application.properties not found": Verify the file exists in src/main/resources or the runtime directory.
Database Connection Failure: Check that PostgreSQL is running, the shop database exists, and the credentials in application.properties are correct.
JSON Import Errors: Ensure the JSON file matches the expected format (keys: name, price, quantity).
GUI Not Displaying: Confirm you’re running on the Event Dispatch Thread (handled by SwingUtilities.invokeLater in Main.java).
NumberFormatException: Input only integers for price and quantity fields.
Additional Notes
Simplifications:
The application assumes a simple database schema with no additional constraints (e.g., unique names).
JSON export excludes IDs to avoid conflicts during import, as IDs are auto-generated by the database.
Challenges Addressed:
Hotkey Support: Implemented platform-agnostic Ctrl/Cmd+E and Ctrl/Cmd+I using KeyStroke.
Dynamic GUI Updates: The bottom panel refreshes automatically after CRUD operations or imports.
Error Handling: Comprehensive checks for null DAO, invalid inputs, and database errors.
Selection Logic: Clicking a product highlights it and prevents accidental updates/deletes without selection.
Potential Improvements:
Add input field for filtering products by name.
Implement batch updates for large JSON imports.
Add confirmation dialogs for delete operations.
Support additional product attributes (e.g., category, description).
Dependencies:
PostgreSQL JDBC driver for database connectivity.
Jackson Databind for JSON serialization/deserialization.
Testing: Tested on macOS and Windows with JDK 17 and PostgreSQL 14. Ensure hotkeys work (Ctrl on Windows/Linux, Cmd on macOS).
Contributing
Feel free to fork this repository, submit issues, or create pull requests for enhancements. Suggested contributions:

UI improvements (e.g., table-based display instead of panels).
Advanced database features (e.g., indexes, constraints).
Additional export formats (e.g., CSV).