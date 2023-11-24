package dbarsukova;


/**
 * Class implements representation of edges for Graph class.
 *
 * @param <V>    type of edge vertices
 * @param <E>    type of edge data
 */

public class Edge<V, E extends Number> {
    private final Vertex<V> from;
    private final Vertex<V> to;
    private final E weight;

    /**
     * edge constructor.
     * edge cannot be constructed from or to a null vertex.
     */
    public Edge(Vertex<V> from, Vertex<V> to, E weight) throws NullPointerException {
        if (from == null || to == null) {
            throw new NullPointerException("Edge vertices must be specified");
        }
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public E getWeight() {
        return weight;
    }

    public Vertex<V> getFrom() {
        return from;
    }

    public Vertex<V> getTo() {
        return to;
    }
}
