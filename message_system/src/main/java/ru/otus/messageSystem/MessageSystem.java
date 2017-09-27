package ru.otus.messageSystem;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

/**
 * @author tully
 */
public final class MessageSystem {
    private static final Logger LOGGER = Logger.getLogger(MessageSystem.class.getName());

    private static final int DEFAULT_STEP_TIME = 10;

    private final Map<Address, ConcurrentLinkedQueue<Message>> messagesMap = new HashMap<>();
    private final Map<Address, Addressee> addresseeMap = new HashMap<>();
    private boolean isStart = false;

    public void addAddressee(Addressee addressee) {
        addresseeMap.put(addressee.getAddress(), addressee);
        messagesMap.put(addressee.getAddress(), new ConcurrentLinkedQueue<>());
    }

    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void start() {
        if (isStart) return;
        isStart = true;
        LOGGER.info("START MESSAGE SYSTEM");
        for (Map.Entry<Address, Addressee> entry : addresseeMap.entrySet()) {
            new Thread(() -> {
                while (true) {

                    ConcurrentLinkedQueue<Message> queue = messagesMap.get(entry.getKey());
                    while (!queue.isEmpty()) {
                        Message message = queue.poll();
                        message.exec(entry.getValue());
                    }
                    try {
                        Thread.sleep(MessageSystem.DEFAULT_STEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
