# Product Manager (Java Swing + PostgreSQL)

A desktop application for managing products in a PostgreSQL database, featuring:
- **CRUD operations** (Create, Read, Update, Delete)
- **JSON import/export** (Ctrl+I/Ctrl+E hotkeys)
- **Swing GUI** with responsive design
- PostgreSQL connectivity

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