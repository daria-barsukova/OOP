package dbarsukova.controller;

import dbarsukova.direction.MoveDirection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


/**
 * UserHandler class handles keyboard events for controlling game.
 */

public class UserHandler implements EventHandler<KeyEvent> {
    private final GameController controller;
    private Thread gameThread;

    /**
     * constructs Handler with specified game controller and thread.
     *
     * @param controller game controller.
     * @param gameThread game thread.
     */
    public UserHandler(GameController controller, Thread gameThread) {
        this.controller = Objects.requireNonNull(controller);
        this.gameThread = Objects.requireNonNull(gameThread);
    }

    /**
     * handles keyboard events to control game.
     *
     * @param event event to handle.
     */
    @Override
    public void handle(KeyEvent event) {
        Map<KeyCode, Runnable> actions = new HashMap<>();
        actions.put(KeyCode.LEFT, () -> controller.changeSnakeDir(MoveDirection.LEFT));
        actions.put(KeyCode.A, () -> controller.changeSnakeDir(MoveDirection.LEFT));
        actions.put(KeyCode.RIGHT, () -> controller.changeSnakeDir(MoveDirection.RIGHT));
        actions.put(KeyCode.D, () -> controller.changeSnakeDir(MoveDirection.RIGHT));
        actions.put(KeyCode.UP, () -> controller.changeSnakeDir(MoveDirection.UP));
        actions.put(KeyCode.W, () -> controller.changeSnakeDir(MoveDirection.UP));
        actions.put(KeyCode.DOWN, () -> controller.changeSnakeDir(MoveDirection.DOWN));
        actions.put(KeyCode.S, () -> controller.changeSnakeDir(MoveDirection.DOWN));
        actions.put(KeyCode.ESCAPE, controller::pause);
        actions.put(KeyCode.ENTER, () -> {
            controller.stop(true);
            if (gameThread.isAlive()) {
                try {
                    gameThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            controller.init();
            gameThread = new Thread(controller);
            gameThread.start();
        });
        Runnable action = actions.get(event.getCode());
        if (action != null) {
            action.run();
        }
    }
}
