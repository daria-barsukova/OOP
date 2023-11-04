package dbarsukova;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * task-1-3-1 substring search.
 */

public class SubstringSearch {

    /**
     * Function that defines line and index of beginning of each occurrence of given substring,
     * which can contain letters of any alphabet in UTF-8 encoding.
     *
     * @param fileName     file name.
     * @param searchString string to be found.
     */
    public static List<Integer[]> search(Reader fileName, String searchString) throws IOException {
        String substring = new String(searchString.getBytes(), StandardCharsets.UTF_8);
        List<Integer[]> arrayOfIndexes = new ArrayList<>();
        int symbol;
        int index = 0;
        int line = 0;
        int length = substring.length();
        while ((symbol = fileName.read()) != -1) {
            if (symbol == '\n') {
                line++;
                index = 0;
            } else if (symbol == substring.charAt(0)) {
                fileName.mark(length);
                int savedIndex = index;
                for (int i = 1; i < length; i++) {
                    index++;
                    if ((fileName.read()) != substring.charAt(i)) {
                        break;
                    }
                }
                arrayOfIndexes.add(new Integer[]{line, index - length + 1});
                fileName.reset();
                index = savedIndex + 1;
            } else {
                index++;
            }
        }
        return arrayOfIndexes;
    }
}
