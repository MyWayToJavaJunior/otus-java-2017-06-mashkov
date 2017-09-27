package ru.otus.messageserver.messages.app;

import javax.websocket.Session;

/**
 * Created by tully.
 */
public interface FrontendService {
    void init();

    void handleRequest(Session session);

    void sendMessage(String message, Session session);

}

