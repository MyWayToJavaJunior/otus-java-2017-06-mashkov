package ru.otus.socketserver.server;

import ru.otus.socketserver.common.messages.MessageProcessor;
import ru.otus.socketserver.common.messages.Msg;
import ru.otus.socketserver.server.messages.SetNameMsg;
import ru.otus.socketserver.server.messages.TestMsgToServer;

import java.util.Optional;

/**
 * @autor slonikmak on 12.10.2017.
 */
public class ServerMessageProcessor implements MessageProcessor{
    @Override
    public Optional<Msg> process(Msg msg) {
        Optional<Msg> result = Optional.empty();

        switch (msg.getClass().getSimpleName()) {
            case "TestMsgToServer": result =  ((TestMsgToServer)msg).exec();
            break;
            case "SetNameMsg": ((SetNameMsg)msg).exec();

        }

        return result;
    }
}
