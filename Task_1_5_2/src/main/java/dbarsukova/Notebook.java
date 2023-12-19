package dbarsukova;

import static dbarsukova.Handler.addNote;
import static dbarsukova.Handler.showNote;
import static dbarsukova.Handler.removeNote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import picocli.CommandLine;
import picocli.CommandLine.Command;


/**
 * task-1-5-2 notebook.
 */

@Command(name = "Notebook", mixinStandardHelpOptions = true)
public class Notebook {

    /**
     * runs book from command line.
     *
     * @param args args
     */
    public static void main(String[] args) {
        new CommandLine(new Notebook()).execute(args);
    }

    /**
     * retrieves contents of JSON-notebook as list.
     */
    public List<Note> getNotes() {
        File file = new File("book.json");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                Type type = new TypeToken<List<Note>>() {
                }.getType();
                Gson gson = new GsonBuilder().create();
                ArrayList<Note> list = gson.fromJson(reader, type);
                if (list == null) {
                    list = new ArrayList<>();
                }
                return list;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * converts list of notes to JSON file.
     *
     * @param list list of notes.
     */
    public void writeNotes(List<Note> list) {
        try {
            Files.write(Paths.get("book.json"),
                    new Gson().toJson(list).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * adds note to book.
     *
     * @param title   title of note.
     * @param content content of note.
     */
    @Command(name = "add", description = "Add new record")
    public void add(String title, String content) {
        List<Note> list = getNotes();
        addNote(title, content, list);
        writeNotes(list);
    }

    /**
     * deletes notes from book.
     *
     * @param names of the notes to remove
     */
    @Command(name = "remove", description = "Remove records")
    public void remove(String[] names) {
        List<Note> list = getNotes();
        if (names == null) {
            writeNotes(new ArrayList<>());
        } else {
            removeNote(names, list);
            writeNotes(list);
        }
    }

    /**
     * Shows either entire contents of book when no arguments
     * were provided, or only those entries that were added
     * between date in first element of the array and second and
     * contain all other strings as substrings in their headers.
     *
     * @param show arguments.
     */
    @Command(name = "show", description = "Show records")
    public void show(String[] show) {
        List<Note> list = getNotes();
        if (show == null) {
            for (Note note : list) {
                System.out.println(note.date + " " + note.title + " " + note.content);
            }
        } else if (show.length < 2) {
            throw new IllegalArgumentException("not enough arguments");
        } else {
            showNote(show, list);
        }
    }
}