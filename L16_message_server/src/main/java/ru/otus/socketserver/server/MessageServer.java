package ru.otus.socketserver.server;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.socketserver.Config;
import ru.otus.socketserver.messages.Msg;
import ru.otus.socketserver.messages.RegisterMsg;
import ru.otus.socketserver.socket.Address;
import ru.otus.socketserver.socket.SocketMsgClient;

import java.applet.AppletContext;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageServer {
    private static final Logger logger = Logger.getLogger(MessageServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int ECHO_DELAY = 100;
    private static final int CAPACITY = 256;
    private static final String MESSAGES_SEPARATOR = "\n\n";

    private final Gson gson;

    private final ExecutorService executor;
    //private final Map<String, ChannelMessages> channelMessages;
    @Autowired
    ChannelManager channelManager;

    public MessageServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        gson = new Gson();
        //channelMessages = new ConcurrentHashMap<>();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws Exception {
        //executor.submit(this::echo);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Config.class);
        context.refresh();
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress("localhost", PORT));

            serverSocketChannel.configureBlocking(false); //non blocking mode
            int ops = SelectionKey.OP_ACCEPT;
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, ops, null);

            logger.info("Started on port: " + PORT);

            while (true) {
                selector.select();//blocks
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    try {
                        if (key.isAcceptable()) {
                            SocketChannel channel = serverSocketChannel.accept(); //non blocking accept
                            String remoteAddress = channel.getRemoteAddress().toString();
                            System.out.println("Connection Accepted: " + remoteAddress);

                            channel.configureBlocking(false);
                            channel.register(selector, SelectionKey.OP_READ);

                            //channelManager.addChannelMessage(remoteAddress, new ChannelMessages(channel));

                        } else if (key.isReadable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            String remoteAddress = channel.getRemoteAddress().toString();
                            ByteBuffer buffer = ByteBuffer.allocate(CAPACITY);
                            int read = channel.read(buffer);
                            if (read != -1) {
                                String result = new String(buffer.array()).trim();
                                System.out.println("Message received: " + result + " from: " + channel.getRemoteAddress());

                                //тут обработаем сообщение, получив его из json
                                //и добавим ответ в канал
                                if (!result.equals("")){
                                    Msg msg = SocketMsgClient.getMsgFromJSON(result, context);

                                    if (msg.getClassName().equals(RegisterMsg.class.getName())){
                                        Address address = msg.getAddressFrom();
                                        channelManager.addChannelMessage(address.getName(), new ChannelMessages(channel, address));
                                    } else {
                                        Address to = msg.getAddressTo();
                                        msg.getAddressFrom().setRemoteAddress(remoteAddress);
                                        channelManager.getChannel(to).ifPresent(c->{
                                            sendMessageToChannel(msg, c.channel);
                                        });


                                        //sendMessageToChannel();
                                        //msg.setChannelMessages(channelMessages.get(channel.getRemoteAddress().toString()));
                                    /*Optional<Msg> optional = msg.exec();
                                    optional.ifPresent(m->{
                                        try {
                                            channelManager.getChannel(channel.getRemoteAddress().toString()).messages.add(new Gson().toJson(m));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    });*/
                                    }


                                }

                                //System.out.println("Message from json:"+result);
                                //channelMessages.get(channel.getRemoteAddress().toString()).messages.add(result);
                            } else {
                                key.cancel();
                                //String remoteAddress = channel.getRemoteAddress().toString();
                                //channelMessages.remove(remoteAddress);
                                System.out.println("Connection closed, key canceled");
                            }
                        }
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, e.getMessage());
                    } finally {
                        iterator.remove();
                    }
                }
            }
        }
    }

    /*@SuppressWarnings("InfiniteLoopStatement")
    private Object echo() throws InterruptedException {
       while (true) {
            for (Map.Entry<String, ChannelMessages> entry : channelMessages.entrySet()) {
                ChannelMessages channelMessages = entry.getValue();
                if (channelMessages.channel.isConnected()) {
                    channelMessages.messages.forEach(message -> {
                        System.out.println("Echoing message to: " + entry.getKey());
                        sendMessageToChannel(message, channelMessages.channel);
                    });
                    channelMessages.messages.clear();
                }
            }
            Thread.sleep(ECHO_DELAY);
        }
    }*/

    public void sendMessageToChannel(String msg, SocketChannel channel){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(CAPACITY);
            buffer.put(msg.getBytes());
            buffer.put(MESSAGES_SEPARATOR.getBytes());
            buffer.flip();
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void sendMessageToChannel(Msg msg, SocketChannel channel){
        String message = gson.toJson(msg);
        sendMessageToChannel(message, channel);
    }
}
