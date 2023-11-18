package dbarsukova;

import java.util.ArrayList;
import java.util.List;


/**
 * task-1-4-1 gradebook.
 */

public class Gradebook {
    private final int number;
    private final int semester;
    private final String name;
    private final String surname;
    private final String faculty;
    private final List<Note> data;

    /**
     * creating a gradebook.
     *
     * @param number gradebook number
     * @param name student's name
     * @param surname student's surname
     * @param faculty faculty
     * @param semester semester number
     */
    public Gradebook(int number, String name, String surname, String faculty, int semester) {
        this.number = number;
        this.name = name;
        this.surname = surname;
        this.faculty = faculty;
        this.semester = semester;
        this.data = new ArrayList<>();
    }

    /**
     * @param note entry in gradebook.
     */
    public void addNote(Note note) {
        data.add(note);
    }

    /**
     * @param subject subject for which mark is set
     * @param teacher teacher of subject
     * @param mark mark
     * @param semester semester number
     */
    public void addNote(String subject, String teacher, int mark, int semester) {
        Note note = new Note(subject, teacher, mark, semester);
        addNote(note);
    }

    /**
     * getting current average score for entire studying period.
     */
    public float averageMark() {
        float res = 0;
        for (Note note : data) {
            res += (float) note.mark / data.size();
        }
        return res;
    }

    /**
     * possibility of obtaining a "red" diploma with honors.
     * <p>
     * requirements for diploma with honors: 75% of grades in diploma supplement
     * (the last grade) are excellent, absence of final grades is satisfactory and
     * qualification work is excellent.
     */
    public boolean possibilityOfRedDiploma() {
        if (this.averageMark() < 4.75) {
            return false;
        }
        for (Note note : data) {
            if (note.mark == 3) {
                return false;
            }
        }
        return data.get(data.size() - 1).mark == 5;
    }

    /**
     * possibility of receiving increased scholarship this semester.
     */
    public boolean possibilityOfIncreasedScholarship() {
        float sum = 0;
        int k = 0;
        for (Note note : data) {
            if (note.semester == this.semester - 1) {
                sum += (float) note.mark;
                ++k;
            }
        }
        return sum / k == 5.0;
    }

    /**
     * representation of notes in string format.
     */
    public String toString() {
        return String.format("Gradebook number: %d\nStudent: %s %s\nFaculty: %s\n",
                number,
                name,
                surname,
                faculty);
    }
}
