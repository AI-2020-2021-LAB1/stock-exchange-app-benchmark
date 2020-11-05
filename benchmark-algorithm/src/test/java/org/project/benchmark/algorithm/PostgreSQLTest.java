package org.project.benchmark.algorithm;


import com.project.benchmark.algorithm.PostgreSQL;
import org.junit.Test;

public class PostgreSQLTest {
    @Test
    public void selectTest() {
        String url = "jdbc:postgresql://193.33.111.196:5431/postgres"; String user = "admin";
        //String url = "jdbc:postgresql://localhost:5432/postgres"; String user = "postgres";
        String password = "admin";
        //insert(url, user, password);
        PostgreSQL.select(url, user, password);
    }

    @Test
    public void insertTest() {
        String url = "jdbc:postgresql://193.33.111.196:5431/postgres"; String user = "admin";
        //String url = "jdbc:postgresql://localhost:5432/postgres"; String user = "postgres";
        String password = "admin";
        //insert(url, user, password);
        PostgreSQL.insert(url, user, password);
    }
}
