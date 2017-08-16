package ru.otus.dao;

import ru.otus.exceptions.MappingException;
import ru.otus.executor.Executor;
import ru.otus.models.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDataSetDao {

    private Executor executor;

    public UserDataSetDao(Connection connection){
        this.executor = new Executor(connection);
    }

    public void save(UserDataSet userDataSet) throws MappingException, SQLException {
        executor.save(userDataSet);
    }

    public UserDataSet read(long id) throws MappingException, SQLException {
       return executor.load(id, UserDataSet.class);
    }

    public UserDataSet readByName(String name) throws MappingException, SQLException {
        return executor.loadByName(name, UserDataSet.class);
    }

    public List<UserDataSet> readAll() throws MappingException, SQLException {
        return executor.loadAll(UserDataSet.class);
    }
}
