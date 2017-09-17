package ru.otus.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.dbhelper.DbHelper;
import ru.otus.executor.Executor;
import ru.otus.interfaces.DBService;
import ru.otus.models.UserDataSet;
import ru.otus.softcache.SimpleCache;

import java.sql.SQLException;

public class TestClass {

    @Autowired
    DBService dbService;
    @Autowired
    SimpleCache cache;

    public TestClass(){}

    public void configDb(){
            Executor executor = new Executor(DbHelper.getConnection());
            try {
                executor.execUpdate("drop table if exists users2");
                executor.execUpdate("create table if not exists users2 (id  bigserial not null, age int, name varchar(256), pass varchar(256),status varchar(256), primary key (id))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            UserDataSet user = new UserDataSet("anton", 29);
            user.setPass("admin");
            user.setStatus("admin");
            dbService.save(user);
    }
    public void startTestCache(){
        new Thread(() -> {
            int size = 100;

            while (true){
                for (int i = 10; i < size; i++) {
                    cache.put((long) i, new UserDataSet("user"+i, i));
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 10; i < size; i++) {

                    cache.get((long)i);
                }
            }
        }).start();
    }

}
