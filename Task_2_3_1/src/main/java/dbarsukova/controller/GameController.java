package dbarsukova.controller;

import dbarsukova.model.Game;
import dbarsukova.View;
import dbarsukova.direction.MoveDirection;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


/**
 * GameController class manages game logic and graphical user interface interactions.
 * it implements Runnable interface to run game loop in separate thread.
 */

public class GameController implements Runnable {
    private Game game;
    private final FieldController fieldPane;
    private AnchorPane pane;
    private Pane menuPane;
    private Pane pausePane;
    private Pane lossPane;
    private Pane winPane;
    private Text score;
    private boolean stopped;
    private boolean busy;
    private MoveDirection nextDir;
    private float step;

    /**
     * constructs GameController with specified dimensions and game parameters.
     *
     * @param width  width of game field.
     * @param height height of game field.
     * @param size   size of each block.
     * @param limit  maximum number of food points.
     * @param goal   target score to win game.
     * @param step   time interval between each game step in milliseconds.
     * @throws IllegalArgumentException if any of arguments is invalid.
     */
    public GameController(int width, int height, int size, int limit, int goal, float step)
            throws IllegalArgumentException {
        if (width < 0 || height < 0 || limit <= 0 || goal <= 0) {
            throw new IllegalArgumentException("Invalid arguments provided");
        }
        initializeGame(width, height, limit, goal, step);
        initializePanes(width, height, size, goal);
        fieldPane = new FieldController(game.getField(), size, new View(width, height, size));
        initializePaneChildren();
    }

    private void initializeGame(int width, int height, int limit, int goal, float step) {
        this.step = step;
        game = new Game(width, height, limit, goal);
    }

    private void initializePanes(int width, int height, int size, int goal) {
        pane = new AnchorPane();
        pane.setMaxWidth(width * size);
        pane.setMaxHeight(height * size);
        pane.setManaged(false);
        View view = new View(width, height, size);
        pausePane = view.initPause();
        lossPane = view.initGameOver();
        winPane = view.initVictory();
        menuPane = view.initMenu(goal);
        score = view.getScore();
    }

    private void initializePaneChildren() {
        pane.getChildren().addAll(
                fieldPane.getFieldPane(), pausePane, menuPane, lossPane, winPane
        );
    }

    /**
     * initializes game by restarting, resetting direction, drawing field and hiding UI elements.
     */
    public void init() {
        game.restart();
        resetDir();
        fieldPane.drawField();
        hidePane();
    }

    private void resetDir() {
        nextDir = MoveDirection.LEFT;
        stopped = false;
        busy = false;
    }

    private void hidePane() {
        pausePane.setVisible(false);
        lossPane.setVisible(false);
        winPane.setVisible(false);
    }

    /**
     * toggles pause state of game.
     */
    public void pause() {
        if (busy == pausePane.isVisible() && !stopped) {
            busy = !busy;
            pausePane.setVisible(!pausePane.isVisible());
        }
    }

    /**
     * gets main pane containing game elements.
     *
     * @return main pane.
     */
    public Pane getPane() {
        return pane;
    }

    /**
     * sets stop state of game loop.
     *
     * @param flag stop state.
     */
    public void stop(boolean flag) {
        this.stopped = flag;
    }

    /**
     * changes direction of snake based on user input.
     *
     * @param dir new direction.
     * @throws NullPointerException if direction is null.
     */
    public void changeSnakeDir(MoveDirection dir) throws NullPointerException {
        if (dir == null) {
            throw new NullPointerException("Invalid arguments provided");
        }
        if (!busy) {
            MoveDirection curr = game.getField().getSnake().getHead().getDir();
            switch (dir) {
                case LEFT:
                    if (curr != MoveDirection.RIGHT) {
                        nextDir = dir;
                    }
                    break;
                case RIGHT:
                    if (curr != MoveDirection.LEFT) {
                        nextDir = dir;
                    }
                    break;
                case UP:
                    if (curr != MoveDirection.DOWN) {
                        nextDir = dir;
                    }
                    break;
                case DOWN:
                    if (curr != MoveDirection.UP) {
                        nextDir = dir;
                    }
                    break;
            }
        }
    }

    /**
     * executes next step of game loop.
     */
    public void next() {
        if (stopped || busy) {
            return;
        }
        if (game.makeStep(nextDir)) {
            fieldPane.deleteApple(game.getField().getLastDelete());
            fieldPane.drawApple(game.getField().getLastAdd());
        }
        updateScore();
        if (game.isLoss()) {
            stopped = true;
            lossPane.setVisible(true);
        } else if (game.isWin()) {
            stopped = true;
            winPane.setVisible(true);
        } else {
            fieldPane.updateSnake();
        }
    }

    private void updateScore() {
        score.setText(String.format("%d/%d", game.getScore(), game.getGoal()));
    }

    @Override
    public void run() {
        while (!stopped) {
            long start = System.currentTimeMillis();
            Platform.runLater(this::next);
            long elapsedTime = System.currentTimeMillis() - start;
            long sleepTime = Math.max(0, (long) step - elapsedTime);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
