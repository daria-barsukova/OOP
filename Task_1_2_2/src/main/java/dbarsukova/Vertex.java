package dbarsukova;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for interacting with vertices.
 *
 * @param <T> name data type.
 */
public class Vertex<T> implements Comparable<Vertex<T>> {
    private final List<Edge> start;
    private final List<Edge> end;
    private Vertex<T> ancestor;
    private final T name;
    private int weight;

    /**
     * variables responsible for vertex color.
     */
    enum Color {
        WHITE, BLACK
    }

    /**
     * creating a vertex.
     *
     * @param data vertex name.
     */
    public Vertex(T data) {
        this.name = data;
        start = new ArrayList<>();
        end = new ArrayList<>();
        weight = 0;
    }

    /**
     * getting vertex name.
     */
    public T getName() {
        return this.name;
    }

    /**
     * setting vertex weight.
     *
     * @param w new weight.
     */
    public void setWeight(int w) {
        this.weight = w;
    }

    /**
     * getting vertex weight.
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * getting parent of vertex.
     */
    public Vertex<T> getAncestor() {
        return this.ancestor;
    }

    protected List<Edge> getStart() {
        return this.start;
    }

    protected void addEdgeStart(Edge edge) {
        this.start.add(edge);
    }

    protected void addEdgeEnd(Edge edge) {
        this.end.add(edge);
    }

    /**
     * setting parent of vertex.
     */
    void setAncestor(Vertex<T> vertex) {
        this.ancestor = vertex;
    }

    /**
     * comparing two objects.
     *
     * @param obj object.
     */
    @Override
    public int compareTo(Vertex<T> obj) {
        return Integer.compare(this.weight, obj.weight);
    }

    /**
     * deleting edge between this and `to`.
     *
     * @param to vertex end name.
     */
    public boolean removeEdge(Vertex<T> to) {
        for (Edge edge : this.start) {
            if (edge.getEdgeEnd() == to) {
                this.start.remove(edge);
                edge.getEdgeEnd().end.remove(edge);
                return true;
            }
        }
        return false;
    }
}
