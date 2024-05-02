package dbarsukova;

import dbarsukova.controller.GameController;
import dbarsukova.controller.UserHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * main class representing Snake game application.
 */

public class App extends Application {

    /**
     * entry point for launching JavaFX application.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * starts JavaFX application.
     *
     * @param stage primary stage for application.
     */
    @Override
    public void start(Stage stage) {
        GameController controller = new GameController(20, 20, 35, 21, 20, 300.0f);
        controller.init();
        Scene scene = new Scene(controller.getPane(), 950, 700, Color.BLUEVIOLET);
        stage.setTitle("SNAKE GAME");
        stage.setScene(scene);
        Thread thread = new Thread(controller);
        scene.setOnKeyPressed(new UserHandler(controller, thread));
        stage.setOnCloseRequest(event -> {
            controller.stop(true);
            if (thread.isAlive()) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
        Platform.runLater(stage::show);
    }
}
