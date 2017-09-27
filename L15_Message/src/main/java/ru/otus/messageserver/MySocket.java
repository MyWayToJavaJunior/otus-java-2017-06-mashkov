package ru.otus.messageserver;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.messageSystem.MessageSystem;
import ru.otus.messageserver.messages.frontend.FrontendServiceImpl;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * @autor slonikmak on 25.09.2017.
 */
@ServerEndpoint("/websocket")
public class MySocket {

    @Autowired
    FrontendServiceImpl frontendService;
    @Autowired
    MessageSystem messageSystem;

    @Autowired
    Gson gson;

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket opened: " + session.getId());
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String txt, Session session) throws IOException {
        MsgFromWeb msg = gson.fromJson(txt, MsgFromWeb.class);
        if (msg.type.equals("info")) {
            frontendService.handleRequest(session);
        } else {
            sessions.forEach(s -> {
                try {
                    s.getBasicRemote().sendText(msg.msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        sessions.remove(session);
        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
    }

    @PostConstruct
    void init() {
        /**FIXME: приходиться стартовать при каждом соиденении (смотри fixme в @{@link MessageServer})*/
        messageSystem.start();
    }

    static class MsgFromWeb {
        String type;
        String msg;
    }

}
