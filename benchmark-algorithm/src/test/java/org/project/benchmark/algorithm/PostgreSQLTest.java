package org.project.benchmark.algorithm;


import com.project.benchmark.algorithm.PostgreSQL;
import org.junit.Test;

public class PostgreSQLTest {
    @Test
    public void selectTest() {
        String url = "jdbc:postgresql://{host:port}/{dbName}"; String user = "{user}";
        //String url = "jdbc:postgresql://localhost:{port}/{dbName}"; String user = "{user}";
        String password = "admin";
        //insert(url, user, password);
        PostgreSQL.select(url, user, password);
    }

    @Test
    public void insertTest() {
        String url = "jdbc:postgresql://{host:port}/{dbName}"; String user = "{user}";
        //String url = "jdbc:postgresql://localhost:{port}/{dbName}"; String user = "{user}";
        String password = "admin";
        //insert(url, user, password);
        PostgreSQL.insert(url, user, password);
    }
}
