package ru.otus.socketclient.messages;

import ru.otus.socketserver.messages.MsgToServer;
import ru.otus.socketserver.socket.Address;

public class SimpleMsgToClient extends MsgToServer {

    String message;


    public SimpleMsgToClient(Class<?> klass, String message) {
        super(klass);
        this.message = message;
        Address to = new Address();
        to.setName("simple");
        setAddressTo(to);
    }

    @Override
    public void exec() {
        System.out.println("msg!!! "+message);
    }
}
