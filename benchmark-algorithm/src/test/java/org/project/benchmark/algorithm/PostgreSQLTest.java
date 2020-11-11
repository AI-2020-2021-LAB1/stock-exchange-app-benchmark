package com.project.benchmark.algorithm;

import com.project.benchmark.algorithm.service.Configurations;
import com.project.benchmark.algorithm.service.Responses;
import com.project.benchmark.algorithm.service.Tests;

import java.sql.*;

public class PostgreSQL {

    public static void selectUser(String url, String user, String password){
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
            ps.setString(2, "Test");
            ps.setString(3, "Testowy");
            ps.setString(4, "test@testowy.pl");
            ps.setString(5, "qMFO4Xg5akqAoi975OFvPL8YmK8EcS4M5.$2a$10$Vx4LEAl9zn3IqkXG6");
            ps.setString(6, "USER");
            ps.setInt(7, 997);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void insertConfigurations(String url, String user, String password, Configurations configurations) {
        String query = "INSERT INTO CONFIGURATIONS(ID, NAME, CREATED_AT, ARCHIVED, REGISTRATION, CERTAINTY_LEVEL, LOGIN_ALL_STOCKS, " +
                "LOGIN_OWNED_STOCKS, LOGIN_USER_ORDERS, LOGIN_MAKE_ORDER, ALL_STOCKS_MAKE_ORDER, ALL_STOCKS_END, OWNED_STOCKS_MAKE_ORDER, " +
                "OWNED_STOCKS_END, USER_ORDERS_MAKE_ORDER, USER_ORDERS_END, USER_ORDERS_DELETE_ORDER, MAKE_ORDER_BUY_ORDER, MAKE_ORDER_SELL_ORDER, " +
                " NO_OF_OPERATIONS, NO_OF_MONEY) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, configurations.getId());
            ps.setString(2, configurations.getName());
            ps.setTimestamp(3, configurations.getCreatedAt());
            ps.setBoolean(4, configurations.isArchived());
            ps.setBoolean(5, configurations.isRegistration());
            ps.setInt(6, configurations.getCertaintyLevel());
            ps.setInt(7, configurations.getLoginAllStocks());
            ps.setInt(8, configurations.getLoginOwnedStocks());
            ps.setInt(9, configurations.getLoginUserOrders());
            ps.setInt(10, configurations.getLoginMakeOrder());
            ps.setInt(11, configurations.getAllStocksMakeOrder());
            ps.setInt(12, configurations.getAllStocksEnd());
            ps.setInt(13, configurations.getOwnedStocksMakeOrder());
            ps.setInt(14, configurations.getOwnedStocksEnd());
            ps.setInt(15, configurations.getUserOrdersMakeOrder());
            ps.setInt(16, configurations.getUserOrdersEnd());
            ps.setInt(17, configurations.getUserOrdersDeleteOrder());
            ps.setInt(18, configurations.getMakeOrderBuyOrder());
            ps.setInt(19, configurations.getMakeOrderSellOrder());
            ps.setInt(20, configurations.getNoOfOperations());
            ps.setInt(21, configurations.getNoOfMoney());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void insertTests(String url, String user, String password, Tests tests){
        String query = "INSERT INTO TESTS(ID, CONFIGURATION_ID, START_DATE, END_DATE) VALUES(?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, tests.getId());
            ps.setLong(2, tests.getConfigurationId());
            ps.setTimestamp(3, tests.getStartDate());
            ps.setTimestamp(4, tests.getEndDate());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void insertTimes(String url, String user, String password, Responses responses) {
        String query = "INSERT INTO RESPONSES(ID, TEST_ID, ENDPOINT, STATUS_CODE, METHOD, RESPONSE_DATE, USERS_LOGGED_IN, REQUEST_RESPONSE_TIME, OPERATION_TIME) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, responses.getId());
            ps.setLong(2, responses.getTestId());
            ps.setString(3, responses.getEndpoint());
            ps.setInt(4, responses.getStatusCode());
            ps.setString(5, responses.getMethod());
            ps.setTimestamp(6, responses.getResponseDate());
            ps.setInt(7, responses.getUsersLoggedIn());
            ps.setInt(8, responses.getRequestResponseTime());
            ps.setInt(9, responses.getOperationTime());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
