package com.example.doodle.model.classes.platforms.platformTypes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class PlatformWithSpring extends Platform {
    private boolean hasSpring = false;

    public Spring spring;

    public boolean getSpringStatus() { return hasSpring; }

    public PlatformWithSpring(float cur_height, float weight, boolean isSpring) {
        super(cur_height, weight);
        if (isSpring) {
            hasSpring = true;
            Random rand = new Random();
            spring = new Spring(cur_height + rand.nextFloat(Platform.width - Spring.springWidth));
        }
    }

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
}
