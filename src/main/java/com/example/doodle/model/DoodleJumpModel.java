package com.example.doodle.model;

import com.example.doodle.model.classes.*;

public class DoodleJumpModel {
    private boolean gameStatus = true;
    public static final int platformDown = 0;

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
            Float upHeight = ObjectInteraction.interactionCheck(player, platformsQueue);
            if (upHeight != -1.0) {
                player.setDefaultVelocity();
            }
        }
        if (platformsQueue.deletePlatformBox(StartData.fieldHeight)) {
            platformsQueue.queue.remove();
            platformsQueue.checkContent();
        }
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
        platformsQueue.repositionPlatforms(platformDown, player);
        player.changePositionY();
        player.setVelocity(400);
        return !platformsQueue.checkPlatformOnField(player, platformsQueue);
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
