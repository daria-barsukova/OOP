package dbarsukova;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Handler class is abstract class that provides methods for
 * managing list of notes. It includes functions for adding,
 * deleting, and displaying notes based on certain criteria.
 */

public abstract class Handler {
    public static final SimpleDateFormat format =
            new SimpleDateFormat("dd.MM.yyyy HH:mm");

    /**
     * adds record to book.
     *
     * @param title   title of note.
     * @param content content of note.
     * @param list    list of notes.
     */
    public static void addNote(String title, String content, List<Note> list)
            throws RuntimeException {
        Note note = new Note(format.format(new Date()), title, content);
        list.add(note);
    }

    /**
     * deletes records with these headers.
     *
     * @param titles titles of notes.
     * @param list   list of notes.
     */
    public static void removeNote(String[] titles, List<Note> list)
            throws RuntimeException {
        List<String> nameList = Arrays.asList(titles);
        Iterator<Note> iterator = list.iterator();
        while (iterator.hasNext()) {
            Note note = iterator.next();
            if (nameList.contains(note.title)) {
                iterator.remove();
            }
        }
    }

    /**
     * prints detailed information about notes, which correspond
     * to specified date range and keywords of title.
     *
     * @param args array of strings.
     * @param list list of notes.
     */
    public static void showNote(String[] args, List<Note> list)
            throws RuntimeException {
        try {
            Date start = format.parse(args[0]);
            Date end = format.parse(args[1]);
            if (start == null || end == null) {
                throw new IllegalArgumentException("incorrect date");
            }
            for (Note note : list) {
                Date currDate = format.parse(note.date);
                if (!currDate.before(start) && !currDate.after(end)) {
                    for (int i = 2; i < args.length; i++) {
                        if (note.title.contains(args[i])) {
                            System.out.println(note.date + " "
                                    + note.title + " " + note.content);
                            break;
                        }
                    }
                }
            }
        } catch (ParseException exc) {
            throw new IllegalArgumentException("incorrect date");
        }
    }
}
