package com.example.doodle.view.fxmlcontrollers;

import com.example.doodle.model.classes.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends Source implements Initializable {

    @FXML
    private ImageView spaceShip;

    @FXML
    private Polygon lightFromShip;

    @FXML
    private ImageView doodler;

    private void moveItem(Node node) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(node);
        translate.setDuration(Duration.millis(2500));
        translate.setByX((float) 60);
        translate.setByY((float) 30);
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setAutoReverse(true);
        translate.play();
    }

    private long lastTime = 0;

    private int index = 0;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        moveItem(spaceShip);
        moveItem(lightFromShip);
        Player player = new Player();
        player.setYPos((float) doodler.getY());
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(doodler);
        translate.setDuration(Duration.millis(1));
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (++index == 4) {
                    lightFromShip.setOpacity(lightFromShip.getOpacity() == 0.43 ? 0 : 0.43);
                    index = 0;
                }
                float curYPos = Player.getLegPosPlayer(player.getCurYPosition());
                player.changePositionY();
                if (now - lastTime >= 7_000_000) {
                    translate.setByY(Player.getLegPosPlayer(player.getCurYPosition()) - curYPos);
                    translate.play();
                    lastTime = now;
                }
                if (Player.getLegPosPlayer(player.getCurYPosition()) > 400) {
                    player.setDefaultVelocity();
                }
            }
        };
        timer.start();
    }

    public void startPlay() {
        notify("StartPlay");
    }

    public void scoreButton() {
        notify("Score");
    }
}
