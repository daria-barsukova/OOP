package dbarsukova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


/**
 * task-1-4-1 test.
 */

public class GradebookTests {

    @Test
    void test1() {
        Gradebook gradebook = new Gradebook(220636, "Daria", "Barsukova", "FIT", 3);
        gradebook.addNote("Algebra and analysis", "Rudometova A. S.", 5, 1);
        gradebook.addNote("Mathematical logic", "Morozov D. A.", 5, 1);
        gradebook.addNote("Declarative programming", "Vlasov V. N.", 5, 1);
        gradebook.addNote("History", "Oplakanskaya R. V.", 5, 1);
        gradebook.addNote("Discrete mathematics", "Apanovich Z. V.", 5, 2);
        Note note1 = new Note("Imperative programming", "Kirin D. L.", 4, 1);
        gradebook.addNote(note1);
        Note note2 = new Note("Imperative programming", "Kirin D. L.", 4, 2);
        gradebook.addNote(note2);
        Note note3 = new Note("Digital platforms", "Nazarov A.D.", 5, 2);
        gradebook.addNote(note3);
        assertEquals(2, note2.semester);
        assertEquals("Kirin D. L.", note2.teacher);
        assertEquals(5, note3.mark);
        assertEquals("Digital platforms", note3.subject);
        assertTrue(gradebook.averageMark() - 4.75 < 0.01);
        assertTrue(gradebook.possibilityOfRedDiploma());
        assertFalse(gradebook.possibilityOfIncreasedScholarship());
        assertEquals("Gradebook number: 220636\nStudent: Daria Barsukova\nFaculty: FIT\n", gradebook.toString());
    }
}