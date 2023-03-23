package com.example.doodle.controller;

import com.example.doodle.DoodleJumpView;
import com.example.doodle.Menu;
import com.example.doodle.model.DoodleJumpModel;
import com.example.doodle.model.classes.Player;
import com.example.doodle.model.classes.ScoreTable;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    private Stage stage;

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

    public void resetMenu(ActionEvent event) {
        try {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int index = 0;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        moveItem(spaceShip);
        moveItem(lightFromShip);
        Player player = new Player();
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
                if (curYPos > 940) {
                    player.setDefaultVelocity();
                }
                if (now - lastTime >= 7_000_000) {
                    translate.setByY(Player.getLegPosPlayer(player.getCurYPosition()) - curYPos);
                    translate.play();
                    lastTime = now;
                }
            }
        };
        timer.start();
    }

    public void startPlay(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        DoodleJumpView view = new DoodleJumpView(this.stage);
        DoodleJumpModel model = new DoodleJumpModel();
        DoodleJumpPresenter presenter = new DoodleJumpPresenter(model, view);
        try {
            presenter.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void scoreButton(ActionEvent event) {
        try {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("score.fxml"));
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
}
