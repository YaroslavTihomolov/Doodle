package com.example.doodle.controller;

import javafx.event.ActionEvent;

public class looseController {
    MenuController menuController = new MenuController();

    public void startPlay(ActionEvent event) {
        menuController.startPlay(event);
    }

    public void resetMenu(ActionEvent event) {
        menuController.resetMenu(event);
    }
}
