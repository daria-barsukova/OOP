package dbarsukova;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * task-1-5-2 test.
 */

public class BookTests {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpOut() {
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    public void cleanOut() {
        System.setOut(System.out);
    }

    @Test
    void test1() {
        Notebook.main(new String[]{"remove"});
        Date date = new Date();
        Notebook.main(new String[]{"add", "Моя заметка", "Очень важная заметка"});
        Notebook.main(new String[]{"show"});
        assertEquals(Handler.format.format(date) + " Моя заметка Очень важная заметка"
                + System.lineSeparator(), out.toString());
        Notebook.main(new String[]{"show", Handler.format.format(date),
                Handler.format.format(date), "Моя"});
        assertEquals(Handler.format.format(date) + " Моя заметка Очень важная заметка"
                + System.lineSeparator()
                + Handler.format.format(date) + " Моя заметка Очень важная заметка"
                + System.lineSeparator(), out.toString());
        Notebook.main(new String[]{"remove", "Моя заметка"});
        Notebook.main(new String[]{"show"});
        assertEquals(Handler.format.format(date) + " Моя заметка Очень важная заметка"
                + System.lineSeparator()
                + Handler.format.format(date) + " Моя заметка Очень важная заметка"
                + System.lineSeparator(), out.toString());
    }
}
