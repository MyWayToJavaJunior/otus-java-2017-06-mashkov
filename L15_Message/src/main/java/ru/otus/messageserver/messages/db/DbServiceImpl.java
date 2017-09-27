package ru.otus.messageserver.messages.db;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.cachedDBservice.CachedDbService;
import ru.otus.interfaces.DBService;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Addressee;
import ru.otus.messageserver.messages.app.MessageSystemContext;
import ru.otus.models.UserDataSet;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Logger;

/**
 * @autor slonikmak on 27.09.2017.
 */

public class DbServiceImpl implements DBService, Addressee {
    private static final Logger LOGGER = Logger.getLogger(DbServiceImpl.class.getName());

    @Autowired
    private CachedDbService dbService;

    private final Address address;
    private final MessageSystemContext context;

    public DbServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("INIT DB SERVICE");
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public String getLocalStatus() {
        return dbService.getLocalStatus();
    }

    @Override
    public void save(UserDataSet dataSet) {
        dbService.save(dataSet);
    }

    @Override
    public UserDataSet read(long id) {
        return dbService.read(id);
    }

    @Override
    public UserDataSet readByName(String name) {
        return dbService.readByName(name);
    }

    @Override
    public List<UserDataSet> readAll() {
        return dbService.readAll();
    }

    @Override
    public void shutdown() {
        dbService.shutdown();
    }

    @Override
    public Address getAddress() {
        return address;
    }

}
