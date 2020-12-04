package org.project.benchmark.algorithm;


import com.project.benchmark.algorithm.PostgreSQL;
import com.project.benchmark.algorithm.Configurations;
import com.project.benchmark.algorithm.Responses;
import com.project.benchmark.algorithm.Tests;
import org.junit.Test;

import java.sql.Timestamp;

public class PostgreSQLTest {
    @Test
    public void selectuserTest() {
        String url = "jdbc:postgresql://{host:port}/{dbName}"; String user = "{user}";
        //String url = "jdbc:postgresql://{host:port}/{dbName}"; String user = "{user}";
        String password = "admin";
        //insert(url, user, password);
        PostgreSQL.selectUser(url, user, password);
    }

    @Test
    public void insertTest() {
        String url = "jdbc:postgresql://{host:port}/{dbName}"; String user = "{user}";
        //String url = "jdbc:postgresql://{host:port}/{dbName}"; String user = "{user}";
        String password = "admin";
        //insert(url, user, password);
        PostgreSQL.insert(url, user, password);
    }

    @Test
    public void insertConfigurationTest() {
        //String url = "jdbc:postgresql://{host:port}/{dbName}"; String user = "{user}";
        String url = "jdbc:postgresql://{host:port}/{dbName}";
        String user = "{user}";
        String password = "{password}";
        Configurations configurations = new Configurations(1, "Testowa", new Timestamp(new java.util.Date().getTime()), false, true, 1,
                33, 33, 24, 10,  30, 70, 30, 70, 15, 70, 15,
        50, 50, 5, 1000);
        PostgreSQL.insertConfigurations(url, user, password, configurations);
    }

    @Test
    public void insertTestsTest() {
        //String url = "jdbc:postgresql://{host:port}/{dbName}"; String user = "{user}";
        String url = "jdbc:postgresql://{host:port}/{dbName}"; String user = "{user}";
        String password = "{password}";
        Tests tests = new Tests(1, 1, new Timestamp(new java.util.Date().getTime()),new Timestamp(new java.util.Date().getTime()));
        PostgreSQL.insertTests(url, user, password, tests);
    }

    @Test
    public void insertTimesTest() {
        //String url = "jdbc:postgresql://{host:port}/{dbName}"; String user = "{user}";
        String url = "jdbc:postgresql://{host:port}/{dbName}"; String user = "{user}";
        String password = "{password}";
        Responses responses = new Responses(1, 1, "user/orders", 200, "GET", new Timestamp(new java.util.Date().getTime()),
                15, 254, 127);
        PostgreSQL.insertTimes(url, user, password, responses);
    }
}
