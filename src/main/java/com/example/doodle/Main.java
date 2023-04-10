package com.example.doodle;

import com.example.doodle.controller.DoodleJumpController;
import com.example.doodle.model.DoodleJumpModel;
import com.example.doodle.view.DoodleJumpView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        DoodleJumpView view = new DoodleJumpView();
        DoodleJumpModel model = new DoodleJumpModel();
        DoodleJumpController presenter = new DoodleJumpController(model, view);
        try {
            presenter.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
