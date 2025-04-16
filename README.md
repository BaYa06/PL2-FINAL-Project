# Product Manager (Java Swing + PostgreSQL)

## Descripton
This project is a simple desktop application that allows users to add, update, delete, and manage product data stored in a PostgreSQL database. It also supports exporting product data to JSON and importing it back when needed.

## Made by: KHOLIKOV BAIIRBEK COMCEH24 

## Main Features

- ✅ Add new products to the database  
- 📝 Edit existing product information  
- ❌ Delete products from the database  
- 📤 Export product data to a JSON file  
- 📥 Import product data from a JSON file  
- 📂 Choose save location using file chooser  
- ⌨️ Use keyboard shortcuts for quick actions  
- 🖥️ Simple and clean graphical interface (GUI)
- **JSON import/export** (Ctrl+I/Ctrl+E hotkeys)
- **Swing GUI** with responsive design
- PostgreSQL connectivity
- Link to presentation: https://www.canva.com/design/DAGksAIwUes/_q_xLRAkDHGS9wWSLwN7Zg/view?utm_content=DAGksAIwUes&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=hf9d33c1c13

---

## Objectives:
- Build a GUI-based application using Java Swing.
- Implement CRUD operations (Create, Read, Update, Delete) on a product list.
- Connect the application to a PostgreSQL database.
- Allow users to export and import product data in JSON format.
- Use file chooser dialogs to save and load files.
- Provide keyboard shortcuts for quicker operations.
- Maintain clean, readable, and modular code.
- Ensure proper input validation and error handling.
- Deliver working software with supporting documentation.
- Demonstrate Java skills in file handling, database integration, and GUI design.

---

## Project Requirement List:
1. GUI using Java Swing.
2. PostgreSQL database connection.
3. Add new product entries.
4. Edit product details.
5. Delete products.
6. Export product data to JSON.
7. Import product data from JSON.
8. File chooser for export/import location.
9. Keyboard shortcuts (Ctrl+E for export, Ctrl+I for import).
10. Display success/error messages using dialog boxes.

---

## Documentation:

### Algorithms & Functions:
- JDBC is used to perform SQL queries and updates.
- JSON export/import is handled using the Jackson ObjectMapper library.
- `ResultSetMetaData` is used to dynamically read column data.
- Action listeners and key bindings are used for GUI interaction.

### Data Structures:
- Product data is stored in `List<Map<String, Object>>` for JSON export/import.
- HashMap used for each product's data.

### Modules:
- `JsonExporter.java`: Handles export to JSON.
- `ImportJson.java`: Handles import from JSON.
- `MainFrame.java`: GUI and event bindings.

### Challenges Faced:
- Setting up PostgreSQL connection properly.
- Ensuring JSON file format compatibility.
- Handling file selection and validation.
- Cross-platform keyboard shortcuts.

---

## Test Cases and Outputs:

### Test Case 1:
**Action:** Add product: Name = "Laptop", Price = 1000.0
**Expected Result:** Product appears in the database and UI list.

### Test Case 2:
**Action:** Export products
**Expected Result:** JSON file is saved with all current products.

### Test Case 3:
**Action:** Import products from JSON
**Expected Result:** Data is added into the database, avoiding duplicates or ID conflicts.

### Test Case 4:
**Action:** Press Ctrl+E
**Expected Result:** Export window opens.

### Test Case 5:
**Action:** Press Ctrl+I
**Expected Result:** Import window opens.

(Screenshots should be included manually in the final report file.)

---

## Code:
- Code is organized in separate files by functionality.
- JDBC used for DB actions.
- Jackson used for JSON parsing.
- GUI uses Swing with `JFrame`, `JButton`, and `JTable`.

(No excessive comments were added as per the instruction.)

---

## Files:
- `export.json` and `import.json`: for storing product data.
- `.java` source files: project source code.
- `.md` files: documentation.

---

