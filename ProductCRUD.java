// Program 1: CRUD Operations on Product Table

import java.sql.*;
import java.util.*;

public class ProductCRUD {
    static final String DB_URL = "jdbc:mysql://localhost:3306/yourdb";
    static final String USER = "root";
    static final String PASS = "yourpassword";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.setAutoCommit(false);
            Scanner sc = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n1. Create Product\n2. Read Products\n3. Update Product\n4. Delete Product\n5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Product ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Product Name: ");
                        String name = sc.nextLine();
                        System.out.print("Price: ");
                        double price = sc.nextDouble();
                        System.out.print("Quantity: ");
                        int qty = sc.nextInt();

                        try (PreparedStatement pst = conn.prepareStatement("INSERT INTO Product VALUES (?, ?, ?, ?)");) {
                            pst.setInt(1, id);
                            pst.setString(2, name);
                            pst.setDouble(3, price);
                            pst.setInt(4, qty);
                            pst.executeUpdate();
                            conn.commit();
                            System.out.println("Product added.");
                        } catch (SQLException e) {
                            conn.rollback();
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 2:
                        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM Product")) {
                            while (rs.next()) {
                                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getDouble(3) + " " + rs.getInt(4));
                            }
                        }
                        break;
                    case 3:
                        System.out.print("Enter ProductID to update: ");
                        int updateId = sc.nextInt();
                        System.out.print("New Price: ");
                        double newPrice = sc.nextDouble();
                        System.out.print("New Quantity: ");
                        int newQty = sc.nextInt();
                        try (PreparedStatement pst = conn.prepareStatement("UPDATE Product SET Price=?, Quantity=? WHERE ProductID=?")) {
                            pst.setDouble(1, newPrice);
                            pst.setInt(2, newQty);
                            pst.setInt(3, updateId);
                            pst.executeUpdate();
                            conn.commit();
                            System.out.println("Product updated.");
                        } catch (SQLException e) {
                            conn.rollback();
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.print("Enter ProductID to delete: ");
                        int deleteId = sc.nextInt();
                        try (PreparedStatement pst = conn.prepareStatement("DELETE FROM Product WHERE ProductID=?")) {
                            pst.setInt(1, deleteId);
                            pst.executeUpdate();
                            conn.commit();
                            System.out.println("Product deleted.");
                        } catch (SQLException e) {
                            conn.rollback();
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                }
            } while (choice != 5);

        } catch (SQLException e) {
            System.out.println("Connection Error: " + e.getMessage());
        }
    }
}

//OUTUT:-
1. Create Product
2. Read Products
3. Update Product
4. Delete Product
5. Exit
Enter choice: 1
Product ID: 101
Product Name: Keyboard
Price: 799.99
Quantity: 20
Product added.
Enter choice: 2
101 Keyboard 799.99 20
Enter choice: 3
Enter ProductID to update: 101
New Price: 749.99
New Quantity: 25
Product updated.
Enter choice: 4
Enter ProductID to delete: 101
Product deleted.
Enter choice: 5
Exiting...
