package dbarsukova;


/**
 * Class implements representation of vertices for Graph class.
 *
 * @param <V> type of vertices
 */

public class Vertex<V> {
    private final V data;

    /**
     * vertex constructor.
     * value of vertex cannot be null.
     */
    public Vertex(V value) throws NullPointerException {
        if (value == null) {
            throw new NullPointerException("Vertices must be specified");
        }
        this.data = value;
    }

    /**
     * getting vertex value.
     */
    public V getData() {
        return data;
    }
}
