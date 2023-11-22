package dbarsukova;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Implementation on adjacency list.
 */

public class AdjacencyList<V, E extends Number> implements Graph<V, E> {
    HashMap<Vertex<V>, ArrayList<Pair<Edge<V, E>, Vertex<V>>>> map;
    private int numOfVert;
    private int numOfEdg;

    private record Pair<X, Y>(X one, Y two) {
    }

    /**
     * creating an empty adjacency list.
     */
    public AdjacencyList() {
        map = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(V data) {
        Vertex<V> vert = new Vertex<>(data);
        map.putIfAbsent(vert, new ArrayList<>());
        ++numOfVert;
        return vert;
    }

    @Override
    public void removeVertex(Vertex<V> v) {
        map.values().forEach(list -> list.removeIf(tuple -> tuple.two().getData().equals(v.getData())));
        map.remove(v);
        --numOfVert;
    }

    @Override
    public void addEdge(Vertex<V> v1, Vertex<V> v2, E weight) {
        map.get(v1).add(new Pair<>(new Edge<>(v1, v2, weight), v2));
        ++numOfEdg;
    }

    @Override
    public void removeEdge(Edge<V, E> e) {
        map.get(e.from()).removeIf(t -> (t.one().to().getData().equals(e.to().getData())));
        --numOfEdg;
    }

    @Override
    public List<Vertex<V>> getVertex() {
        return new ArrayList<>(map.keySet());
    }

    @Override
    public List<Edge<V, E>> getEdge() {
        Set<Edge<V, E>> set = new HashSet<>();
        for (Vertex<V> vert : map.keySet()) {
            for (Pair<Edge<V, E>, Vertex<V>> t : map.get(vert)) {
                set.add(t.one());
            }
        }
        return new ArrayList<>(set);
    }

    @Override
    public Vertex<V> getVertex(V data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("Vertex must be specified");
        }
        for (Vertex<V> vert : map.keySet()) {
            if (vert.getData().equals(data)) {
                return vert;
            }
        }
        return null;
    }

    @Override
    public Edge<V, E> getEdge(Vertex<V> from, Vertex<V> to) {
        for (Pair<Edge<V, E>, Vertex<V>> t : map.get(from)) {
            if (t.one().to().getData().equals(to.getData())) {
                return t.one();
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
