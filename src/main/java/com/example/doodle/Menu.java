package com.example.doodle;

import com.example.doodle.model.classes.ScoreTable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Menu extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Image icon = new Image("DoodleJumpIcon.png");
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("Doodle Jump");
            primaryStage.setScene(scene);
            ScoreTable scoreTable = new ScoreTable();
            scoreTable.addData();
            primaryStage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) { Application.launch(); }
}
