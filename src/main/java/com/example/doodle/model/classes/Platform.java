package com.example.doodle.model.classes;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Platform {
    public static final int width = 90;
    public static final int height = 23;
    public Position position = new Position();
    private static final int max = 5;
    private static final int min = 1;
    public static final int fieldWidth = 500;
    public static final int fieldHeight = 675;
    public static final float platformWidthDiapason = fieldWidth - width;
    public static final float platformDown = 0;
    public static final float platformUp = 1;

    public static float curMaxY = fieldHeight;
    public static float curYVelocity = 0;
    public static final Image platformImage = new Image("C:\\Users\\я\\Pictures\\Doodle_platform_1.png");
    public ImageView platformView = null;

    private static long platformCount = 0;

    private final long platformNum;

    public Platform(float cur_height, float weight) {
        position.setX(cur_height);
        position.setY(weight);
        platformCount++;
        platformNum = platformCount;
    }

    public long getPlatformNum() { return platformNum; }

    public static void setYVelocity(float velocity) { curYVelocity = velocity; }

    public static void changeVelocityY() {
        curYVelocity += Player.gravity_const * Player.coef_1;
    }

    public static float distanceInFrame() {
        return curYVelocity * Player.coef_1 + Player.coef_2;
    }

    public void changePosition(int sign) {
        if (sign == platformDown) {
            position.setY(position.y() + distanceInFrame());
        } else if (sign == platformUp) {
            position.setY(position.y() - distanceInFrame());
        }
    }

    public static Platform generatePlatform(int offset) {
        Random rand = new Random();
        float yPos = 330 + rand.nextFloat(Player.jumpHeight) - Player.jumpHeight * offset;
        if (curMaxY > yPos) {
            curMaxY = yPos;
        }
        return new Platform(rand.nextFloat(Platform.platformWidthDiapason), yPos);
    }

    private static Platform generateFirstInBoxPlatform() {
        Random rand = new Random();
        float yPos = curMaxY - rand.nextFloat(Player.jumpHeight);
        if (curMaxY > yPos) {
            curMaxY = yPos;
        }
        return new Platform(rand.nextFloat(Platform.platformWidthDiapason), yPos);
    }

    public static Platform[] generatePlatformBox(int offset) {
        Random rand = new Random();
        int platformCount = rand.nextInt((max - min) + 1) + min;
        Platform[] platformsBox = new Platform[platformCount];
        for (int i = 0; i < platformCount; i++) {
            if (i == platformCount - 1) {
                platformsBox[i] = generateFirstInBoxPlatform();
            } else {
                platformsBox[i] = generateFirstInBoxPlatform();
            }
        }
        return platformsBox;
    }

}
