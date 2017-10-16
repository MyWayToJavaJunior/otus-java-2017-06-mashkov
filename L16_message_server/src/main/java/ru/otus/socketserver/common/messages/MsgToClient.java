package ru.otus.socketserver.common.messages;

public abstract class MsgToClient extends Msg {

    protected MsgToClient(Class<?> klass) {
        super(klass);
    }
}
