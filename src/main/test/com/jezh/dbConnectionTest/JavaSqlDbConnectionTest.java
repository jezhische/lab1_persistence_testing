package com.jezh.dbConnectionTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.annotation.Order;

import java.io.FileReader;
import java.sql.*;
import java.util.*;

public class JavaSqlDbConnectionTest {

    private String jdbcUrl;
    private String user;
    private String password;
    private String select_all_sql_from_user_profile = "select * from user_profile";

    private boolean isAppropriateDrivers;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileReader("src\\main\\resources\\application.properties"));
        jdbcUrl = properties.getProperty("jdbc.url");
        user = properties.getProperty("jdbc.username");
        password = properties.getProperty("jdbc.password");
    }

    @After
    public void tearDown() throws Exception {
        isAppropriateDrivers = false;
    }

    @Test
    public void printAvailableDrivers() throws Exception {
        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
        Iterator<Driver> loaderIterator = loader.iterator();
        Assert.assertTrue(loaderIterator.hasNext());
        loaderIterator.forEachRemaining(driver -> System.out.println(driver));
    }

    @Test
    public void isMySqlDriverExists() throws Exception {
        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
        Iterator<Driver> loaderIterator = loader.iterator();
        loaderIterator.forEachRemaining(driver -> {
            if (driver.getClass().getName().equals("com.mysql.jdbc.Driver")) isAppropriateDrivers = true;
        });
        Assert.assertTrue(isAppropriateDrivers);
    }

    @Test
    public void isHsqldbDriverExists() throws Exception {
        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
        Iterator<Driver> loaderIterator = loader.iterator();
        loaderIterator.forEachRemaining(driver -> {
            if (driver.getClass().getName().equals("org.hsqldb.jdbc.JDBCDriver")) isAppropriateDrivers = true;
        });
        Assert.assertTrue(isAppropriateDrivers);
    }

// test that there is "createDatabaseIfNotExist=true" property in db url
    @Test
    public void testCanCreateDbWithUrlProperties() throws Exception {
        Connection myConn = DriverManager.getConnection(jdbcUrl, user, password);
        Assert.assertTrue(myConn.isValid(0));
        myConn.close();
        Assert.assertTrue(myConn.isClosed());
    }

    @Test
    public void testDbConnection() throws Exception {
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306?useSSL=false", user, password);
        Assert.assertTrue(myConn.isValid(0));
        myConn.close();
        Assert.assertTrue(myConn.isClosed());
    }

    @Test
    public void testSchemaConnection() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        HashMap<Integer, String> userProfileMap = new HashMap();
        try {
            connection = DriverManager.getConnection(jdbcUrl, user, password);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(select_all_sql_from_user_profile);
            while (resultSet.next())
                userProfileMap.put(resultSet.getInt("id"), resultSet.getString("type"));
            connection.commit();

            Assert.assertTrue(userProfileMap.containsKey(1)&&userProfileMap.containsValue("USER"));

            Iterator<Map.Entry<Integer, String>> iterator = userProfileMap.entrySet().iterator();
            iterator.forEachRemaining(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
        } catch (SQLException e) {
            connection.rollback(); // throws SQLException
            System.out.println(e.getMessage() + ": SQLState: " + e.getSQLState());
        } finally {
            resultSet.close(); // throws SQLException
            statement.close(); // throws SQLException
            connection.close(); // throws SQLException
        }

    }
}
