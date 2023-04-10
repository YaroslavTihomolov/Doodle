package com.example.doodle.model.classes.platforms.platformTypes;

import javafx.scene.image.Image;

public class DisappearingPlatform extends Platform {
    private boolean touchStatus = false;

    public DisappearingPlatform(float cur_height, float weight) {
        super(cur_height, weight);
        platformImage = new Image("C:\\4 семестр\\Doodle\\src\\main\\resources\\com\\example\\doodle\\DisappearingPlatform.png");
    }

    public void updateTouchStatus() {
        touchStatus = true;
    }

    public boolean getTouchStatus() {
        return touchStatus;
    }
}
