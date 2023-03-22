package com.example.doodle.model.classes;

public class ObjectInteraction {
    private static float interactionPlayer(Platform platform, Player player) {
        float x = platform.position.x();
        float y = platform.position.y();
        float x_1 = platform.position.x() + Platform.width;
        float y_1 = platform.position.y() + Platform.height;

        if (player.getRightLegPosRPlayer() > x && player.getLeftLegPosRPlayer() < x_1 &&
                Player.getLegPosPlayer(player.getCurYPosition()) > y && Player.getLegPosPlayer(player.getCurYPosition()) < y_1) {
            return player.curPlatformY - platform.position.y();
        }
        return -1;
    }

        public static Float interactionCheck(Player player, PlatformsQueue platformsQueue) {
        int index = 0;
        float val = -1.0f;
        outerLoop:
        for (var platformsBox : platformsQueue.queue) {
            if (++index >= 4) {
                break;
            }
            for (var platform : platformsBox) {
                if (platform != null ) {
                    val = interactionPlayer(platform, player);
                    if (val != -1.0) {
                        //player.changeScoreStatus(player.getCurPlatformNum() != platform.getPlatformNum());
                        if (player.getCurPlatformNum() != platform.getPlatformNum()) {
                            System.out.println((player.curPlatformY + "   " + platform.position.y()));
                            player.addScore((long) (player.curPlatformY - platform.position.y()));
                        }
                        player.setCurPlatformNum(platform.getPlatformNum());
                        player.curPlatformY = platform.position.y();
                        player.setYPos(platform.position.y() - Player.LegOffsetPlayer);
                        break outerLoop;
                    }
                }
            }
        }
        return val;
    }
}
