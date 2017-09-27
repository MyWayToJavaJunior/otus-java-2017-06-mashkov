package ru.otus.messageserver.messages.db;

import ru.otus.interfaces.DBService;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;

import javax.websocket.Session;


/**
 * @autor slonikmak on 27.09.2017.
 */
public class GetInfoMsg extends MsgToDB {
    private final MessageSystem messageSystem;
    private final Session session;

    public GetInfoMsg(MessageSystem messageSystem, Address from, Address to, Session session) {
        super(from, to);
        this.session = session;
        this.messageSystem = messageSystem;
    }

    @Override
    public void exec(DBService dbService) {
        messageSystem.sendMessage(new GetInfoAnswer(getTo(), getFrom(), dbService.getLocalStatus(), session));
    }
}
