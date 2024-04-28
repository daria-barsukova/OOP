package dbarsukova.model;

import dbarsukova.direction.MoveDirection;
import dbarsukova.snake.Body;
import dbarsukova.snake.Snake;
import dbarsukova.snake.SnakeElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Field class represents game field for snake game.
 */

public class Field {
    private Snake snake;
    private final int width;
    private final int height;
    private final int limit;
    private final List<SnakeElement> appleElem;
    private final List<SnakeElement> WallElem;
    private boolean[][] appleField;
    private boolean[][] wallField;
    private SnakeElement lastAdd;
    private SnakeElement lastDelete;

    /**
     * constructs new Field object with specified parameters.
     *
     * @param width  width of game field.
     * @param height height of game field.
     * @param limit  maximum number of food points allowed on field.
     * @throws IllegalArgumentException if any of parameters are invalid.
     */
    public Field(int width, int height,
                 int limit)
            throws IllegalArgumentException {
        if (width <= 0 || height <= 0 || limit <= 0 || limit > 100) {
            throw new IllegalArgumentException("Invalid arguments provided");
        }
        this.width = width;
        this.height = height;
        this.limit = limit;
        appleElem = new ArrayList<>();
        WallElem = new ArrayList<>();
    }

    /**
     * initializes game field, including creating snake, food and walls.
     */
    public void initField() {
        snake = new Snake(width / 2, height / 2, MoveDirection.LEFT);
        appleField = new boolean[width][height];
        wallField = new boolean[width][height];
        appleElem.clear();
        WallElem.clear();
        generateItems(limit, this::spawnApple);
        generateItems((int) Math.min(limit * 1.5,
                width * height - limit - snake.getLength() - 2), this::spawnWall);
    }

    private void generateItems(int count, Runnable generator) {
        for (int i = 0; i < count; i++) {
            generator.run();
        }
    }

    /**
     * retrieves width of game field.
     *
     * @return width of game field.
     */
    public int getWidth() {
        return width;
    }

    /**
     * retrieves height of game field.
     *
     * @return height of game field.
     */
    public int getHeight() {
        return height;
    }

    /**
     * retrieves snake object.
     *
     * @return snake object.
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * retrieves field indicating presence of food at each point on field.
     *
     * @return 2D boolean array.
     */
    public boolean[][] getAppleField() {
        return appleField;
    }

    /**
     * retrieves field indicating presence of walls at each point on field.
     *
     * @return 2D boolean array.
     */
    public boolean[][] getWallField() {
        return wallField;
    }

    /**
     * checks if specified point on field contains food.
     *
     * @param x x-coordinate of point.
     * @param y y-coordinate of point.
     * @return true if specified point contains food, false otherwise.
     * @throws IllegalArgumentException if coordinates are out of bounds.
     * @throws IllegalStateException    if appleField is not initialized.
     */
    public boolean checkAppleField(int x, int y)
            throws IllegalArgumentException, IllegalStateException {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Invalid arguments provided");
        }
        if (appleField == null) {
            throw new IllegalStateException("Invalid arguments provided");
        }
        return appleField[x][y];
    }

    /**
     * checks if specified point on field contains a wall.
     *
     * @param x x-coordinate of point.
     * @param y y-coordinate of point.
     * @return true if point contains wall, false otherwise.
     * @throws IllegalArgumentException if coordinates are out of bounds.
     */
    public boolean checkWallField(int x, int y) throws IllegalArgumentException {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Invalid arguments provided");
        }
        return wallField[x][y];
    }

    /**
     * retrieves list of food points currently present on field.
     *
     * @return list of SnakeElement objects representing food points.
     */
    public List<SnakeElement> getAppleElem() {
        return appleElem;
    }

    /**
     * retrieves list of wall points currently present on field.
     *
     * @return list of SnakeElement objects representing wall points.
     */
    public List<SnakeElement> getWallElem() {
        return WallElem;
    }

    /**
     * retrieves last food point that was added to field.
     *
     * @return last food point added to field as SnakeElement object.
     */
    public SnakeElement getLastAdd() {
        return lastAdd;
    }

    /**
     * retrieves last food point that was removed from field.
     *
     * @return last food point removed from field as SnakeElement object.
     */
    public SnakeElement getLastDelete() {
        return lastDelete;
    }

    /**
     * checks if specified point on field collides with snake.
     *
     * @param x x-coordinate of point.
     * @param y y-coordinate of point.
     * @return true if point collides with snake, false otherwise.
     */
    public boolean elemCollision(int x, int y) {
        for (int i = 0; i < 2; i++) {
            Body next = snake.nextNode(i == 0 ? snake.getHead() : snake.nextNode(snake.getHead()));
            if (next.getElem().getX() == x && next.getElem().getY() == y) {
                return true;
            }
        }
        for (Body node : snake.getSnake()) {
            if (node.getElem().getX() == x && node.getElem().getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if snake collides with itself.
     *
     * @return true if snake collides with itself, false otherwise.
     */
    public boolean snakeCollision() {
        Body head = snake.getHead();
        return snake.getSnake().subList(1, snake.getLength()).stream().anyMatch(n ->
                n.getElem().getX() == head.getElem().getX() && n.getElem().getY() == head.getElem().getY());
    }

    /**
     * generates food point on field at random location.
     */
    public void spawnApple() {
        Random r = new Random();
        int x, y;
        boolean flag;
        do {
            x = r.nextInt(width - 2) + 1;
            y = r.nextInt(height - 2) + 1;
            flag = elemCollision(x, y) || appleField[x][y] || wallField[x][y];
        } while (flag);
        appleField[x][y] = true;
        lastAdd = new SnakeElement(x, y);
        appleElem.add(lastAdd);
    }

    /**
     * removes food point at specified coordinates from field.
     *
     * @param x x-coordinate of food point to be removed.
     * @param y y-coordinate of food point to be removed.
     */
    public void removeApple(int x, int y) {
        appleField[x][y] = false;
        appleElem.removeIf(p -> {
            if (p.getX() == x && p.getY() == y) {
                lastDelete = p;
                return true;
            }
            return false;
        });
    }

    /**
     * generates wall on field at random location.
     */
    public void spawnWall() {
        Random r = new Random();
        int x, y;
        List<SnakeElement> wall = new ArrayList<>();
        do {
            x = r.nextInt(width - 2) + 1;
            y = r.nextInt(height - 2) + 1;
        } while (elemCollision(x, y) || appleField[x][y] || wallField[x][y]);
        int length = r.nextInt(Math.min(10, Math.min(width - x, height - y))) + 1;
        boolean h = r.nextBoolean();
        for (int i = 0; i < length; i++) {
            int newX = h ? x + i : x;
            int newY = h ? y : y + i;
            if (!elemCollision(newX, newY) && !appleField[newX][newY] && !wallField[newX][newY]) {
                wallField[newX][newY] = true;
                wall.add(new SnakeElement(newX, newY));
            } else {
                break;
            }
        }
        WallElem.addAll(wall);
    }
}
