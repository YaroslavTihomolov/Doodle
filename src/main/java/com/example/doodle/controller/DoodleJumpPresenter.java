package com.example.doodle.controller;

import com.example.doodle.DoodleJumpView;
import com.example.doodle.model.DoodleJumpModel;
import com.example.doodle.model.classes.PlatformsQueue;
import com.example.doodle.model.classes.Player;
import com.example.doodle.model.classes.ScoreTable;
import javafx.animation.AnimationTimer;

import java.io.*;

public class DoodleJumpPresenter {
    private final DoodleJumpModel model;
    private final DoodleJumpView view;
    private long score;

    DoodleJumpPresenter(DoodleJumpModel modelReference, DoodleJumpView viewReference) {
        model = modelReference;
        view = viewReference;
    }

    private boolean gameStatus = true;

    public void run() throws IOException {
        Player player = new Player();
        PlatformsQueue platformsQueue = new PlatformsQueue();
        ScoreTable scoreTable = new ScoreTable();
        view.setPlayer();
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!model.update(player, platformsQueue)) {
                    gameStatus = false;
                }
                view.draw(player, platformsQueue, gameStatus, player.getScore());
                if (!gameStatus) {
                    super.stop();
                    try {
                        scoreTable.addToTable(player.getScore() - 1);
                        scoreTable.updateFile();
                        view.loose();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    platformsQueue.queue.remove();
                }
            }
        };
        timer.start();
    }
}
