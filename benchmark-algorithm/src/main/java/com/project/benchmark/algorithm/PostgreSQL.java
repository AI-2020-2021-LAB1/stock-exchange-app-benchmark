package com.project.benchmark.algorithm;

import java.math.BigDecimal;
import java.sql.*;

public class PostgreSQL {

    public static void select(String url, String user, String password){
        try{ Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM USERS ");

            while (rs.next()) {
                System.out.print("ID: " + rs.getString(1) + " ");
                System.out.print("FirstName: " + rs.getString(2) + " ");
                System.out.print("LastName:  " + rs.getString(3) + " ");
                System.out.print("E-mail: " + rs.getString(4) + " ");
                System.out.print("Password: " + rs.getString(5) + " ");
                System.out.print("Role: " + rs.getString(6) + " ");
                System.out.println("Money: " + rs.getInt(7));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void insert(String url, String user, String password){
        String query = "INSERT INTO USERS(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE, MONEY) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = con.prepareStatement(query)){
            ps.setLong(1, 40);
            ps.setString(2, "Chris");
            ps.setString(3, "Lesniak");
            ps.setString(4, "koronakielce@scyzoryki.pl");
            ps.setString(5, "qMFO4Xg5akqAoi975OFvPL8YmK8EcS4M5.$2a$10$Vx4LEAl9zn3IqkXG6");
            ps.setString(6, "USER");
            ps.setInt(7, 997);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
