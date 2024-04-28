package dbarsukova.snake;


import dbarsukova.direction.MoveDirection;

/**
 * Body class represents body segment of snake in game.
 */

public class Body {
    private final SnakeElement elem;
    private MoveDirection dir;

    /**
     * constructs Body object with specified coordinates and direction.
     *
     * @param x   x-coordinate of body segment.
     * @param y   y-coordinate of body segment.
     * @param dir direction of movement.
     * @throws NullPointerException if direction is null.
     */
    public Body(int x, int y, MoveDirection dir) throws NullPointerException {
        if (dir == null) {
            throw new NullPointerException("Illegal direction");
        }
        elem = new SnakeElement(x, y);
        this.dir = dir;
    }

    /**
     * retrieves SnakeElement associated with this body segment.
     *
     * @return SnakeElement.
     */
    public SnakeElement getElem() {
        return elem;
    }

    /**
     * retrieves direction of movement of this body segment.
     *
     * @return direction of movement.
     */
    public MoveDirection getDir() {
        return dir;
    }

    /**
     * sets direction of movement of this body segment.
     *
     * @param dir new direction of movement.
     * @throws NullPointerException if direction is null.
     */
    public void setDir(MoveDirection dir) throws NullPointerException {
        if (dir == null) {
            throw new NullPointerException("Illegal direction");
        }
        this.dir = dir;
    }
}
