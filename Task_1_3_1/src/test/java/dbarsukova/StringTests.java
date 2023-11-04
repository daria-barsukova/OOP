package dbarsukova;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * task-1-3-1 test.
 */
public class StringTests {
    @Test
    public void test1() throws IOException {
        List<Integer[]> expected = new ArrayList<>();
        List<Integer[]> actual;
        expected.add(new Integer[]{0, 1});
        expected.add(new Integer[]{0, 8});
        try (Reader file = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().
                        getResourceAsStream("test1.txt")), UTF_8))) {
            actual = SubstringSearch.search(file, "бра");
        }
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i)[0], actual.get(i)[0]);
            Assertions.assertEquals(expected.get(i)[1], actual.get(i)[1]);
        }
    }

    @Test
    public void test2() throws IOException {
        List<Integer[]> expected = new ArrayList<>();
        List<Integer[]> actual;
        try (Reader file = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader()
                        .getResourceAsStream("test2.txt")), UTF_8))) {
            actual = SubstringSearch.search(file, "");
        }
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i)[0], actual.get(i)[0]);
            Assertions.assertEquals(expected.get(i)[1], actual.get(i)[1]);
        }
    }

    @Test
    public void test3() throws IOException {
        List<Integer[]> expected = new ArrayList<>();
        List<Integer[]> actual;
        expected.add(new Integer[]{0, 0});
        expected.add(new Integer[]{0, 1});
        expected.add(new Integer[]{0, 2});
        expected.add(new Integer[]{0, 3});
        expected.add(new Integer[]{0, 4});
        expected.add(new Integer[]{0, 5});
        expected.add(new Integer[]{0, 6});
        expected.add(new Integer[]{0, 7});
        expected.add(new Integer[]{0, 8});
        expected.add(new Integer[]{1, 0});
        try (Reader file = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader()
                        .getResourceAsStream("test3.txt")), UTF_8))) {
            actual = SubstringSearch.search(file, "AA");
        }
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i)[0], actual.get(i)[0]);
            Assertions.assertEquals(expected.get(i)[1], actual.get(i)[1]);
        }
    }
}
