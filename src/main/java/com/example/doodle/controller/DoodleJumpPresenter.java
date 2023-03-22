package com.example.doodle.controller;

import com.example.doodle.DoodleJumpView;
import com.example.doodle.model.DoodleJumpModel;
import com.example.doodle.model.classes.PlatformsQueue;
import com.example.doodle.model.classes.Player;
import javafx.animation.AnimationTimer;

import java.io.IOException;

public class DoodleJumpPresenter {
    private final DoodleJumpModel model;
    private final DoodleJumpView view;

    DoodleJumpPresenter(DoodleJumpModel modelReference, DoodleJumpView viewReference) {
        model = modelReference;
        view = viewReference;
    }

    private boolean gameStatus = true;

    public void run() {
        Player player = new Player();
        PlatformsQueue platformsQueue = new PlatformsQueue();
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
                    platformsQueue.queue.remove();
                    try {
                        view.loose();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        timer.start();
    }
}
