package ru.otus.socketserver.server;

import org.springframework.stereotype.Component;
import ru.otus.socketserver.messages.Msg;
import ru.otus.socketserver.socket.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChannelManager {

    private final Map<String, List<ChannelMessages>> channelMessages;

    ChannelManager(){
        channelMessages = new ConcurrentHashMap<>();
    }

    public void addChannelMessage(String type, ChannelMessages channel){
        List<ChannelMessages> channels = channelMessages.get(type);
        if (channels == null){
            ArrayList<ChannelMessages> list = new ArrayList<>();
            list.add(channel);
            channelMessages.put(type, list);
        } else {
            channels.add(channel);
        }
    }

    public Optional<ChannelMessages> getChannel(Address address){
        List<ChannelMessages> channels = channelMessages.get(address.getName());
        if (address.getRemoteAddress()!=null){
            return channels.stream().filter(c->c.getAddress().getRemoteAddress().equals(address.getRemoteAddress())).findFirst();
        }
        return channels.stream().findFirst();
    }

    public void sendMessage(Msg msg){

    }


}
