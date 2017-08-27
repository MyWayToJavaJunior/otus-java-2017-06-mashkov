package ru.otus.cachedDBservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.dbhelper.DbHelper;
import ru.otus.executor.Executor;
import ru.otus.models.UserDataSet;
import ru.otus.simplecache.CacheBuilder;
import ru.otus.simplecache.SimpleCache;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CachedDbServiceTest {
    CachedDbService dbService;
    SimpleCache<Long, UserDataSet> cache;
    Executor executor;

    @BeforeEach
    void setUp(){
        executor = new Executor(DbHelper.getConnection());
        try {
            executor.execUpdate("create table if not exists users2 (id  bigserial not null, name varchar(256), age int not null, primary key (id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cache = new CacheBuilder<Long, UserDataSet>()
                .getEternalCache(3)
                .setExtractor(UserDataSet::getId)
                .build();
        dbService = new CachedDbService(cache);

        try {
            executor.execUpdate("create table if not exists users2 (id  bigserial not null, name varchar(256), age int not null, primary key (id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @AfterEach
    void tearDown(){
        try {
            executor.execUpdate("drop table users2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void save() {
        UserDataSet bob = new UserDataSet("Bob", 21);
        UserDataSet bill = new UserDataSet("Bill", 22);
        UserDataSet ben = new UserDataSet("Ben", 23);
        UserDataSet john = new UserDataSet("John", 24);
        dbService.save(bob);
        dbService.save(bill);
        dbService.save(ben);
        dbService.save(john);

        assertEquals("Bill", cache.get(bill.getId()).orElse(null).getName());
        assertEquals(null, cache.get(bob.getId()).orElse(null));


    }
    @Test
    void read() {
        UserDataSet bob = new UserDataSet("Bob", 21);
        UserDataSet bill = new UserDataSet("Bill", 22);
        UserDataSet ben = new UserDataSet("Ben", 23);
        UserDataSet john = new UserDataSet("John", 24);
        dbService.save(bob);
        dbService.save(bill);
        dbService.save(ben);
        dbService.save(john);

        dbService.read(ben.getId());
        assertEquals(1, cache.getHitCount());
        dbService.read(bob.getId());
        assertEquals(1, cache.getMissCount());
    }


}