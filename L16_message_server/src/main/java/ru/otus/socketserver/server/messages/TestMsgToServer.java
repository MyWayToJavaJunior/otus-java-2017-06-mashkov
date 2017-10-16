package ru.otus.socketserver.server.messages;

import ru.otus.socketserver.common.messages.Msg;
import ru.otus.socketserver.common.messages.MsgToServer;

import java.util.Optional;

/**
 * @autor slonikmak on 11.10.2017.
 */
public class TestMsgToServer extends MsgToServer {

    public TestMsgToServer(Class<?> klass) {
        super(klass);
    }

    @Override
    public Optional<Msg> exec() {
        System.out.println("exec");
        return Optional.of(new TestAnswerToClient(TestAnswerToClient.class));
    }
}
