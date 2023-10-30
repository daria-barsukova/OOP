package dbarsukova;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * task-1-2-2 test.
 */
public class Tests {

    @Test
    public void test1() {
        Graph<String> graph = new Reader().readGraph("src/test/java/dbarsukova/incidence_matrix.txt", Reader.TypePresentation.INC_MATRIX);
        Assertions.assertTrue(graph.hasVertex("a"));
        Assertions.assertTrue(graph.hasVertex("b"));
        Assertions.assertTrue(graph.hasVertex("c"));
        Assertions.assertFalse(graph.hasVertex("g"));
        Assertions.assertTrue(graph.hasEdge("a", "b"));
        Assertions.assertTrue(graph.hasEdge("b", "c"));
        Assertions.assertTrue(graph.hasEdge("c", "d"));
        Assertions.assertFalse(graph.hasEdge("b", "a"));
        Assertions.assertEquals(graph.getWeight("a", "b"), 1);
        Assertions.assertEquals(graph.getWeight("b", "c"), 2);
        Assertions.assertEquals(graph.getWeight("c", "d"), 3);
    }


    @Test
    public void test2() {
        Graph<String> graph = new Reader().readGraph("src/test/java/dbarsukova/incidence_matrix.txt", Reader.TypePresentation.INC_MATRIX);
        Assertions.assertEquals(graph.getSmallestWeight("a", "d"), 6);
        Assertions.assertEquals(graph.getSmallestWeight("e", "d"), 17);
        Assertions.assertEquals(graph.getSmallestWeight("a", "a"), 0);
        Assertions.assertTrue(graph.addEdge("a", "d", 2));
        Assertions.assertEquals(graph.getSmallestWeight("a", "d"), 2);
        Assertions.assertEquals(graph.getSmallestWeight("e", "d"), 13);
        Assertions.assertFalse(graph.hasVertex("k"));
        Assertions.assertTrue(graph.addVertex("k"));
        Assertions.assertTrue(graph.hasVertex("k"));
        Assertions.assertTrue(graph.addEdge("k", "a", 3));
        Assertions.assertTrue(graph.addEdge("k", "a", 7));
        Assertions.assertEquals(graph.getSmallestWeight("k", "b"), 4);
        Assertions.assertEquals(graph.getSmallestWeight("b", "k"), -1);

        List<Vertex<String>> vertices = graph.getVertices();
        Assertions.assertEquals(graph.defineVertex("a"), vertices.get(0));
        Assertions.assertFalse(graph.removeEdge("a", "f"));
        Assertions.assertTrue(graph.setWeight("f", "a", 7));
    }

    @Test
    public void test3() {
        Graph<String> graph = new Reader().readGraph("src/test/java/dbarsukova/incidence_matrix.txt", Reader.TypePresentation.INC_MATRIX);
        Assertions.assertEquals(graph.getShortestPath("a", "f"), "f 15←--e 10←--d 6←--c 3←--b 1←--a 0");
        Assertions.assertEquals(graph.sortVertex("a"), "a(0) b(1) c(3) d(6) e(10) f(15) ");
    }

    @Test
    public void test4() {
        Graph<String> graph = new Reader().readGraph("src/test/java/dbarsukova/adjacency_matrix.txt", Reader.TypePresentation.ADJ_MATRIX);
        Assertions.assertTrue(graph.hasVertex("a"));
        Assertions.assertTrue(graph.hasVertex("b"));
        Assertions.assertTrue(graph.hasVertex("c"));
        Assertions.assertFalse(graph.hasVertex("k"));
        Assertions.assertTrue(graph.hasEdge("a", "b"));
        Assertions.assertTrue(graph.hasEdge("b", "c"));
        Assertions.assertTrue(graph.hasEdge("c", "d"));
        Assertions.assertFalse(graph.hasEdge("b", "a"));
        Assertions.assertEquals(graph.getWeight("a", "b"), 1);
        Assertions.assertEquals(graph.getWeight("b", "c"), 2);
        Assertions.assertEquals(graph.getWeight("c", "d"), 3);
    }

    @Test
    public void test5() {
        Graph<String> graph = new Reader().readGraph("src/test/java/dbarsukova/adjacency_list.txt", Reader.TypePresentation.ADJ_LIST);
        Assertions.assertTrue(graph.hasVertex("a"));
        Assertions.assertTrue(graph.hasVertex("b"));
        Assertions.assertTrue(graph.hasVertex("c"));
        Assertions.assertFalse(graph.hasVertex("g"));
        Assertions.assertTrue(graph.hasEdge("a", "b"));
        Assertions.assertTrue(graph.hasEdge("b", "c"));
        Assertions.assertTrue(graph.hasEdge("c", "d"));
        Assertions.assertFalse(graph.hasEdge("b", "a"));
        Assertions.assertEquals(graph.getWeight("a", "b"), 1);
        Assertions.assertEquals(graph.getWeight("b", "c"), 1);
        Assertions.assertEquals(graph.getWeight("c", "d"), 1);
    }
}
