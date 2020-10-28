import java.sql.*;
import org.postgresql.Driver;

public class PostgreSQL {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://193.33.111.196:5431/postgres";
        String user = "admin";
        String password = "admin";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM USERS")) {

            while (rs.next()) {
                System.out.print("ID: " + rs.getString(1) + " ");
                System.out.print("FirstName: " + rs.getString(2) + " ");
                System.out.print("LastName:  " + rs.getString(3) + " ");
                System.out.print("E-mail: " + rs.getString(4) + " ");
                System.out.println("Password: " + rs.getString(5) + " ");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}