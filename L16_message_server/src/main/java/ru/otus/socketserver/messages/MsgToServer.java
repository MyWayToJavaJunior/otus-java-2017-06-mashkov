package ru.otus.socketserver.messages;


public abstract class MsgToServer extends Msg {

    protected MsgToServer(Class<?> klass) {
        super(klass);
    }

}
