package dbarsukova;

import dbarsukova.application.Application;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


/**
 * task-2-2-1 runner test.
 */

public class RunnerTest {
    @Test
    void test1() {
        Application pizzeriaApp = new Application();
        assertDoesNotThrow(pizzeriaApp::run);
    }

    @Test
    void test2() {
        Application pizzeriaApp = new Application();
        assertDoesNotThrow(pizzeriaApp::createFile);
    }

    @Test
    void test3() {
        Application pizzeriaApp = new Application();
        assertDoesNotThrow(pizzeriaApp::getConfig);
    }
}