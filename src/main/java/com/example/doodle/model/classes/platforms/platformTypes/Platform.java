package com.example.doodle.model.classes.platforms.platformTypes;


import com.example.doodle.model.classes.Player;
import com.example.doodle.model.classes.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    protected Image platformImage;
    public ImageView platformView = null;

    protected static long platformCount = 0;

    protected final long platformNum;

    public Platform(float cur_height, float weight) {
        position.setX(cur_height);
        position.setY(weight);
        platformCount++;
        platformImage = new Image("C:\\Users\\я\\Pictures\\Doodle_platform_1.png");
        platformNum = platformCount;
    }

    public static void setDefaultSettings() {
        curMaxY = fieldHeight;
        curYVelocity = 0;
        platformCount = 0;
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

    public Image getImage() {
        return platformImage;
    }
}
