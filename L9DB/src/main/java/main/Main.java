package main;


import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Driver driver = new Driver();
        try {
            DriverManager.registerDriver(driver);
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/myBase","anton", "anton");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE 'newTable' if NOT EXISTS ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
