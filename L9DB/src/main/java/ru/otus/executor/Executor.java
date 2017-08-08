package ru.otus.executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    private Connection connection;

    public Executor(Connection connection){
        this.connection = connection;
    }

    public int execUpdate(String query){

        try (Statement stmt = connection.createStatement()){
            stmt.executeUpdate(query);
            return stmt.getUpdateCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
