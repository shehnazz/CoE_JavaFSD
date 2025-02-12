package FeeReportGeneration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {
    public static boolean addStudent(String name, String email, String course, double fee, double paid, String address, String phone) {
        String query = "INSERT INTO student (name, email, course, fee, paid, due, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            double due = fee - paid;
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, course);
            stmt.setDouble(4, fee);
            stmt.setDouble(5, paid);
            stmt.setDouble(6, due);
            stmt.setString(7, address);
            stmt.setString(8, phone);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void checkDueFee() {
        String query = "SELECT * FROM student WHERE due > 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Student: " + rs.getString("name") + ", Due: " + rs.getDouble("due"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
