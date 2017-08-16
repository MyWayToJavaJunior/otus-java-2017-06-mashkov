package ru.otus.DBService;

import ru.otus.dao.UserDataSetDao;
import ru.otus.dbhelper.DbHelper;
import ru.otus.exceptions.MappingException;
import ru.otus.interfaces.DBService;
import ru.otus.models.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBServiceImpl implements DBService {
    private Connection connection;

    public DBServiceImpl(){
        connection = DbHelper.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLocalStatus() {
        try {
            if (connection.isClosed()) return "I am closed";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "I am working";
    }

    @Override
    public void save(UserDataSet dataSet) {
        UserDataSetDao dao = new UserDataSetDao(connection);
        try {
            dao.save(dataSet);
            connection.commit();
        } catch (MappingException | SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public UserDataSet read(long id) {
        UserDataSetDao dao = new UserDataSetDao(connection);
        UserDataSet result = null;
        try {
            result = dao.read(id);
            connection.commit();
        } catch (MappingException | SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public UserDataSet readByName(String name) {
        UserDataSetDao dao = new UserDataSetDao(connection);
        UserDataSet result = null;
        try {
            result = dao.readByName(name);
            connection.commit();
        } catch (MappingException | SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<UserDataSet> readAll() {
        UserDataSetDao dao = new UserDataSetDao(connection);
        List<UserDataSet> result = null;
        try {
            result = dao.readAll();
            connection.commit();
        } catch (MappingException | SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
