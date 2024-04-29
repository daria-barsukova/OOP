package dbarsukova.controller;

import dbarsukova.View;
import dbarsukova.model.Field;
import dbarsukova.snake.Snake;
import dbarsukova.snake.SnakeElement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


/**
 * FieldController class manages graphical representation of
 * game field and its elements.
 */

public class FieldController {
    private final View view;
    private final Field field;
    private final Pane fieldPane;
    private List<Node> snakeNodes;

    /**
     * constructs FieldController with specified field, block size and view.
     *
     * @param field game field.
     * @param size  size of each block on field.
     * @param view  view used for graphical rendering.
     * @throws IllegalArgumentException if size is less than or equal to 0 or view is null.
     */
    public FieldController(Field field, int size, View view) {
        if (size <= 0 || view == null) {
            throw new IllegalArgumentException("Invalid arguments provided");
        }
        this.field = field;
        fieldPane = new Pane();
        fieldPane.setPrefSize(field.getWidth() * size, field.getHeight() * size);
        this.view = view;
    }

    /**
     * gets pane containing game field.
     *
     * @return pane containing game field.
     */
    public Pane getFieldPane() {
        return fieldPane;
    }

    /**
     * draws entire game field including food points, barrier points and snake.
     */
    public void drawField() {
        fieldPane.getChildren().clear();
        field.getAppleElem().forEach(elem ->
                fieldPane.getChildren().add(
                        view.drawCircle(elem.getX(), elem.getY(), Color.BROWN))
        );
        field.getWallElem().forEach(elem ->
                fieldPane.getChildren().add(
                        view.drawBlock(elem.getX(), elem.getY(), Color.GRAY))
        );
        snakeNodes = drawSnake(field.getSnake());
        fieldPane.getChildren().addAll(snakeNodes);
    }

    /**
     * draws snake on field.
     *
     * @param snake snake to be drawn.
     * @return list of nodes representing snake.
     * @throws IllegalArgumentException if snake is null.
     */
    public List<Node> drawSnake(Snake snake) {
        if (snake == null) {
            throw new IllegalArgumentException("Invalid arguments provided");
        }
        List<Node> nodes = new ArrayList<>();
        snake.getSnake().forEach(part -> {
            int turn = TurnController.calculateRotation(part.getDir());
            if (part == snake.getHead()) {
                nodes.add(view.drawHead(part.getElem().getX(), part.getElem().getY(), turn));
            } else {
                nodes.add(view.drawBody(part.getElem().getX(), part.getElem().getY(), turn));
            }
        });
        return nodes;
    }

    /**
     * updates graphical representation of snake on field.
     */
    public void updateSnake() {
        fieldPane.getChildren().removeAll(snakeNodes);
        snakeNodes = drawSnake(field.getSnake());
        fieldPane.getChildren().addAll(snakeNodes);
    }

    /**
     * draws food point on field.
     *
     * @param elem food point to be drawn.
     */
    public void drawApple(SnakeElement elem) {
        fieldPane.getChildren().add(view.drawCircle(elem.getX(), elem.getY(), Color.BROWN));
    }

    /**
     * deletes food point from field.
     *
     * @param elem food point to be deleted.
     */
    public void deleteApple(SnakeElement elem) {
        fieldPane.getChildren().removeIf(node ->
                view.getCord((int) node.getLayoutX()) == elem.getX() && view.getCord((int) node.getLayoutY()) == elem.getY());
    }
}
