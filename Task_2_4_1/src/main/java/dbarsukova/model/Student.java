package dbarsukova.model;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * represents student with identifier, full name and repository information.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Student extends GroovySupport {
    private String id;
    private String fullName;
    private String repository;
}
