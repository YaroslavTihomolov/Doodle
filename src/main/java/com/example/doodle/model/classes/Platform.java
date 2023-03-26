package com.example.doodle.model.classes;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Platform {
    public static final int width = 90;
    public static final int height = 23;
    public Position position = new Position();
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

    private boolean hasSpring = false;

    public Spring spring;

    public boolean getSpringStatus() { return hasSpring; }

    public class Spring {
        public static final Image springImage = new Image("C:\\4 семестр\\Doodle\\src\\main\\resources\\com\\example\\doodle\\DoodleJumpSpring.png");
        public static final Image springLongImage = new Image("C:\\4 семестр\\Doodle\\src\\main\\resources\\com\\example\\doodle\\DoodleJumpLongSpring.png");
        public static final float springWidth = 25;
        public static final float springUpPlatform = 12;

        private boolean hasJumped = false;

        public ImageView springView = null;
        private final float xPos;

        Spring(float yPos) {
            hasSpring = true;
            this.xPos = yPos;
        }

        public void setHasJumped(boolean hasJumped) { this.hasJumped = hasJumped; }

        public boolean isHasJumped() { return hasJumped; }

        public float getXPos() {
            return xPos;
        }
    }

    public Platform(float cur_height, float weight, boolean isSpring) {
        position.setX(cur_height);
        position.setY(weight);
        platformCount++;
        platformNum = platformCount;
        Random rand = new Random();
        if (isSpring) {
            hasSpring = true;
            spring = new Spring(cur_height + rand.nextFloat(Platform.width - Spring.springWidth));
        }
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


    public static Platform generatePlatform() {
        Random rand = new Random();
        float yPos = curMaxY - rand.nextFloat(Player.jumpHeight - height) - height;
        if (curMaxY > yPos) {
            curMaxY = yPos;
        }
        if (rand.nextFloat(1) < 0.1) {
            return new Platform(rand.nextFloat(Platform.platformWidthDiapason), yPos, true);
        } else {
            return new Platform(rand.nextFloat(Platform.platformWidthDiapason), yPos, false);
        }
    }
}
