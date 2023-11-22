package dbarsukova;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Implementation on incidence matrix.
 */

public class IncidenceMatrix<V, E extends Number> implements Graph<V, E> {
    private int numOfVert;
    private int numOfEdg;
    private final HashMap<Vertex<V>, HashMap<Edge<V, E>, Dir>> matrix;

    /**
     * edge direction.
     */
    public enum Dir {
        from, to
    }

    /**
     * creating an empty incidence matrix.
     */
    public IncidenceMatrix() {
        matrix = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(V data) {
        Vertex<V> vert = new Vertex<>(data);
        matrix.put(vert, new HashMap<>());
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
        matrix.get(v1).put(new Edge<>(v1, v2, weight), Dir.from);
        matrix.get(v2).put(new Edge<>(v1, v2, weight), Dir.to);
        ++numOfEdg;
    }

    @Override
    public void removeEdge(Edge<V, E> e) {
        matrix.keySet().forEach(vertex -> matrix.get(vertex).remove(e));
        --numOfEdg;
    }

    @Override
    public List<Vertex<V>> getVertex() {
        return new ArrayList<>(matrix.keySet());
    }

    @Override
    public List<Edge<V, E>> getEdge() {
        Set<Edge<V, E>> set = new HashSet<>();
        for (Vertex<V> vert : matrix.keySet()) {
            set.addAll(matrix.get(vert).keySet());
        }
        return new ArrayList<>(set);
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
    public Edge<V, E> getEdge(Vertex<V> from, Vertex<V> to) {
        for (Edge<V, E> edg : matrix.get(from).keySet()) {
            if (edg.to().getData().equals(to.getData())) {
                return edg;
            }
        }
        return null;
    }

    public int numberOfVertices() {
        return numOfVert;
    }

    public int numberOfEdges() {
        return numOfEdg;
    }
}
