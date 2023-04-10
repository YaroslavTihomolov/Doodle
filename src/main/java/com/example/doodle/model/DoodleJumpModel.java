package com.example.doodle.model;

import com.example.doodle.model.classes.*;
import com.example.doodle.model.classes.platforms.platformTypes.Platform;
import com.example.doodle.model.classes.platforms.PlatformsQueue;
import javafx.scene.input.KeyCode;

public class DoodleJumpModel {
    private boolean gameStatus = true;
    public static final int platformDown = 0;

    public void setDefaultSettings() {
        gameStatus = true;
    }

    private void gameStatusCheck(float legPos) {
        if (legPos > StartData.fieldHeight) {
            gameStatus = false;
        }
    }

    private void downPlatform(Player player, PlatformsQueue platformsQueue) {
        if (player.getYVelocity() > 0) {
            Platform.setYVelocity(player.getYVelocity());
            player.changeVelocityYToZero();
        }
        if (Platform.curYVelocity == 0) {
            player.changePositionY();
        } else {
            platformsQueue.repositionPlatforms(platformDown, player);
        }
        player.changePositionX();
    }

    private void upPlayer(Player player, PlatformsQueue platformsQueue) {
        player.changePositionX();
        player.changePositionY();
        player.posTransfer();
        if (player.playerFalls()) {
            ObjectInteraction.interactionCheck(player, platformsQueue);
            player.updateVelocity();
        }
        platformsQueue.deletePlatform();
        platformsQueue.checkContent();
    }

    private void gameStatusOn(Player player, PlatformsQueue platformsQueue) {
        if (player.getHeadPosPlayer() < StartData.fieldHalf) {
            downPlatform(player, platformsQueue);
        } else {
            upPlayer(player, platformsQueue);
        }
        gameStatusCheck(Player.getLegPosPlayer(player.getCurYPosition()));
    }

    private boolean gameStatusOff(Player player, PlatformsQueue platformsQueue) {
        player.changePositionY();
        player.setVelocity(400);
        platformsQueue.repositionPlatforms(platformDown, player);
        return !platformsQueue.checkPlatformOnField(player);
    }

    public void handleButton(KeyCode keyCode, Player player) {
        if (keyCode == KeyCode.LEFT) {
            player.updateImage(Player.leftDoodlerImage);
            player.changeVelocityXL();
        } else if (keyCode == KeyCode.RIGHT) {
            player.updateImage(Player.rightDoodlerImage);
            player.changeVelocityXR();
        }
    }

    public boolean update(Player player, PlatformsQueue platformsQueue) {
        if (gameStatus) {
            gameStatusOn(player, platformsQueue);
        } else {
            return gameStatusOff(player, platformsQueue);
        }
        return true;
    }
}
