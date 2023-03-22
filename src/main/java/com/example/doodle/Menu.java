package com.example.doodle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Menu extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            //Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Image icon = new Image("DoodleJumpIcon.png");
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("Doodle Jump");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) { Application.launch(); }
}
