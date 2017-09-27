package ru.otus.messageserver.messages.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;

/**
 * Created by tully.
 */
@Component
public class MessageSystemContext {
    private final MessageSystem messageSystem;

    @Autowired
    @Qualifier(value = "socketAddress")
    private Address frontAddress;

    @Autowired
    @Qualifier(value = "dbAddress")
    private Address dbAddress;

    @Autowired
    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }
}
