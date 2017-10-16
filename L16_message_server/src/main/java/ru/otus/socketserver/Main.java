package ru.otus.socketserver;

import ru.otus.socketserver.server.MessageServer;

/**
 * @autor slonikmak on 10.10.2017.
 */
public class Main {
    public static void main(String[] args) {
        MessageServer server = new MessageServer();
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
