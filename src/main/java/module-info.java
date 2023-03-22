module com.example.doodle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.doodle to javafx.fxml;
    exports com.example.doodle;
    exports com.example.doodle.model;
    opens com.example.doodle.model to javafx.fxml;
    exports com.example.doodle.model.classes;
    opens com.example.doodle.model.classes to javafx.fxml;
    exports com.example.doodle.controller;
    opens com.example.doodle.controller to javafx.fxml;
}