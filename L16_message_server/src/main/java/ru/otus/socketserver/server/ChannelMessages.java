package ru.otus.socketserver.server;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor slonikmak on 12.10.2017.
 */
public class ChannelMessages {
    final SocketChannel channel;
    final List<String> messages = new ArrayList<>();
    private String name = "anonymous";
    //private final MessageServer server;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ChannelMessages(SocketChannel channel) {
        this.channel = channel;
    }

}
