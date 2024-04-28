package dbarsukova.snake;


/**
 * SnakeElement class represents single segment of snake body.
 */

public class SnakeElement {
    private int x;
    private int y;

    /**
     * constructs SnakeElement object with specified coordinates.
     *
     * @param x x-coordinate of snake element.
     * @param y y-coordinate of snake element.
     */
    public SnakeElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * retrieves x-coordinate of snake element.
     *
     * @return x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * retrieves y-coordinate of snake element.
     *
     * @return y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * sets x-coordinate of snake element.
     *
     * @param x new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * sets y-coordinate of snake element.
     *
     * @param y new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }
}
