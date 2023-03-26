package com.example.doodle.model.classes;

public class ObjectInteraction {
    private static boolean jumpOnSpring = false;

    private static float interactionPlayer(Platform platform, Player player) {
        float x = platform.position.x();
        float y = platform.position.y();
        float x_1 = platform.position.x() + Platform.width;
        float y_1 = platform.position.y() + Platform.height;

        if (platform.getSpringStatus()) {
            if (player.getRightLegPosRPlayer() > platform.spring.getXPos() && player.getLeftLegPosRPlayer() < platform.spring.getXPos() + Platform.Spring.springWidth &&
                    Player.getLegPosPlayer(player.getCurYPosition()) > y && Player.getLegPosPlayer(player.getCurYPosition()) < y_1) {
                jumpOnSpring = true;
                platform.spring.setHasJumped(true);
                return player.curPlatformY - platform.position.y();
            }
        }
        if (player.getRightLegPosRPlayer() > x && player.getLeftLegPosRPlayer() < x_1 &&
                Player.getLegPosPlayer(player.getCurYPosition()) > y && Player.getLegPosPlayer(player.getCurYPosition()) < y_1) {
            jumpOnSpring = false;
            return player.curPlatformY - platform.position.y();
        }
        return -1;
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
                if (jumpOnSpring) {
                    player.setSpringVelocity();
                } else {
                    player.setDefaultVelocity();
                }
                player.setCurPlatformNum(platform.getPlatformNum());
                player.curPlatformY = platform.position.y();
                player.setYPos(platform.position.y() - Player.LegOffsetPlayer);
                break;
            }
        }
    }
}
