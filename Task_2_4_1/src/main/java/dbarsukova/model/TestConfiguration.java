package dbarsukova.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * represents configuration for tests, containing information
 * about tasks, groups, handouts, checkpoints and seminars.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TestConfiguration extends GroovySupport {
    private ArrayList<Task> tasks;
    private List<Group> groups;
    private List<String> handout;
    private List<String> checkpoints;
    private List<String> seminars;
}
