package com.example.doodle.view.fxmlcontrollers;

import com.example.doodle.controller.DoodleJumpController;

public abstract class Source {
    private static DoodleJumpController doodleJumpController;

    public static void addPlayer(DoodleJumpController sourceDoodleJumpController) {
        doodleJumpController = sourceDoodleJumpController;
    }

    @SafeVarargs
    public final <T>void notify(String event, T... args) {
        doodleJumpController.listen(event, args);
    }
}