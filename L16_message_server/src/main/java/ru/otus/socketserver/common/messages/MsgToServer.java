package ru.otus.socketserver.common.messages;

import ru.otus.socketserver.server.ChannelMessages;

import java.util.Optional;

public abstract class MsgToServer extends Msg{
    private ChannelMessages channelMessages;

    protected MsgToServer(Class<?> klass) {
        super(klass);
    }

    public abstract Optional<Msg> exec();

    public ChannelMessages getChannelMessages() {
        return channelMessages;
    }

    public void setChannelMessages(ChannelMessages channelMessages) {
        this.channelMessages = channelMessages;
    }
    public ChannelMessages channelMessages(){
        return channelMessages;
    }
}
