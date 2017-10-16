package ru.otus.socketserver.server.messages;

import ru.otus.socketserver.common.messages.MsgToClient;

/**
 * @autor slonikmak on 12.10.2017.
 */
public class TestAnswerToClient extends MsgToClient{

    private String message = "Message from server";

    protected TestAnswerToClient(Class<?> klass) {
        super(klass);
    }
}