> Ensure all screenshots and output files are submitted along with this documentation and source code for full evaluation.
![Screenshot](https://github.com/BaYa06/PL2-FINAL-Project/blob/main/src/main/resources/Снимок%20экрана%202025-04-16%20в%2015.43.51.png)
![Screenshot](https://github.com/BaYa06/PL2-FINAL-Project/blob/main/src/main/resources/Снимок%20экрана%202025-04-16%20в%2015.44.46.png)
![Screenshot](https://github.com/BaYa06/PL2-FINAL-Project/blob/main/src/main/resources/Снимок%20экрана%202025-04-16%20в%2015.45.10.png)
![Screenshot](https://github.com/BaYa06/PL2-FINAL-Project/blob/main/src/main/resources/Снимок%20экрана%202025-04-16%20в%2015.45.25.png)
![Screenshot](https://github.com/BaYa06/PL2-FINAL-Project/blob/main/src/main/resources/Снимок%20экрана%202025-04-16%20в%2015.45.41.png)
![Screenshot](https://github.com/BaYa06/PL2-FINAL-Project/blob/main/src/main/resources/Снимок%20экрана%202025-04-16%20в%2015.53.42.png)

---

## 🚀 **Setup Guide**

### **Prerequisites**
1. **Java 17+** (OpenJDK or Oracle JDK)
2. **PostgreSQL 14+** (with a database named `shop`)
3. **Maven** (for dependency management)

### **1. Database Setup**
```sql
CREATE DATABASE shop;

-- Connect to the 'shop' database
\c shop

-- Create table
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price INTEGER CHECK (price > 0),
    quantity INTEGER CHECK (quantity > 0)
);

-- Create user (optional)
CREATE USER admin WITH PASSWORD 'admin';
GRANT ALL PRIVILEGES ON TABLE products TO admin;

2. Configuration

Create src/main/resources/application.properties:
db.url=jdbc:postgresql://localhost:5432/shop
db.user=admin
db.password=admin

3. Build & Run
mvn clean package
java -jar target/your-app.jar
(Replace your-app.jar with your actual JAR name)

🛠 Features

1. Product Management

Add: Name (string), Price (int > 0), Quantity (int > 0)
Update: Select a product → Modify fields → Click "Update"
Delete: Select a product → Click "Delete"
Clear: Resets form and selection
2. JSON Operations

Hotkey	Action
Ctrl+E	Export to JSON (excludes IDs)
Ctrl+I	Import from JSON

JSON Format Example
[
  {
    "name": "Laptop",
    "price": 999,
    "quantity": 10
  }
]

3. UI Components

Top Panel: Input form with validation
Bottom Panel: Scrollable product list (select items by clicking)
Color Scheme: Amber (#FBAB16) and white
🧩 Project Structure
src/
├── main/
│   ├── java/com/example/
│   │   ├── Main.java              # Entry point (DB connection + GUI launch)
│   │   ├── ui/
│   │   │   └── MainFrame.java     # Swing GUI implementation
│   │   ├── dao/
│   │   │   └── ProductDaoImpl.java # PostgreSQL CRUD operations
│   │   ├── model/
│   │   │   └── Product.java       # Data model
│   │   ├── ImportJson.java        # JSON import logic
│   │   └── JsonExporter.java      # JSON export logic
│   └── resources/
│       └── application.properties # DB credentials

⚙️ Dependencies

Managed by Maven (pom.xml):
<dependencies>
    <!-- PostgreSQL Driver -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.6.0</version>
    </dependency>
    
    <!-- Jackson (JSON) -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.15.2</version>
    </dependency>
    
    <!-- Swing (included in JDK) -->
</dependencies>

 Troubleshooting

Issue	Solution
"DB connection failed"	Check application.properties and PostgreSQL service status
"No such table: products"	Run the SQL setup script above
Hotkeys not working	Ensure OS-specific modifiers (Ctrl vs. Cmd on macOS)
JSON import fails	Verify file format matches the example

👨‍💻 Developer Notes

Threading: GUI runs on Swing's Event Dispatch Thread (EDT)
Error Handling: Validation for price/quantity (must be > 0)
Extensibility: Add new DAOs for additional tables


---

### **Key Highlights**
1. **Step-by-Step DB Setup** – Includes exact SQL commands.
2. **Hotkey Documentation** – Clear table for keyboard shortcuts.
3. **Validation Rules** – Explains constraints (e.g., price > 0).
4. **Troubleshooting Table** – Quick fixes for common issues.
5. **Project Structure** – Maps files to their roles.

To use this:
1. Save as `README.md` in your project root.
2. Update the `your-app.jar` placeholder if needed.
3. Add screenshots by including `![Screenshot](screenshot.png)` if desired.