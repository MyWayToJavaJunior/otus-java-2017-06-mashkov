package ru.otus.socketclient.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.otus.socketclient.client.ClientMain;
import ru.otus.socketserver.common.socket.SocketMsgClient;

/**
 * @autor slonikmak on 12.10.2017.
 */
public class ChatController {
    private SocketMsgClient socketClient;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea chatField;

    @FXML
    private TextField messageField;

    @FXML
    private Label hitLabel;

    @FXML
    private Label missLabel;

    @FXML
    void sendMessage(ActionEvent event) {

    }

    @FXML
    void setName(ActionEvent event) {

    }

    public void initialize(){
        try {
            socketClient = new ClientMain().start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
