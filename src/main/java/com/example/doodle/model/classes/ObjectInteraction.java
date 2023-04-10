package com.example.doodle.model.classes;

import com.example.doodle.model.classes.platforms.platformTypes.DisappearingPlatform;
import com.example.doodle.model.classes.platforms.platformTypes.Platform;
import com.example.doodle.model.classes.platforms.platformTypes.PlatformWithSpring;
import com.example.doodle.model.classes.platforms.PlatformsQueue;

public class ObjectInteraction {
    private static boolean jumpOnSpring = false;

    private static float interactionPlayerWithPlatformWithSpring(PlatformWithSpring platformWithSpring, Player player) {
        if (player.getRightLegPosRPlayer() > platformWithSpring.spring.getXPos() && player.getLeftLegPosRPlayer() < platformWithSpring.spring.getXPos() + PlatformWithSpring.Spring.springWidth &&
                Player.getLegPosPlayer(player.getCurYPosition()) > platformWithSpring.position.y() && Player.getLegPosPlayer(player.getCurYPosition()) < platformWithSpring.position.y() + Platform.height) {
            jumpOnSpring = true;
            platformWithSpring.spring.setHasJumped(true);
            return player.curPlatformY - platformWithSpring.position.y();
        }
        return -1;
    }

    private static float interactionPlayer(Platform platform, Player player) {
        float x = platform.position.x();
        float y = platform.position.y();
        float x_1 = platform.position.x() + Platform.width;
        float y_1 = platform.position.y() + Platform.height;

        if (platform.getClass().getName().equals("com.example.doodle.model.classes.platforms.platformTypes.DisappearingPlatform")) {
            if (((DisappearingPlatform)platform).getTouchStatus()) {
                return -1;
            }
        }


        if (platform.getClass().getName().equals("com.example.doodle.model.classes.platforms.platformTypes.PlatformWithSpring")) {
            float tmp = interactionPlayerWithPlatformWithSpring((PlatformWithSpring) platform, player);
            if (tmp != -1) {
                return tmp;
            }
        }

        if (player.getRightLegPosRPlayer() > x && player.getLeftLegPosRPlayer() < x_1 &&
                Player.getLegPosPlayer(player.getCurYPosition()) > y && Player.getLegPosPlayer(player.getCurYPosition()) < y_1) {
            jumpOnSpring = false;
            return player.curPlatformY - platform.position.y();
        }
        return -1;
    }

    private static void notifyDisappearingPlatform(DisappearingPlatform disappearingPlatform) {
        disappearingPlatform.updateTouchStatus();
    }

    public static void interactionCheck(Player player, PlatformsQueue platformsQueue) {
        float val;
        for (var platform : platformsQueue.platformList) {
            if (platform.position.y() < 0) {
                break;
            }
            val = interactionPlayer(platform, player);
            if (val != -1.0) {
                if (player.getCurPlatformNum() != platform.getPlatformNum() && (player.curPlatformY - platform.position.y() > 0)) {
                    player.addScore((long) (player.curPlatformY - platform.position.y()));
                }
                if (platform.getClass().getName().equals("com.example.doodle.model.classes.platforms.platformTypes.DisappearingPlatform")) {
                    notifyDisappearingPlatform((DisappearingPlatform) platform);
                }
                player.updateInteractionStatus(jumpOnSpring);
                player.setCurPlatformNum(platform.getPlatformNum());
                player.curPlatformY = platform.position.y();
                break;
            }
        }
    }
}
