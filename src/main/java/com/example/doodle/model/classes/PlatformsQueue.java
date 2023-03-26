package com.example.doodle.model.classes;

import java.util.ArrayList;
import java.util.List;


public class PlatformsQueue {
    public final List<Platform> platformList = new ArrayList<>();

    public PlatformsQueue() {
        Platform.curMaxY = Platform.fieldHeight;
        checkContentStart();
    }

    private void checkContentStart() {
        platformList.add(new Platform(StartData.startXPos, Player.getLegPosPlayer(StartData.startYPos), false));
        checkContent();
    }

    public void checkContent() {
        while (Platform.curMaxY > -Player.jumpHeight * 4) {
            platformList.add(Platform.generatePlatform());
        }
    }

    public void repositionPlatforms(int sign, Player player) {
        if (!player.playerFalls() && Platform.distanceInFrame() > 0) {
            player.addScore((long) Platform.distanceInFrame());
        }

        Platform.curMaxY += Platform.distanceInFrame();

        for (var platform : platformList) {
            platform.changePosition(sign);
        }
        Platform.changeVelocityY();
    }

    public void deletePlatform() {
        this.platformList.removeIf(platform -> platform.position.y() > StartData.fieldHeight);
    }

    public boolean checkPlatformOnField(Player player) {
        for (var platform : platformList) {
            if (platform.position.y() + 50 > 0) {
                return false;
            }
        }
        Platform.setYVelocity(player.getYVelocity() + 400);
        return true;
    }
}
