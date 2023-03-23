package com.example.doodle.model.classes;

import java.util.LinkedList;
import java.util.Queue;


public class PlatformsQueue {
    public final Queue<Platform[]> queue = new LinkedList<>();

    public PlatformsQueue() {
        Platform.curMaxY = Platform.fieldHeight;
        checkContentStart();
    }

    private void checkContentStart() {
        Platform[] platformsBox = new Platform[3];
        platformsBox[0] = new Platform(StartData.startXPos, Player.getLegPosPlayer(StartData.startYPos));
        for (int i = 0; i < 3; i++) {
            if (i != 0) {
                platformsBox[i] = Platform.generatePlatform(0);
            }
        }
        queue.add(platformsBox);
        while (queue.size() < 4) {
            queue.add(Platform.generatePlatformBox(queue.size()));
        }
    }

    public void checkContent() {
        while (queue.size() < 4) {
            queue.add(Platform.generatePlatformBox(queue.size()));
        }
    }

    public void repositionPlatforms(int sign, Player player) {
        if (!player.playerFalls() && Platform.distanceInFrame() > 0) {
            player.addScore((long) Platform.distanceInFrame());
        }

        Platform.curMaxY += Platform.distanceInFrame();

        for (var platformBox : queue) {
            for (var platform : platformBox) {
                platform.changePosition(sign);
            }
        }
        Platform.changeVelocityY();
    }

    public boolean deletePlatformBox(int fieldHeight) {
        var platformBox = queue.peek();
        assert platformBox != null;
        for (var platform : platformBox) {
            if (platform.position.y() < fieldHeight) {
                return false;
            }
        }
        return true;
    }

    public boolean checkPlatformOnField(Player player, PlatformsQueue platformsQueue) {
        var platformBox = platformsQueue.queue.peek();
        assert platformBox != null;
        for (var platform : platformBox) {
            if (platform.position.y() + 50 > 0) {
                return false;
            }
        }
        Platform.setYVelocity(player.getYVelocity() + 400);
        return true;
    }
}
