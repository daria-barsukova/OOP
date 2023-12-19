package dbarsukova;


/**
 * class representing Note that includes date, title, and content.
 */

public class Note {
    String date;
    String title;
    String content;

    public Note(String date, String title, String content) {
        this.date = date;
        this.title = title;
        this.content = content;
    }
}