package ru.otus.messageserver.messages.db;


import ru.otus.interfaces.DBService;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Addressee;
import ru.otus.messageSystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToDB extends Message {


    public MsgToDB(Address from, Address to) {
        super(from, to);

    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        }
    }

    public abstract void exec(DBService dbService);
}
