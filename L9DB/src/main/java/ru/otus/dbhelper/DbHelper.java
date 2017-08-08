package ru.otus.dbhelper;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHelper {
    public static Connection getConnection(){
        try {
            DriverManager.registerDriver(new Driver());
            String url = "jdbc:postgresql://"+
                    "localhost:5432/"+
                    "myBase";

            return DriverManager.getConnection(url,"anton", "anton");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void example(){
        try (Connection connection = getConnection()){
            System.out.println("Connected to: "+connection.getMetaData().getURL());
            System.out.println("DB name: "+connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: "+connection.getMetaData().getDatabaseProductVersion());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
