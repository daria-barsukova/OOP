package dbarsukova.controller;

import java.io.File;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;


/**
 * utility class for inspecting Gradle tasks in project.
 */

public class GradleController {

    /**
     * inspects Gradle tasks in specified project directory.
     *
     * @param pathToProject path to project directory.
     */
    public static void inspectGradleTasks(String pathToProject) {
        try (ProjectConnection connection = GradleConnector.newConnector()
                .forProjectDirectory(new File(pathToProject))
                .connect()) {
            BuildLauncher build = connection.newBuild();
            configureTasks(build, "build", "test", "javadoc");
            build.run();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * configures Gradle tasks for execution.
     *
     * @param build BuildLauncher object.
     * @param tasks Gradle tasks to be executed.
     */
    private static void configureTasks(BuildLauncher build, String... tasks) {
        build.forTasks(tasks);
    }
}
