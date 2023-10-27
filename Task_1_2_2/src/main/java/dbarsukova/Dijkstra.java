package dbarsukova;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Dijkstra iterator for oriented weighted graph.
 *
 * @param <T> name data type.
 * @param <U> vertex data type.
 */
public class Dijkstra<T, U extends Vertex<T>> implements Iterator<Vertex<T>> {
    private final Graph<T> graph;
    private final List<U> list;
    private final int counter;
    private final HashMap<U, Vertex.Color> color;

    /**
     * creating an iterator.
     *
     * @param graph graph.
     * @param start the first node.
     */
    @SuppressWarnings("unchecked")
    public Dijkstra(Graph<T> graph, U start) {
        list = new ArrayList<>();
        counter = graph.getCounter();
        color = new HashMap<>();
        this.graph = graph;
        for (Vertex<T> vertex : graph.getVertices()) {
            color.put((U) vertex, Vertex.Color.WHITE);
            vertex.setAncestor(null);
            vertex.setWeight(Integer.MAX_VALUE);
        }
        start.setAncestor(null);
        start.setWeight(0);
        color.put(start, Vertex.Color.BLACK);
        list.add(start);
    }

    @Override
    public boolean hasNext() {
        return !(list.isEmpty());
    }

    @SuppressWarnings("unchecked")
    @Override
    public U next() {
        int currentModification = graph.getCounter();
        if (currentModification != counter) {
            throw new ConcurrentModificationException();
        }
        U vertex = Collections.min(list);
        for (Edge edge : vertex.getStart()) {
            U neighbour = (U) edge.getEdgeEnd();
            if (edge.getWeight() + vertex.getWeight() < neighbour.getWeight()) {
                neighbour.setWeight(edge.getWeight() + vertex.getWeight());
                neighbour.setAncestor(vertex);
                if (color.get(neighbour) == Vertex.Color.WHITE) {
                    color.put(neighbour, Vertex.Color.BLACK);
                    list.add(neighbour);
                }
            }
        }
        list.remove(vertex);
        return vertex;
    }
}
