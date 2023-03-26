package com.example.doodle;

import com.example.doodle.model.classes.Platform;
import com.example.doodle.model.classes.PlatformsQueue;
import com.example.doodle.model.classes.Player;
import com.example.doodle.model.classes.StartData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DoodleJumpView {
    private Scene scene;
    private final Stage stage;
    Parent root;

    private ImageView doodlerView;
    private final Text score;

    private long curScore = 0;

    public DoodleJumpView(Stage primaryStage) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("field.fxml")));
            scene = new Scene(root);
            stage = primaryStage;
            score = new Text(25, 35, "0");
            score.setFont(new Font("Comic Sans MS", 25));
            ((Pane)root).getChildren().add(score);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPlayer() {
        doodlerView = new ImageView(Player.rightDoodlerImage);
        doodlerView.setX(StartData.startXPos);
        doodlerView.setY(StartData.startYPos);
        ((Pane)root).getChildren().add(doodlerView);
    }

    public void draw(Player player, PlatformsQueue platformsQueue, long newScore) {
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.LEFT) {
                doodlerView.setImage(Player.leftDoodlerImage);
                player.changeVelocityXL();
            } else if (keyCode == KeyCode.RIGHT) {
                doodlerView.setImage(Player.rightDoodlerImage);
                player.changeVelocityXR();
            }
        });
        scene.setOnKeyReleased(event -> player.changeVelocityXToZero());
        drawDoodler(player);
        drawPlatformsQueue(platformsQueue);
        for (int i = 0; i < newScore - curScore; i++) {
            score.setText(String.valueOf(curScore + i));
        }
        curScore = newScore;
    }

    private void drawDoodler(Player player) {
        doodlerView.setX(player.getCurXPosition());
        doodlerView.setY(player.getCurYPosition());
        doodlerView.toFront();
    }

    private void drawPlatformsQueue(PlatformsQueue platformsQueue) {
        for (var platform : platformsQueue.platformList) {
            if (platform.platformView == null) {
                platform.platformView = new ImageView(Platform.platformImage);
                ((Pane)root).getChildren().add(platform.platformView);
            }
            if (platform.getSpringStatus()) {
                if (platform.spring.springView == null) {
                    platform.spring.springView = new ImageView(Platform.Spring.springImage);
                    ((Pane)root).getChildren().add(platform.spring.springView);
                }
                if (platform.spring.isHasJumped()) {
                    platform.spring.springView.setImage(Platform.Spring.springLongImage);
                    platform.spring.springView.setY(platform.position.y() - 30);
                } else {
                    platform.spring.springView.setY(platform.position.y() - Platform.Spring.springUpPlatform);
                }
                platform.spring.springView.setX(platform.spring.getXPos());
            }
            platform.platformView.setX(platform.position.x());
            platform.platformView.setY(platform.position.y());
            platform.platformView.toBack();
        }
    }

    public void loose() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("looseMenu.fxml")));
        scene = new Scene(root);
        Text resultScore = new Text(150, 280, "your score: " + score.getText());
        resultScore.setFont(new Font("Comic Sans MS", 25));
        ((Pane)root).getChildren().add(score);
        ((Pane)root).getChildren().add(resultScore);
        stage.setScene(scene);
    }
}
