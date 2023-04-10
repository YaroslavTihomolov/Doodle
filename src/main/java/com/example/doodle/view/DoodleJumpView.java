package com.example.doodle.view;

import com.example.doodle.model.classes.*;
import com.example.doodle.model.classes.platforms.platformTypes.DisappearingPlatform;
import com.example.doodle.model.classes.platforms.platformTypes.PlatformWithSpring;
import com.example.doodle.model.classes.platforms.PlatformsQueue;
import com.example.doodle.view.fxmlcontrollers.Source;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DoodleJumpView extends Source {
    private Scene scene;
    private final Stage stage;
    Parent root;

    private ImageView doodlerView;
    private Text score;

    private long curScore = 0;

    public void setField() {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/doodle/field.fxml")));
            scene = new Scene(root);
            score = new Text(25, 35, "0");
            score.setFont(new Font("Comic Sans MS", 25));
            ((Pane)root).getChildren().add(score);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DoodleJumpView() {
        stage = new Stage();
        stage.setResizable(false);
    }

    public void openScoreTable() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DoodleJumpView.class.getResource("/com/example/doodle/score.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            ScoreTable scoreTable = new ScoreTable();
            var it = scoreTable.getIterator();
            int index = 0;
            while (it.hasNext()) {
                Text text = new Text(100, 180 + index++ * 35, index + ". Doodler: " + it.next());
                text.setFont(new Font("Comic Sans MS", 30));
                ((Pane) root).getChildren().add(text);
            }
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DoodleJumpView.class.getResource("/com/example/doodle/menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Image icon = new Image("DoodleJumpIcon.png");
            stage.getIcons().add(icon);
            stage.setTitle("Doodle Jump");
            stage.setScene(scene);
            ScoreTable scoreTable = new ScoreTable();
            scoreTable.addData();
            stage.show();
        } catch (Exception e) {
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
            notify("Button", keyCode);
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
        doodlerView.setImage(player.getCurImage());
        doodlerView.setX(player.getCurXPosition());
        doodlerView.setY(player.getCurYPosition());
        doodlerView.toFront();
    }


    private void drawPlatformWithSpring(PlatformWithSpring platformWithSpring) {
        if (platformWithSpring.getSpringStatus()) {
            if (platformWithSpring.spring.springView == null) {
                platformWithSpring.spring.springView = new ImageView(PlatformWithSpring.Spring.springImage);
                ((Pane)root).getChildren().add(platformWithSpring.spring.springView);
            }
            if (platformWithSpring.spring.isHasJumped()) {
                platformWithSpring.spring.springView.setImage(PlatformWithSpring.Spring.springLongImage);
                platformWithSpring.spring.springView.setY(platformWithSpring.position.y() - 30);
            } else {
                platformWithSpring.spring.springView.setY(platformWithSpring.position.y() - PlatformWithSpring.Spring.springUpPlatform);
            }
            platformWithSpring.spring.springView.setX(platformWithSpring.spring.getXPos());
        }
    }


    private void drawDisappearingPlatform(DisappearingPlatform disappearingPlatform) {
        if (disappearingPlatform.getTouchStatus()) {
            Parent parent = disappearingPlatform.platformView.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(disappearingPlatform.platformView);
            } else if (parent instanceof Group) {
                ((Group) parent).getChildren().remove(disappearingPlatform.platformView);
            }
        }
    }


    private void drawPlatformsQueue(PlatformsQueue platformsQueue) {
        for (var platform : platformsQueue.platformList) {

            if (platform.platformView == null) {
                platform.platformView = new ImageView(platform.getImage());
                ((Pane)root).getChildren().add(platform.platformView);
            }

            if (platform.getClass().getName().equals("com.example.doodle.model.classes.platforms.platformTypes.PlatformWithSpring")) {
                drawPlatformWithSpring((PlatformWithSpring) platform);
            }

            if (platform.getClass().getName().equals("com.example.doodle.model.classes.platforms.platformTypes.DisappearingPlatform")) {
                drawDisappearingPlatform((DisappearingPlatform) platform);
            }

            platform.platformView.setX(platform.position.x());
            platform.platformView.setY(platform.position.y());
            platform.platformView.toBack();
        }
    }


    public void loose() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/doodle/looseMenu.fxml")));
        scene = new Scene(root);
        Text resultScore = new Text(150, 280, "your score: " + score.getText());
        resultScore.setFont(new Font("Comic Sans MS", 25));
        ((Pane)root).getChildren().add(score);
        ((Pane)root).getChildren().add(resultScore);
        stage.setScene(scene);
    }
}
