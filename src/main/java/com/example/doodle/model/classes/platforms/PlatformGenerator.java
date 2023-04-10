package com.example.doodle.model.classes.platforms;

import com.example.doodle.model.classes.Player;
import com.example.doodle.model.classes.platforms.platformTypes.DisappearingPlatform;
import com.example.doodle.model.classes.platforms.platformTypes.Platform;
import com.example.doodle.model.classes.platforms.platformTypes.PlatformWithSpring;

import java.util.Random;

public class PlatformGenerator {
    public static Platform generatePlatform() {
        Random rand = new Random();
        float yPos = Platform.curMaxY - rand.nextFloat(Player.jumpHeight - Platform.height) - Platform.height;
        if (Platform.curMaxY > yPos) {
            Platform.curMaxY = yPos;
        }
        float randomValue = rand.nextFloat(1);
        if (randomValue < 0.1) {
            return new PlatformWithSpring(rand.nextFloat(Platform.platformWidthDiapason), yPos, true);
        } else if(randomValue < 0.2) {
            return new DisappearingPlatform(rand.nextFloat(Platform.platformWidthDiapason), yPos);
        } else {
            return new Platform(rand.nextFloat(Platform.platformWidthDiapason), yPos);
        }
    }
}
