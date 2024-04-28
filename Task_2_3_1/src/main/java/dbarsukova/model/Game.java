package dbarsukova.model;


import dbarsukova.direction.MoveDirection;
import dbarsukova.snake.Snake;
import dbarsukova.snake.SnakeElement;

/**
 * SnakeGame class represents main game logic and state management.
 */

public class Game {
    private final int width;
    private final int height;
    private final int goal;
    private final Field field;
    private int score;
    private boolean loss;
    private boolean win;

    /**
     * constructs SnakeGame object with specified width, height, limit and goal.
     *
     * @param width  width of game field.
     * @param height height of game field.
     * @param limit  limit.
     * @param goal   goal score to win.
     * @throws IllegalArgumentException if goal is less than or equal to 0.
     */
    public Game(int width, int height, int limit, int goal)
            throws IllegalArgumentException {
        if (goal <= 0) {
            throw new IllegalArgumentException("Goal should be more than 0");
        }
        this.width = width;
        this.height = height;
        this.goal = goal;
        field = new Field(width, height, limit);
    }

    /**
     * restarts game by resetting game state.
     */
    public void restart() {
        loss = false;
        win = false;
        score = 0;
        field.initField();
    }

    /**
     * retrieves game field.
     *
     * @return game field.
     */
    public Field getField() {
        return field;
    }

    /**
     * retrieves current score of game.
     *
     * @return current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * retrieves goal score required to win game.
     *
     * @return goal score.
     */
    public int getGoal() {
        return goal;
    }

    /**
     * checks if game has ended in loss.
     *
     * @return true if game is lost, false otherwise.
     */
    public boolean isLoss() {
        return loss;
    }

    /**
     * checks if game has ended in win.
     *
     * @return true if game is won, false otherwise.
     */
    public boolean isWin() {
        return win;
    }

    /**
     * performs step in game based on given direction.
     *
     * @param nextDir direction of next step.
     * @return true if step results in acquiring food, false otherwise.
     * @throws IllegalArgumentException if nextDir is null.
     */
    public boolean makeStep(MoveDirection nextDir)
            throws IllegalArgumentException {
        if (loss || win) {
            return false;
        }
        if (nextDir == null) {
            throw new IllegalArgumentException("Illegal direction");
        }
        Snake snake = field.getSnake();
        snake.changeDir(nextDir);
        snake.grow();
        SnakeElement headElem = snake.getHead().getElem();
        int headX = headElem.getX();
        int headY = headElem.getY();
        headX = (headX + width) % width;
        headY = (headY + height) % height;
        headElem.setX(headX);
        headElem.setY(headY);
        if (field.snakeCollision() || field.checkWallField(headX, headY)) {
            loss = true;
            return false;
        }
        if (field.checkAppleField(headX, headY)) {
            field.removeApple(headX, headY);
            field.spawnApple();
            score++;
            if (score == goal) {
                win = true;
            }
            return true;
        } else {
            snake.getSnake().remove(snake.getTail());
            return false;
        }
    }
}
