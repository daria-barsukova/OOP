package dbarsukova;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Implementation on adjacency matrix.
 */

public class AdjacencyMatrix<V, E extends Number> implements Graph<V, E> {
    private int numOfVert;
    private int numOfEdg;
    private final HashMap<Vertex<V>, HashMap<Vertex<V>, Edge<V, E>>> matrix;

    /**
     * creating an empty adjacency matrix.
     */
    public AdjacencyMatrix() {
        matrix = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(V data) {
        Vertex<V> vert = new Vertex<>(data);
        matrix.putIfAbsent(vert, new HashMap<>());
        ++numOfVert;
        return vert;
    }

    @Override
    public void removeVertex(Vertex<V> v) {
        matrix.get(v).clear();
        matrix.remove(v);
        --numOfVert;
    }

    @Override
    public void addEdge(Vertex<V> v1, Vertex<V> v2, E weight) {
        matrix.get(v1).put(v2, new Edge<>(v1, v2, weight));
        ++numOfEdg;
    }

    @Override
    public void removeEdge(Edge<V, E> e) {
        matrix.get(e.getFrom()).remove(e.getTo());
        --numOfEdg;
    }

    @Override
    public List<Vertex<V>> getVertex() {
        return new ArrayList<>(matrix.keySet());
    }

    @Override
    public Vertex<V> getVertex(V data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("Vertex must be specified");
        }
        for (Vertex<V> vert : matrix.keySet()) {
            if (vert.getData().equals(data)) {
                return vert;
            }
        }
        return null;
    }

    @Override
    public List<Edge<V, E>> getEdge() {
        Set<Edge<V, E>> set = new HashSet<>();
        for (Vertex<V> vert : matrix.keySet()) {
            set.addAll(matrix.get(vert).values());
        }
        return new ArrayList<>(set);
    }

    @Override
    public Edge<V, E> getEdge(Vertex<V> from, Vertex<V> to) {
        return matrix.get(from).get(to);
    }

    public int numberOfVertices() {
        return numOfVert;
    }

    public int numberOfEdges() {
        return numOfEdg;
    }
}
