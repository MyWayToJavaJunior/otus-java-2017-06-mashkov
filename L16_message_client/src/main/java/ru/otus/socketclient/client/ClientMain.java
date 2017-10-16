package ru.otus.socketclient.client;



import ru.otus.socketserver.common.messages.Msg;
import ru.otus.socketserver.common.socket.SocketMsgClient;
import ru.otus.socketserver.server.messages.TestMsgToServer;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tully.
 */
public class ClientMain {
    private static final Logger logger = Logger.getLogger(ClientMain.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private static final int PAUSE_MS = 5000;
    private static final int MAX_MESSAGES_COUNT = 5;

    public static void main(String[] args) throws Exception {
        new ClientMain().start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public SocketMsgClient start() throws Exception {
        String pid = ManagementFactory.getRuntimeMXBean().getName();

        SocketMsgClient client = new ManagedMsgSocketClient(HOST, PORT);
        client.init();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    //Обработка входящих сообщений
                    Object msg = client.take();
                    System.out.println("Message received: " + msg.toString());
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });

        new Thread(()->{
            int count = 0;
            while (count < MAX_MESSAGES_COUNT) {
                Msg msg = new TestMsgToServer(TestMsgToServer.class);
                client.send(msg);
                System.out.println("Message send: " + msg.toString());
                try {
                    Thread.sleep(PAUSE_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }

        }).start();
        //При выходе вызвать
        //client.close();
        //executorService.shutdown();

        return client;
    }

}
