package ru.otus.socketserver.common.socket;

import ru.otus.socketserver.common.messages.Msg;

import java.io.IOException;

/**
 * Created by tully.
 */
public interface MsgClient {
    void send(Msg msg);

    Msg pool();

    Msg take() throws InterruptedException;

    void close() throws IOException;
}
