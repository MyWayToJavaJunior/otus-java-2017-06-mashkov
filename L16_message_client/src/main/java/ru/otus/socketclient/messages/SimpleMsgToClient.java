package ru.otus.socketclient.messages;

import ru.otus.socketserver.messages.MsgToServer;
import ru.otus.socketserver.socket.Address;

public class SimpleMsgToClient extends MsgToServer {

    String message;


    public SimpleMsgToClient(String message) {
        super(SimpleMsgToClient.class);
        this.message = message;
        Address to = new Address();
        to.setName("simple");
        setAddressTo(to);
        Address from = new Address();
        from.setName("simple");
        setAddressFrom(from);
    }

    @Override
    public void exec() {
        System.out.println("msg!!! "+message);
    }
}
