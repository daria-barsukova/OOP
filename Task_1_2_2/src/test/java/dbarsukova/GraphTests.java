package dbarsukova;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * task-1-2-2 test.
 */
public class GraphTests {

    @Test
    public void test1() {
        Graph<String, Integer> graph = new AdjacencyMatrix<>();
        Assertions.assertThrows(NullPointerException.class,
                () -> graph.addEdge(null, null, 1));
        Assertions.assertThrows(NullPointerException.class,
                () -> graph.getVertex(null));
        Assertions.assertThrows(NullPointerException.class,
                () -> new Edge<>(null, null, 1));
        Assertions.assertThrows(NullPointerException.class,
                () -> new Vertex<String>(null));
    }

    @Test
    public void test2() {
        AdjacencyMatrix<String, Integer> graph = new AdjacencyMatrix<>();
        Vertex<String> a = graph.addVertex("A");
        Vertex<String> b = graph.addVertex("B");
        Vertex<String> c = graph.addVertex("C");
        graph.addEdge(a, b, 1);
        graph.addEdge(a, c, 2);
        for (Edge<String, Integer> edge : graph.getEdge()) {
            Assertions.assertTrue(graph.getEdge().contains(edge));
        }
        Assertions.assertEquals(3, graph.numberOfVertices());
        Assertions.assertEquals(2, graph.numberOfEdges());
    }

    @Test
    public void test3() {
        IncidenceMatrix<String, Integer> graph = new IncidenceMatrix<>();
        Vertex<String> a = graph.addVertex("A");
        Vertex<String> b = graph.addVertex("B");
        Vertex<String> c = graph.addVertex("C");
        graph.addEdge(a, b, 1);
        graph.addEdge(a, c, 2);
        for (Edge<String, Integer> edge : graph.getEdge()) {
            Assertions.assertTrue(graph.getEdge().contains(edge));
        }
        Assertions.assertEquals(3, graph.numberOfVertices());
        Assertions.assertEquals(2, graph.numberOfEdges());
    }

    @Test
    public void test4() {
        AdjacencyList<String, Integer> graph = new AdjacencyList<>();
        Vertex<String> a = graph.addVertex("A");
        Vertex<String> b = graph.addVertex("B");
        Vertex<String> c = graph.addVertex("C");
        graph.addEdge(a, b, 1);
        graph.addEdge(a, c, 2);
        for (Edge<String, Integer> edge : graph.getEdge()) {
            Assertions.assertTrue(graph.getEdge().contains(edge));
        }
        Assertions.assertEquals(3, graph.numberOfVertices());
        Assertions.assertEquals(2, graph.numberOfEdges());
    }

    @Test
    public void test5() {
        Graph<String, Integer> graph = new AdjacencyMatrix<>();
        Reader.read(graph, "/TestGraph.txt");
        Vertex<String> v1 = graph.getVertex("C");
        Vertex<String> v2 = graph.getVertex("D");
        Assertions.assertEquals("C", v1.getData());
        Assertions.assertEquals("D", v2.getData());
        Assertions.assertEquals(2, graph.getEdge(v1, v2).getWeight());
    }

    @Test
    public void test6() {
        Graph<String, Integer> graph = new IncidenceMatrix<>();
        Reader.read(graph, "/TestGraph.txt");
        Vertex<String> v1 = graph.getVertex("A");
        Vertex<String> v2 = graph.getVertex("B");
        Assertions.assertEquals("A", v1.getData());
        Assertions.assertEquals("B", v2.getData());
        Assertions.assertEquals(5, graph.getEdge(v1, v2).getWeight());
    }

    @Test
    public void test7() {
        Graph<String, Integer> graph = new AdjacencyList<>();
        Reader.read(graph, "/TestGraph.txt");
        Vertex<String> v1 = graph.getVertex("A");
        Vertex<String> v2 = graph.getVertex("B");
        Assertions.assertEquals("A", v1.getData());
        Assertions.assertEquals("B", v2.getData());
        Assertions.assertEquals(5, graph.getEdge(v1, v2).getWeight());
    }

    @Test
    public void test8() {
        Graph<String, Integer> graph = new AdjacencyList<>();
        Reader.read(graph, "/TestGraph.txt");
        Vertex<String> v = graph.getVertex("C");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("A", 14);
        expected.put("B", 10);
        expected.put("C", 0);
        expected.put("D", 2);
        Map<String, Integer> vrts = SortByDistanceFromVertex.sort(v, graph);
        Assertions.assertEquals(expected, vrts);
    }

    @Test
    public void test9() {
        Graph<String, Integer> graph = new AdjacencyMatrix<>();
        Reader.read(graph, "/TestGraph.txt");
        Vertex<String> v = graph.getVertex("C");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("A", 14);
        expected.put("B", 10);
        expected.put("C", 0);
        expected.put("D", 2);
        Map<String, Integer> vrts = SortByDistanceFromVertex.sort(v, graph);
        Assertions.assertEquals(expected, vrts);
    }

    @Test
    public void test10() {
        Graph<String, Integer> graph = new IncidenceMatrix<>();
        Reader.read(graph, "/TestGraph.txt");
        Vertex<String> v = graph.getVertex("C");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("A", 14);
        expected.put("B", 10);
        expected.put("C", 0);
        expected.put("D", 2);
        Map<String, Integer> vrts = SortByDistanceFromVertex.sort(v, graph);
        Assertions.assertEquals(expected, vrts);
    }
}
