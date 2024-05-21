package dbarsukova.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * represents group contains list of students.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Group extends GroovySupport {
    private String id;
    private List<Student> students;
}
