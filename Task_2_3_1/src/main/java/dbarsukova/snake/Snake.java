package dbarsukova.snake;

import dbarsukova.direction.MoveDirection;

import java.util.ArrayList;
import java.util.List;


/**
 * Snake class represents snake entity in game.
 * it manages snake's body, movement and growth.
 */

public class Snake {
    private final List<Body> snake;

    /**
     * constructs Snake object with initial position and direction.
     *
     * @param x          initial x-coordinate of snake's head.
     * @param y          initial y-coordinate of snake's head.
     * @param dir  initial direction of snake.
     */
    public Snake(int x, int y, MoveDirection dir) {
        snake = new ArrayList<>();
        snake.add(new Body(x, y, dir));
        grow();
        grow();
    }

    /**
     * increases length of snake by one block.
     */
    public void grow() {
        snake.add(0, nextNode(getHead()));
    }

    /**
     * gets length of snake.
     *
     * @return length of snake.
     */
    public int getLength() {
        return snake.size();
    }

    /**
     * gets head of snake.
     *
     * @return head of snake.
     */
    public Body getHead() {
        return snake.get(0);
    }

    /**
     * gets tail of snake.
     *
     * @return tail of snake.
     */
    public Body getTail() {
        return snake.get(snake.size() - 1);
    }

    /**
     * gets list of blocks representing snake's body.
     *
     * @return list of blocks.
     */
    public List<Body> getSnake() {
        return snake;
    }

    /**
     * changes direction of snake.
     *
     * @param dir new direction.
     */
    public void changeDir(MoveDirection dir) {
        getHead().setDir(dir);
    }

    /**
     * calculates position of next block based on current
     * block's position and direction.
     *
     * @param node current block of snake.
     * @return next block of snake.
     * @throws IllegalArgumentException if direction is invalid.
     */
    public Body nextNode(Body node) {
        int x = node.getElem().getX();
        int y = node.getElem().getY();
        switch (node.getDir()) {
            case LEFT:
                return new Body(x - 1, y, MoveDirection.LEFT);
            case RIGHT:
                return new Body(x + 1, y, MoveDirection.RIGHT);
            case UP:
                return new Body(x, y - 1, MoveDirection.UP);
            case DOWN:
                return new Body(x, y + 1, MoveDirection.DOWN);
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
    }
}
