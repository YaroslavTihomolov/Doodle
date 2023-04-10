package com.example.doodle.controller;

import com.example.doodle.view.DoodleJumpView;
import com.example.doodle.model.DoodleJumpModel;
import com.example.doodle.model.classes.platforms.PlatformsQueue;
import com.example.doodle.model.classes.Player;
import com.example.doodle.model.classes.ScoreTable;
import com.example.doodle.view.fxmlcontrollers.Source;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

import java.io.*;

public class DoodleJumpController {
    private final DoodleJumpModel model;
    private final DoodleJumpView view;

    Player player;
    PlatformsQueue platformsQueue;
    ScoreTable scoreTable;

    public DoodleJumpController(DoodleJumpModel modelReference, DoodleJumpView viewReference) {
        model = modelReference;
        view = viewReference;
        scoreTable = new ScoreTable();
        Source.addPlayer(this);
    }

    private boolean gameStatus = true;

    private void setDefaultSettings() {
        view.setField();
        view.setPlayer();
        model.setDefaultSettings();
        gameStatus = true;
    }

    private void startGame() {
        player = new Player();
        platformsQueue = new PlatformsQueue();
        setDefaultSettings();

        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!model.update(player, platformsQueue)) {
                    gameStatus = false;
                }
                view.draw(player, platformsQueue, player.getScore());
                if (!gameStatus) {
                    super.stop();
                    try {
                        scoreTable.addToTable(player.getScore() - 1);
                        scoreTable.updateFile();
                        view.loose();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    platformsQueue.platformList.clear();
                }
            }
        };
        timer.start();
    }


    public <T> void listen(String message, T[] args) {
        switch (message) {
            case "StartPlay", "RestartGame" -> startGame();

            case "OpenMenu" -> view.showMenu();

            case "Score" -> view.openScoreTable();

            case "Button" -> model.handleButton((KeyCode) args[0], player);
        }
    }

    public void run() throws IOException {
        view.showMenu();
    }

}
