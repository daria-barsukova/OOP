package dbarsukova;

import java.util.List;


/**
 * task-1-2-2 working with oriented weighted graphs.
 */

public interface Graph<V, E extends Number> {
    Vertex<V> addVertex(V data);
    void removeVertex(Vertex<V> v);
    void addEdge(Vertex<V> v1, Vertex<V> v2, E weight);
    void removeEdge(Edge<V, E> e);
    Vertex<V> getVertex(V data);
    Edge<V, E> getEdge(Vertex<V> from, Vertex<V> to);
    List<Vertex<V>> getVertex();
    List<Edge<V, E>> getEdge();
    int numberOfVertices();
    int numberOfEdges();
}