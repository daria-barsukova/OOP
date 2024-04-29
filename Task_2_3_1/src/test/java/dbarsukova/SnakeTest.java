package dbarsukova;

import dbarsukova.direction.MoveDirection;
import dbarsukova.model.Field;
import dbarsukova.model.Game;
import dbarsukova.snake.Body;
import dbarsukova.snake.Snake;
import dbarsukova.snake.SnakeElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SnakeTest {
    @Test
    public void test1() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new Snake(0, 0, null));
        Assertions.assertThrows(NullPointerException.class, () -> new Snake(0, 0, MoveDirection.LEFT).changeDir(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Field(0, 0, 3));
    }

    @Test
    public void test2() {
        Snake snake = new Snake(5, 0, MoveDirection.LEFT);
        Body body = snake.nextNode(snake.getHead());
        Assertions.assertEquals(2, body.getElem().getX());
        Assertions.assertEquals(0, body.getElem().getY());
        Assertions.assertEquals(MoveDirection.LEFT, body.getDir());
        snake.changeDir(MoveDirection.DOWN);
        for (int i = 0; i < 4; i++) {
            snake.grow();
        }
        Assertions.assertEquals(7, snake.getLength());
        Assertions.assertEquals(4, snake.getHead().getElem().getY());
        Assertions.assertEquals(0, snake.getTail().getElem().getY());
    }

    @Test
    public void test3() {
        Game game = new Game(20, 20, 2, 1);
        game.restart();
        Assertions.assertFalse(game.isWin());
        Assertions.assertFalse(game.isLoss());
        SnakeElement elem = game.getField().getLastAdd();
        Assertions.assertTrue(game.getField().checkAppleField(elem.getX(), elem.getY()));
        game.getField().removeApple(elem.getX(), elem.getY());
        elem = game.getField().getLastAdd();
        Assertions.assertFalse(game.getField().checkAppleField(elem.getX(), elem.getY()));
        game.getField().getAppleField()[7][10] = true;
        game.makeStep(MoveDirection.LEFT);
        Assertions.assertTrue(game.isWin());
        Assertions.assertEquals(1, game.getScore());
        game.restart();
        game.getField().getWallField()[7][10] = true;
        game.getField().checkWallField(7, 10);
        game.makeStep(MoveDirection.LEFT);
        Assertions.assertTrue(game.isLoss());
        Assertions.assertEquals(0, game.getScore());
    }
}