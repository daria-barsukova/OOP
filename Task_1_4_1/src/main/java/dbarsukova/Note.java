package dbarsukova;


/**
 * Class for entry into gradebook.
 */

public class Note {
    public int mark;
    public int semester;
    public String subject;
    public String teacher;

    /**
     * creates record.
     *
     * @param subject  subject for which mark is set
     * @param teacher  teacher of subject
     * @param mark     mark
     * @param semester semester number
     */
    public Note(String subject, String teacher, int mark, int semester) {
        this.subject = subject;
        this.teacher = teacher;
        this.mark = mark;
        this.semester = semester;
    }
}
