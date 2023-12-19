package dbarsukova;


/**
 * class representing Note that includes date, title, and content.
 */

public class Note {
    String date;
    String title;
    String content;

    /**
     * creates object Note.
     *
     * @param date date of note.
     * @param title title of note.
     * @param content content of note.
     */
    public Note(String date, String title, String content) {
        this.date = date;
        this.title = title;
        this.content = content;
    }
}