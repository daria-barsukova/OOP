package dbarsukova;

import dbarsukova.controller.DslParser;
import dbarsukova.controller.GitManager;
import dbarsukova.controller.GradleController;
import dbarsukova.controller.HtmlReportConstructor;
import dbarsukova.model.Group;
import dbarsukova.model.Student;
import dbarsukova.model.StudentTask;
import dbarsukova.model.Task;
import dbarsukova.model.TestConfiguration;
import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;


/**
 * application is responsible for inspecting and evaluating tasks for students.
 * it parses configuration, clones student repositories, inspects Gradle tasks
 * and generates reports.
 */

public class App {
    private static final String CONFIG_PATH = "/config.groovy";
    private static final String LABS_PATH = "Task_2_4_1/src/main/resources/labs/";
    private static final String RESOURCES_PATH = "Task_2_4_1/src/main/resources/";
    private static final int TEST_TIMEOUT_SECONDS = 10;

    /**
     * main method initiates task inspection and evaluation process.
     *
     * @param args command line arguments.
     * @throws URISyntaxException if there is URI syntax error.
     * @throws DocumentException  if there is error parsing XML document.
     */
    public static void main(String[] args) throws URISyntaxException, DocumentException {
        TestConfiguration configuration = DslParser.parseConfig(CONFIG_PATH);
        System.out.println(configuration);
        List<Task> tasksToCheck = getTasksToCheck(configuration);
        HtmlReportConstructor reportConstructor = new HtmlReportConstructor();
        for (Group group : configuration.getGroups()) {
            initializeTaskProgress(tasksToCheck);
            for (Student student : group.getStudents()) {
                GitManager.cloneRepository(student.getRepository(), student.getId(), LABS_PATH);
                for (Task task : tasksToCheck) {
                    inspectAndAssignTask(student, task);
                }
            }
            reportConstructor.generateReport(group, tasksToCheck, RESOURCES_PATH);
        }
    }

    /**
     * retrieves list of tasks to be checked based on configuration.
     *
     * @param configuration test configuration.
     * @return list of tasks to be checked.
     */
    private static List<Task> getTasksToCheck(TestConfiguration configuration) {
        return configuration.getTasks().stream()
                .filter(task -> configuration.getHandout().contains(task.getId()))
                .collect(Collectors.toList());
    }

    /**
     * initializes progress of each task to empty list.
     *
     * @param tasks list of tasks.
     */
    private static void initializeTaskProgress(List<Task> tasks) {
        for (Task task : tasks) {
            task.setProgress(new ArrayList<>());
        }
    }

    /**
     * inspects and assigns task for given student.
     *
     * @param student student.
     * @param task    task.
     * @throws DocumentException if there is error parsing XML document.
     */
    private static void inspectAndAssignTask(Student student, Task task) throws DocumentException {
        String studentTaskPath = LABS_PATH + student.getId() + "/" + task.getId();
        GradleController.inspectGradleTasks(studentTaskPath);
        StudentTask studentTask = new StudentTask();
        studentTask.setStudent(student.getFullName());
        File testResultsDir = new File(studentTaskPath + "/build/test-results/test/");
        File testReport = getTestReport(testResultsDir);
        studentTask.setBuild(testResultsDir.getParentFile().getParentFile().isDirectory());
        studentTask.setDocGenerated(isDocumentationGenerated(testResultsDir
                        .getParentFile()
                        .getParentFile()));
        parseTestReport(testReport, studentTask);
        double score = calculateScore(studentTask, task);
        studentTask.setScore(String.format("%.1f",
                Math.ceil((score * task.getMaxPoints()) * 2) / 2));
        task.getProgress().add(studentTask);
    }

    /**
     * retrieves test report file from test results directory.
     *
     * @param testResultsDir test results directory.
     * @return test report file.
     */
    private static File getTestReport(File testResultsDir) {
        return Arrays.stream(Objects.requireNonNull(testResultsDir.listFiles()))
                .filter(file -> file.isFile() && file.getName().endsWith(".xml"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Test report not found"));
    }

    /**
     * checks if documentation is generated in build directory.
     *
     * @param buildDir build directory.
     * @return true if documentation is generated, false otherwise.
     */
    private static boolean isDocumentationGenerated(File buildDir) {
        return Arrays.stream(Objects.requireNonNull(buildDir.listFiles()))
                .anyMatch(file -> file.getName().equals("docs"));
    }

    /**
     * parses test report and updates assigned task with test results.
     *
     * @param testReport   test report file.
     * @param studentTask assigned task.
     * @throws DocumentException if there is error parsing XML document.
     */
    private static void parseTestReport(File testReport, StudentTask studentTask)
            throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(testReport);
        int totalTests = Integer.parseInt(document
                .selectSingleNode("testsuite").valueOf("@tests"));
        int failedTests = Integer.parseInt(document
                .selectSingleNode("testsuite").valueOf("@failures"));
        int ignoredTests = Integer.parseInt(document
                .selectSingleNode("testsuite").valueOf("@skipped"));
        studentTask.setTestsCount(totalTests);
        studentTask.setTestsPassed(totalTests - failedTests);
        List<Node> testCases = document.selectNodes("//testcase");
        for (Node testCase : testCases) {
            double duration = Double.parseDouble(testCase.valueOf("@time"));
            if (duration > TEST_TIMEOUT_SECONDS) {
                ignoredTests++;
            }
        }
        studentTask.setTestsIgnored(ignoredTests);
    }

    /**
     * calculates score for assigned task based on various criteria.
     *
     * @param studentTask assigned task.
     * @param task         task.
     * @return calculated score.
     */
    private static double calculateScore(StudentTask studentTask, Task task) {
        double score = -0.5;
        if (studentTask.isBuild() && studentTask.isDocGenerated()
                && studentTask.getTestsCount() == studentTask.getTestsPassed()
                + studentTask.getTestsIgnored()) {
            score += 0.5;
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("dd-MM-yyyy").withLocale(Locale.ENGLISH);
            if (LocalDate.parse(task.getSoftDeadline(), formatter).isAfter(LocalDate.now())) {
                studentTask.setSoftDeadline(true);
                score += 0.5;
            }
            if (LocalDate.parse(task.getHardDeadline(), formatter).isAfter(LocalDate.now())) {
                studentTask.setHardDeadline(true);
                score += 0.5;
            }
        }
        return score;
    }
}
