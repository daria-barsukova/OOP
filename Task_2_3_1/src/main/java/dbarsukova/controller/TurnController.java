package dbarsukova.controller;

import dbarsukova.direction.MoveDirection;


/**
 * TurnController class provides method for calculating rotation angles based on directions.
 */

public class TurnController {

    /**
     * calculates rotation angle based on given direction.
     *
     * @param dir direction for which to calculate rotation angle.
     * @return rotation angle in degrees.
     */
    public static int calculateRotation(MoveDirection dir) {
        switch (dir) {
            case LEFT:
                return 90;
            case RIGHT:
                return -90;
            case UP:
                return 180;
            default:
                return 0;
        }
    }
}
