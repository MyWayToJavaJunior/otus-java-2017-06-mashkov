package ru.otus.messageserver.messages.db;

import ru.otus.messageSystem.Address;
import ru.otus.messageserver.messages.app.FrontendService;
import ru.otus.messageserver.messages.frontend.MsgToFrontend;

import javax.websocket.Session;

/**
 * @autor slonikmak on 27.09.2017.
 */
public class GetInfoAnswer extends MsgToFrontend {

    private final String message;
    private final Session session;

    public GetInfoAnswer(Address from, Address to, String message, Session session) {
        super(from, to);
        this.message = message;
        this.session = session;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.sendMessage(message, session);
    }
}
