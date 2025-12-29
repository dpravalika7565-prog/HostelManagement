import java.sql.*;
import java.util.Scanner;

public class HotelManagement {

    static Connection con;

    // Connect to SQLite
    static void connect() throws Exception {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:C:/SqlLite/hotel.db");

        Statement st = con.createStatement();
        st.execute("PRAGMA foreign_keys = ON");
    }

    // Add Room
    static void addRoom(Scanner sc) throws Exception {
        String sql = "INSERT INTO Hotel(room_id, room_type, price) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        System.out.print("Room ID: ");
        ps.setInt(1, sc.nextInt());
        sc.nextLine();

        System.out.print("Room Type: ");
        ps.setString(2, sc.nextLine());

        System.out.print("Price: ");
        ps.setDouble(3, sc.nextDouble());

        ps.executeUpdate();
        System.out.println("Room added successfully.\n");
        ps.close();
    }

    // View Rooms
    static void viewRooms() throws Exception {
        String sql = "SELECT * FROM Hotel";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("\nID  Type   Guest   CheckIn    CheckOut   Price  Status");
        System.out.println("---------------------------------------------------------");

        while (rs.next()) {
            System.out.println(
                rs.getInt("room_id") + "  " +
                rs.getString("room_type") + "  " +
                rs.getString("guest_name") + "  " +
                rs.getString("check_in") + "  " +
                rs.getString("check_out") + "  " +
                rs.getDouble("price") + "  " +
                rs.getString("status")
            );
        }
        rs.close();
        ps.close();
    }

    // Book Room
    static void bookRoom(Scanner sc) throws Exception {
        String sql = "UPDATE Hotel SET guest_name=?, check_in=?, check_out=?, status='Booked' WHERE room_id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        sc.nextLine();
        System.out.print("Guest Name: ");
        ps.setString(1, sc.nextLine());

        System.out.print("Check-in Date (YYYY-MM-DD): ");
        ps.setString(2, sc.nextLine());

        System.out.print("Check-out Date (YYYY-MM-DD): ");
        ps.setString(3, sc.nextLine());

        System.out.print("Room ID: ");
        ps.setInt(4, sc.nextInt());

        ps.executeUpdate();
        System.out.println("Room booked successfully.\n");
        ps.close();
    }

    // Checkout Room
    static void checkoutRoom(Scanner sc) throws Exception {
        String sql = "UPDATE Hotel SET guest_name=NULL, check_in=NULL, check_out=NULL, status='Available' WHERE room_id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        System.out.print("Room ID: ");
        ps.setInt(1, sc.nextInt());

        ps.executeUpdate();
        System.out.println("Checkout completed.\n");
        ps.close();
    }

    // Delete Room
    static void deleteRoom(Scanner sc) throws Exception {
        String sql = "DELETE FROM Hotel WHERE room_id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        System.out.print("Room ID: ");
        ps.setInt(1, sc.nextInt());

        ps.executeUpdate();
        System.out.println("Room deleted.\n");
        ps.close();
    }

    public static void main(String[] args) throws Exception {
        connect();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- HOTEL MANAGEMENT SYSTEM ---");
            System.out.println("1. Add Room");
            System.out.println("2. View Rooms");
            System.out.println("3. Book Room");
            System.out.println("4. Checkout Room");
            System.out.println("5. Delete Room");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1: addRoom(sc); break;
                case 2: viewRooms(); break;
                case 3: bookRoom(sc); break;
                case 4: checkoutRoom(sc); break;
                case 5: deleteRoom(sc); break;
                case 6: System.out.println("Thank you!"); break;
                default: System.out.println("Invalid choice");
            }
        } while (choice != 6);

        con.close();
        sc.close();
    }
}