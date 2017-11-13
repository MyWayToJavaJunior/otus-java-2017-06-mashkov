package ru.otus.socketserver.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.otus.socketserver.server.ChannelManager;
import ru.otus.socketserver.socket.Address;
import ru.otus.socketserver.socket.ClientType;
import ru.otus.socketserver.server.ChannelMessages;

import java.nio.channels.SocketChannel;


/**
 * @autor slonikmak on 12.10.2017.
 */
@Component
@Scope("prototype")
public class RegisterMsg extends MsgToServer{


    public RegisterMsg() {
        super(RegisterMsg.class);
    }


    @Override
    public void exec() {
    }
}
