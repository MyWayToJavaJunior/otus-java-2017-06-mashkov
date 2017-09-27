package ru.otus.messageserver.messages.frontend;

import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Addressee;
import ru.otus.messageSystem.Message;
import ru.otus.messageserver.messages.app.FrontendService;
import ru.otus.messageserver.messages.app.MessageSystemContext;
import ru.otus.messageserver.messages.db.GetInfoMsg;

import javax.annotation.PostConstruct;
import javax.websocket.Session;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @autor slonikmak on 27.09.2017.
 */
public class FrontendServiceImpl implements FrontendService, Addressee {
    private final static Logger LOGGER = Logger.getLogger(FrontendServiceImpl.class.getName());

    private final Address address;
    private final MessageSystemContext context;

    public FrontendServiceImpl(Address address, MessageSystemContext context) {
        this.address = address;
        this.context = context;
    }

    @Override
    @PostConstruct
    public void init() {
        LOGGER.info("INIT FRONTEND");
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void handleRequest(Session session) {
        Message message = new GetInfoMsg(context.getMessageSystem(), getAddress(), context.getDbAddress(), session);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void sendMessage(String message, Session session) {
        try {
            session.getBasicRemote().sendText("MESSAGE FROM SERVER:\n" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
