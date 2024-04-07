package dbarsukova;
import dbarsukova.application.Application;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RunnerTest {

        @Test
        void testRun() throws IOException {
            Application pizzeriaApp = new Application();

            assertDoesNotThrow(pizzeriaApp::run);
        }

        @Test
        void testCreateFile() throws IOException {
            Application pizzeriaApp = new Application();

            assertDoesNotThrow(pizzeriaApp::createFile);
        }

        @Test
        void testGetConfigurator() throws IOException {
            Application pizzeriaApp = new Application();

            assertDoesNotThrow(pizzeriaApp::getConfig);
        }




}