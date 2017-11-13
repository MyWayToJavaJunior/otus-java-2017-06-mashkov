package ru.otus.socketserver.server;

import ru.otus.socketserver.socket.Address;
import ru.otus.socketserver.socket.ClientType;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor slonikmak on 12.10.2017.
 */
public class ChannelMessages {

    final SocketChannel channel;
    final List<String> messages = new ArrayList<>();

    private Address address;

    ChannelMessages(SocketChannel channel) {
        this.channel = channel;
    }
    ChannelMessages(SocketChannel channel, Address address) {
        this.channel = channel;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
