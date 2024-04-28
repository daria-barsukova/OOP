package dbarsukova;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.util.Objects;


/**
 * View class handles visual representation of Snake game.
 * it provides methods for drawing various game elements such as the snake, food and game overlays.
 */

public class View {
    private final int blockSize;
    private final int foodSize;
    private final int width;
    private final int height;
    private Text score;
    private final Image head;
    private final Image body;

    /**
     * constructs new View object with specified width, height and block size.
     *
     * @param width     width of game area in blocks.
     * @param height    height of game area in blocks.
     * @param blockSize size of each block in pixels.
     */
    public View(int width, int height, int blockSize) {
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        foodSize = 20;
        head = new Image(
                Objects.requireNonNull(View.class.getResourceAsStream("/images/head.png")));
        body = new Image(
                Objects.requireNonNull(View.class.getResourceAsStream("/images/body.png")));
    }

    /**
     * draws circular food element at specified position with given color.
     *
     * @param x     x-coordinate of center of food.
     * @param y     y-coordinate of center of food.
     * @param color color of food.
     * @return Node representing food element.
     */
    public Node drawCircle(int x, int y, Color color) {
        Circle circle = new Circle((double) blockSize / 2, (double) blockSize / 2, (double) foodSize / 2);
        circle.setLayoutX(blockSize * x);
        circle.setLayoutY(blockSize * y);
        circle.setFill(color);
        circle.setStroke(color.darker());
        circle.setStrokeWidth(2);
        return circle;
    }

    /**
     * draws rectangular block element at specified position with given color.
     *
     * @param x     x-coordinate of top-left corner of block.
     * @param y     y-coordinate of top-left corner of block.
     * @param color color of block.
     * @return Node representing block element.
     */
    public Node drawBlock(int x, int y, Color color) {
        Rectangle r = new Rectangle(blockSize - 2, blockSize - 2);
        r.setFill(color);
        r.setLayoutX(x * blockSize);
        r.setLayoutY(y * blockSize);
        r.setStroke(color.darker());
        r.setStrokeWidth(2);
        return r;
    }

    /**
     * draws snake's head at specified position with given rotation angle.
     *
     * @param x      x-coordinate of head.
     * @param y      y-coordinate of head.
     * @param rotate rotation angle of head.
     * @return Node representing snake's head.
     */
    public Node drawHead(int x, int y, int rotate) {
        return drawImage(head, x, y, rotate);
    }

    /**
     * draws segment of snake's body at specified position with given rotation angle.
     *
     * @param x      x-coordinate of body segment.
     * @param y      y-coordinate of body segment.
     * @param rotate rotation angle of body segment.
     * @return Node representing snake's body segment.
     */
    public Node drawBody(int x, int y, int rotate) {
        return drawImage(body, x, y, rotate);
    }

    /**
     * draws image at specified position with given rotation angle.
     *
     * @param image  image to draw.
     * @param x      x-coordinate of image.
     * @param y      y-coordinate of image.
     * @param rotate rotation angle of image.
     * @return Node representing image.
     */
    public Node drawImage(Image image, int x, int y, int rotate) {
        Node node = new ImageView(image);
        node.setRotate(rotate);
        node.setLayoutX(x * blockSize);
        node.setLayoutY(y * blockSize);
        return node;
    }

    /**
     * converts pixel coordinates to block coordinates.
     *
     * @param cord coordinate value in pixels.
     * @return corresponding block coordinate.
     */
    public int getCord(int cord) {
        return cord / blockSize;
    }

    /**
     * retrieves score text node.
     *
     * @return Text node representing score.
     */
    public Text getScore() {
        return score;
    }

    /**
     * initializes pause screen.
     *
     * @return Pane representing pause screen.
     */
    public Pane initPause() {
        Rectangle r = new Rectangle(width * blockSize, height * blockSize);
        r.setOpacity(0.75);
        r.setFill(Color.DARKGRAY);
        Text text = new Text("PAUSED");
        text.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 60));
        text.setFill(Color.WHITE);
        text.setWrappingWidth(width * blockSize);
        text.setTextAlignment(TextAlignment.CENTER);
        StackPane pauseContainer = new StackPane();
        pauseContainer.getChildren().addAll(r, text);
        return pauseContainer;
    }

    /**
     * initializes main menu screen.
     *
     * @param goal goal score.
     * @return Pane representing main menu screen.
     */
    public Pane initMenu(int goal) {
        StackPane menuPane = new StackPane();
        Rectangle menuBack = new Rectangle(250, height * blockSize);
        menuBack.setFill(Color.SILVER);
        menuPane.setLayoutX(width * blockSize);
        menuBack.setStroke(Color.SILVER.darker());
        menuBack.setStrokeWidth(2);
        menuBack.setManaged(false);
        Text h = new Text("GAME STATUS");
        h.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 24));
        h.setFill(Color.DARKBLUE);
        Text food = new Text("Food eaten:");
        food.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        score = new Text(String.format("0 / %d", goal));
        score.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        score.setFill(Color.DARKBLUE);
        Text helpText = new Text("New game: ENTER\n"
                + "Pause: ESC\n");
        helpText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        VBox menu = new VBox();
        menu.setPrefSize(250, height * blockSize);
        menu.setAlignment(Pos.TOP_LEFT);
        menu.setSpacing(10);
        menu.getChildren().addAll(h, food, score, helpText);
        menu.setPadding(new Insets(15));
        menuPane.getChildren().addAll(menuBack, menu);
        return menuPane;
    }

    /**
     * initializes game over screen.
     *
     * @return Pane representing game over screen.
     */
    public Pane initGameOver() {
        Rectangle r = new Rectangle(width * blockSize, height * blockSize);
        r.setOpacity(0.75);
        r.setFill(Color.DARKRED);
        Text text = new Text("GAME OVER");
        text.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 60));
        text.setFill(Color.WHITE);
        text.setWrappingWidth(width * blockSize);
        text.setTextAlignment(TextAlignment.CENTER);
        Pane menuPane = new StackPane();
        menuPane.getChildren().addAll(r, text);
        return menuPane;
    }

    /**
     * initializes victory screen.
     *
     * @return Pane representing victory screen.
     */
    public Pane initVictory() {
        Rectangle r = new Rectangle(width * blockSize, height * blockSize);
        r.setOpacity(0.65);
        r.setFill(Color.DARKGREEN);
        Text text = new Text("VICTORY!");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 60));
        text.setWrappingWidth(width * blockSize);
        text.setTextAlignment(TextAlignment.CENTER);
        Pane menuPane = new StackPane();
        menuPane.getChildren().addAll(r, text);
        return menuPane;
    }
}
