package com.example.doodle.model.classes;

import javafx.scene.image.Image;

public class Player {
    private static final float frames = 144;
    public static final float coef_1 = 1 / frames;
    public static final float gravity_const = -2160;
    public static final float coef_2 = gravity_const / (frames * frames);
    private final float base_left_right_velocity = 416 / frames;
    private static final float base_up_velocity = 1080;
    private static final float jumpHalfTime = base_up_velocity / (-gravity_const);
    public static final float jumpHeight = (-gravity_const) * jumpHalfTime * jumpHalfTime / 2 - 5;
    public static final float rightLegOffsetRPlayer = 57;
    public static final float leftLegOffsetRPlayer = 20;
    public static final float LegOffsetPlayer = 75;
    public static final Image rightDoodlerImage = new Image("C:\\4 семестр\\Doodle\\src\\main\\resources\\com\\example\\doodle\\Doodler_right.png");
    public static final Image leftDoodlerImage = new Image("C:\\4 семестр\\Doodle\\src\\main\\resources\\com\\example\\doodle\\Doodler_left.png");

    private Image curImage = rightDoodlerImage;
    private long curPlatformNum = 0;
    private final Position curPosition;
    private boolean interactionStatus = false;
    private boolean springStatus = false;

    private long score = 0;

    public float curPlatformY = 675;

    private float curXVelocity, curYVelocity;

    public long getCurPlatformNum() { return curPlatformNum; }

    public long getScore() { return score; }

    public void setCurPlatformNum(long curPlatformNum) { this.curPlatformNum = curPlatformNum; }

    public Player() {
        curPosition = new Position(220, 530);
    }

    private void changeVelocityY() {
        curYVelocity += gravity_const * coef_1;
    }

    public float distanceInFrame() { return curYVelocity * coef_1 + coef_2; }

    public void changePositionX() { curPosition.setX(curPosition.x() + curXVelocity); }

    public void addScore(long distance) { score += distance; }

    public void changePositionY() {
        curPosition.setY(curPosition.y() - distanceInFrame());
        changeVelocityY();
    }

    public boolean playerFalls() {
        return curYVelocity < 0;
    }

    public float getCurYPosition() { return curPosition.y(); }

    public float getCurXPosition() { return curPosition.x(); }

    public void setDefaultVelocity() {
        curYVelocity = base_up_velocity;
    }

    public void setSpringVelocity() { curYVelocity = base_up_velocity * 1.5f; }

    public void changeVelocityXR() {
        curXVelocity = base_left_right_velocity;
    }

    public void changeVelocityXL() {
        curXVelocity = -base_left_right_velocity;
    }

    public float getYVelocity() { return curYVelocity; }

    public void changeVelocityXToZero() {
        curXVelocity = 0;
    }

    public void changeVelocityYToZero() { curYVelocity = 0; }

    public void posTransfer() {
        if (curPosition.x() < -40) {
            curPosition.setX(459);
        }
        if (curPosition.x() > 460) {
            curPosition.setX(-39);
        }
    }

    public float getRightLegPosRPlayer() {
        return curPosition.x() + rightLegOffsetRPlayer;
    }

    public float getLeftLegPosRPlayer() {
        return curPosition.x() + leftLegOffsetRPlayer;
    }

    public static float getLegPosPlayer(float curPos) { return curPos + LegOffsetPlayer; }

    public float getHeadPosPlayer() { return curPosition.y() + 6; }

    public void setVelocity(float velocityToSet) {
        curYVelocity = velocityToSet;
    }

    public void setYPos(float yPos) { curPosition.setY(yPos); }

    public void updateVelocity() {
        if (!interactionStatus)
            return;
        if (springStatus) {
            setSpringVelocity();
            springStatus = false;
        } else {
            setDefaultVelocity();
        }
        interactionStatus = false;
    }

    public void updateInteractionStatus(boolean springStatus) {
        this.springStatus = springStatus;
        this.interactionStatus = true;
    }

    public void updateImage(Image newImage) { curImage = newImage; }

    public Image getCurImage() { return curImage; }
}
