package dbarsukova.model;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * represents task assigned to student with various attributes indicating
 * task's status and evaluation results.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class StudentTask extends GroovySupport {
    private String student;
    private boolean build;
    private boolean docGenerated;
    private boolean softDeadline = false;
    private boolean hardDeadline = false;
    private int testsCount;
    private int testsPassed;
    private int testsIgnored;
    private String score;
}
