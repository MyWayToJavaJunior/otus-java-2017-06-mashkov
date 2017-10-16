package ru.otus.socketclient.app;/**
 * @autor slonikmak on 12.10.2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainClientApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/chat.fxml"));
            primaryStage.setScene(new Scene(parent));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
