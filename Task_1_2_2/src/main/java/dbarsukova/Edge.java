package dbarsukova;

/**
 * Class for interacting with edges.
 */
public class Edge {
    private final Vertex<?> from;
    private final Vertex<?> to;
    private int weight;

    /**
     * creating an edge.
     *
     * @param weight weight.
     * @param start  edge start name.
     * @param end    edge end name.
     */
    public Edge(int weight, Vertex<?> start, Vertex<?> end) {
        this.weight = weight;
        this.from = start;
        this.to = end;
    }

    /**
     * getting weight.
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * setting weight.
     *
     * @param w new weight.
     */
    public void setWeight(int w) {
        this.weight = w;
    }

    /**
     * getting edge start name.
     */
    public Vertex<?> getStart() {
        return this.from;
    }

    /**
     * getting edge end name.
     */
    public Vertex<?> getEdgeEnd() {
        return this.to;
    }
}
