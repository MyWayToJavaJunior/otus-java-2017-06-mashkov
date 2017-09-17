package ru.otus.cachedDBservice;

import ru.otus.DBService.DBServiceImpl;
import ru.otus.interfaces.DBService;
import ru.otus.models.UserDataSet;
import ru.otus.softcache.SimpleCache;


import java.util.List;
import java.util.Optional;

public class CachedDbService implements DBService {
    private DBService dbService;
    private SimpleCache<Long, UserDataSet> userDataSetCache;

    public CachedDbService(SimpleCache<Long, UserDataSet> cache) {
        dbService = new DBServiceImpl();
        userDataSetCache = cache;

    }

    @Override
    public String getLocalStatus() {
        return dbService.getLocalStatus();
    }

    @Override
    public void save(UserDataSet dataSet) {
        dbService.save(dataSet);
        userDataSetCache.put(dataSet.getId(), dataSet);

    }

    @Override
    public UserDataSet read(long id) {
        Optional<UserDataSet> result = userDataSetCache.get(id);
        if (result.isPresent()) return result.get();
        else {
            UserDataSet dataSet = dbService.read(id);
            userDataSetCache.put(dataSet.getId(), dataSet);

            return dataSet;
        }

    }

    @Override
    public UserDataSet readByName(String name) {
        //как работать с кешем если он типизирован по id? Можно завести кеш, типизированный по name?
        UserDataSet dataSet = dbService.readByName(name);

        if (dataSet!=null) userDataSetCache.put(dataSet.getId(), dataSet);

        return dataSet;
    }

    @Override
    public List<UserDataSet> readAll() {
        //как получить данные из кеша, а чего не хватает из базы?
        List<UserDataSet> list = dbService.readAll();
        list.forEach(userDataSet -> {
            userDataSetCache.put(userDataSet.getId(), userDataSet);

        });
        return list;
    }

    @Override
    public void shutdown() {
        dbService.shutdown();
    }
}
