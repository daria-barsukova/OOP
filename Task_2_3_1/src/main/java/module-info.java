module dbarsukova {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    exports dbarsukova;
    opens dbarsukova to javafx.fxml;
    exports dbarsukova.controller;
    opens dbarsukova.controller to javafx.fxml;
    exports dbarsukova.model;
    opens dbarsukova.model to javafx.fxml;
    exports dbarsukova.snake;
    opens dbarsukova.snake to javafx.fxml;
    exports dbarsukova.direction;
    opens dbarsukova.direction to javafx.fxml;
}