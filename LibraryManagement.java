import java.sql.*;
import java.util.Scanner;

public class LibraryManagement 
{
    static final String URL = "jdbc:mysql://localhost:3306/library_db";
    static final String USER = "root";  // Your MySQL username
    static final String PASSWORD = "";  // Your MySQL password

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) 
        {
            while (true) 
            {
                System.out.println("\nLibrary Management System");
                System.out.println("1. Add Book");
                System.out.println("2. View Books");
                System.out.println("3. Issue Book");
                System.out.println("4. Return Book");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  

                switch (choice) {
                    case 1:
                        addBook(conn, scanner);
                        break;
                    case 2:
                        viewBooks(conn);
                        break;
                    case 3:
                        issueBook(conn, scanner);
                        break;
                    case 4:
                        returnBook(conn, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    private static void addBook(Connection conn, Scanner scanner) throws SQLException 
    {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        String query = "INSERT INTO books (title, author, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) 
        {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
            System.out.println("Book added successfully!");
        }
    }

    private static void viewBooks(Connection conn) throws SQLException 
    {
        String query = "SELECT * FROM books";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) 
             {
            System.out.println("\nAvailable Books:");
            while (rs.next()) 
            {
                System.out.println("ID: " + rs.getInt("book_id") + ", Title: " + rs.getString("title") +
                        ", Author: " + rs.getString("author") + ", Quantity: " + rs.getInt("quantity"));
            }
        }
    }

    private static void issueBook(Connection conn, Scanner scanner) throws SQLException 
    {
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter your name: ");
        String memberName = scanner.nextLine();

        String checkQuery = "SELECT quantity FROM books WHERE book_id = ?";
        String issueQuery = "INSERT INTO transactions (book_id, member_name, issue_date) VALUES (?, ?, CURDATE())";
        String updateQuery = "UPDATE books SET quantity = quantity - 1 WHERE book_id = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) 
        {
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt("quantity") > 0) 
            {
                try (PreparedStatement issueStmt = conn.prepareStatement(issueQuery);
                     PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) 
                     {
                    issueStmt.setInt(1, bookId);
                    issueStmt.setString(2, memberName);
                    issueStmt.executeUpdate();

                    updateStmt.setInt(1, bookId);
                    updateStmt.executeUpdate();
                    System.out.println("Book issued successfully!");
                }
            } 
            else 
            {
                System.out.println("Book not available!");
            }
        }
    }

    private static void returnBook(Connection conn, Scanner scanner) throws SQLException 
    {
        System.out.print("Enter transaction ID: ");
        int transactionId = scanner.nextInt();

        String checkQuery = "SELECT book_id FROM transactions WHERE transaction_id = ? AND return_date IS NULL";
        String returnQuery = "UPDATE transactions SET return_date = CURDATE() WHERE transaction_id = ?";
        String updateQuery = "UPDATE books SET quantity = quantity + 1 WHERE book_id = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) 
        {
            checkStmt.setInt(1, transactionId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) 
            {
                int bookId = rs.getInt("book_id");

                try (PreparedStatement returnStmt = conn.prepareStatement(returnQuery);
                     PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) 
                     {
                    returnStmt.setInt(1, transactionId);
                    returnStmt.executeUpdate();

                    updateStmt.setInt(1, bookId);
                    updateStmt.executeUpdate();
                    System.out.println("Book returned successfully!");
                }
            } 
            else 
            {
                System.out.println("Invalid transaction ID!");
            }
        }
    }
}
