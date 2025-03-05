# Library Management System (Console-Based)

## 📌 Overview
The **Library Management System** is a simple **console-based Java application** that allows users to **add books, view books, issue books, and return books** using a **MySQL database**. It demonstrates basic **CRUD operations** (Create, Read, Update, Delete) using **JDBC**.

## 📂 Features
- 📚 **Add Books** – Store book details in the database.
- 📖 **View Books** – Display available books.
- 🔖 **Issue Books** – Borrow books and update inventory.
- 🔄 **Return Books** – Return books and update records.
- 🛠 **Database Integration** – Uses MySQL for data storage.

## 🏗 Tech Stack
- **Programming Language:** Java
- **Database:** MySQL
- **Libraries:** JDBC (Java Database Connectivity)

## ⚙️ Setup Instructions

### 1️⃣ Install Dependencies
Ensure you have the following installed:
- **Java Development Kit (JDK)** (Version 8+)
- **MySQL Server**
- **JDBC Driver** for MySQL (`mysql-connector-java.jar`)

### 2️⃣ Create MySQL Database & Tables
Run the following SQL commands in **MySQL Workbench** or **Command Line**:

```sql
CREATE DATABASE library_db;
USE library_db;

CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    quantity INT
);

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    member_name VARCHAR(255),
    issue_date DATE,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);
```

### 3️⃣ Configure Database Connection in Java
Update **LibraryManagement.java** with your **MySQL username and password**:

```java
static final String URL = "jdbc:mysql://localhost:3306/library_db";
static final String USER = "your_mysql_username";
static final String PASSWORD = "your_mysql_password";
```

### 4️⃣ Compile and Run
Open a **terminal** or **command prompt**, navigate to the project directory, and run:

```sh
javac LibraryManagement.java
java LibraryManagement
```

## 🎮 Usage Guide
1. **Run the program** and choose an option from the menu.
2. Follow the prompts to **add books, view books, issue, or return books**.
3. MySQL database updates automatically with each operation.
4. To exit, choose option **5 (Exit)**.

## 🚀 Future Enhancements
- ✅ Add a **GUI** using Java Swing.
- ✅ Implement **user authentication**.
- ✅ Generate **book issue reports**.

## 📜 License
This project is **open-source** and available for modification and enhancement!

---

💡 **Contributions are welcome!** If you find any issues or want to improve this project, feel free to contribute. 😊

