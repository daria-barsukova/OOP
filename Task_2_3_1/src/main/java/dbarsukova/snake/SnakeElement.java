package dbarsukova.snake;


/**
 * SnakeElement class represents single segment of snake body.
 */

public class SnakeElement {
    private int elemX;
    private int elemY;

    /**
     * constructs SnakeElement object with specified coordinates.
     *
     * @param x x-coordinate of snake element.
     * @param y y-coordinate of snake element.
     */
    public SnakeElement(int x, int y) {
        this.elemX = x;
        this.elemY = y;
    }

    /**
     * retrieves x-coordinate of snake element.
     *
     * @return x-coordinate.
     */
    public int getX() {
        return elemX;
    }

    /**
     * retrieves y-coordinate of snake element.
     *
     * @return y-coordinate.
     */
    public int getY() {
        return elemY;
    }

    /**
     * sets x-coordinate of snake element.
     *
     * @param x new x-coordinate.
     */
    public void setX(int x) {
        this.elemX = x;
    }

    /**
     * sets y-coordinate of snake element.
     *
     * @param y new y-coordinate.
     */
    public void setY(int y) {
        this.elemY = y;
    }
}
