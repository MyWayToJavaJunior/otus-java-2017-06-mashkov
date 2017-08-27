package ru.otus.cachedDBservice;

import ru.otus.DBService.DBServiceImpl;
import ru.otus.interfaces.DBService;
import ru.otus.models.UserDataSet;
import ru.otus.simplecache.CacheException;
import ru.otus.simplecache.SimpleCache;

import java.util.List;
import java.util.Optional;

public class CachedDbService  implements DBService{
    private DBService dbService;
    private SimpleCache<Long, UserDataSet> userDataSetCache;

    CachedDbService(SimpleCache<Long, UserDataSet> cache){
        dbService = new DBServiceImpl();
        userDataSetCache = cache;

    }

    @Override
    public String getLocalStatus() {
        return dbService.getLocalStatus();
    }

    @Override
    public void save(UserDataSet dataSet){
        dbService.save(dataSet);
        try {
            userDataSetCache.put(dataSet);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDataSet read(long id) {
        Optional<UserDataSet> result = userDataSetCache.get(id);
        if (result.isPresent()) return result.get();
        else {
            UserDataSet dataSet = dbService.read(id);
            try {
                userDataSetCache.put(dataSet);
            } catch (CacheException e) {
                e.printStackTrace();
            }
            return dataSet;
        }

    }

    @Override
    public UserDataSet readByName(String name) {
        //как работать с кешем если он типизирован по id? Можно завести кеш, типизированный по name?
        UserDataSet dataSet = dbService.readByName(name);
        try {
            userDataSetCache.put(dataSet);
        } catch (CacheException e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    @Override
    public List<UserDataSet> readAll() {
        //как получить данные из кеша, а чего не хватает из базы?
        List<UserDataSet> list = dbService.readAll();
        list.forEach(userDataSet -> {
            try {
                userDataSetCache.put(userDataSet);
            } catch (CacheException e) {
                e.printStackTrace();
            }
        });
        return list;
    }

    @Override
    public void shutdown() {
        dbService.shutdown();
    }
}
