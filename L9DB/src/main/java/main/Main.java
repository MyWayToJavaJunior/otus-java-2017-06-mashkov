package main;


import ru.otus.dbhelper.DbHelper;
import ru.otus.exceptions.MappingException;
import ru.otus.executor.Executor;
import ru.otus.models.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        //DbHelper.example();

        Connection connection = DbHelper.getConnection();
        Executor executor = new Executor(connection);

        //String query = "create table test (name  VARCHAR(10),age INTEGER)";

        UserDataSet userDataSet = new UserDataSet("anton", 33);


        try {
            executor.execUpdate("create table if not exists users (id  bigserial not null, name varchar(256), age int not null, primary key (id))");
            executor.save(userDataSet);
        } catch (SQLException | MappingException e) {
            e.printStackTrace();
        } finally {
            try {
                //executor.execUpdate("drop table users");
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
