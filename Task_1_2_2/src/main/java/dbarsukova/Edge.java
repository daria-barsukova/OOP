package dbarsukova;


/**
 * Class implements representation of edges for Graph class.
 *
 * @param from   edge start
 * @param to     edge end
 * @param weight edge weight
 * @param <V>    type of edge vertices
 * @param <E>    type of edge data
 */

public record Edge<V, E extends Number>(Vertex<V> from, Vertex<V> to, E weight) {

    /**
     * edge constructor.
     * edge cannot be constructed from or to a null vertex.
     */
    public Edge {
        if (from == null || to == null) {
            throw new NullPointerException("Edge vertices must be specified");
        }
    }
}
