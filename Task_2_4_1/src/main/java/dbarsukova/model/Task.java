package dbarsukova.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * represents task with various attributes such as identifier, name, deadlines,
 * maximum points and progress of student tasks.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Task extends GroovySupport {
    private String id;
    private String name;
    private int maxPoints;
    private String softDeadline;
    private String hardDeadline;
    private List<StudentTask> progress;
}
