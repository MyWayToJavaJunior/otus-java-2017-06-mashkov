package ru.otus.socketclient.messages;

import ru.otus.socketserver.common.messages.MsgToClient;

/**
 * @autor slonikmak on 12.10.2017.
 */
public class AnswerToClient extends MsgToClient {
    protected AnswerToClient(Class<?> klass) {
        super(klass);
        setBaseMsgPackage("ru.otus.socketclient.messages");
    }

}
