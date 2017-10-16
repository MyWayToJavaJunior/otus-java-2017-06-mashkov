package ru.otus.socketserver.server.messages;

import ru.otus.socketserver.common.messages.Msg;
import ru.otus.socketserver.common.messages.MsgToServer;

import java.util.Optional;

/**
 * @autor slonikmak on 12.10.2017.
 */
public class SetNameMsg extends MsgToServer {
    private String name;

    protected SetNameMsg(Class<?> klass) {
        super(klass);
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public Optional<Msg> exec() {
        channelMessages().setName(name);
        return Optional.empty();
    }
}
