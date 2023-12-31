package dbarsukova;

import java.util.HashMap;
import java.util.Map;


/**
 * implementation of an algorithm for sorting vertices.
 * sorting by distance from a given vertex for the created Graph object.
 */

public class SortByDistanceFromVertex {

    /**
     * sorting vertices.
     *
     * @param start starting vertex
     * @param graph graph for sorting
     */
    public static <V> Map<V, Integer> sort(Vertex<V> start, Graph<V, Integer> graph) {
        final Map<V, Integer> sort = new HashMap<>();
        for (Vertex<V> vert : graph.getVertex()) {
            sort.put(vert.getData(), java.lang.Integer.MAX_VALUE);
        }
        sort.replace(start.getData(), 0);
        for (int i = 0; i < graph.numberOfVertices(); ++i) {
            for (Edge<V, Integer> edg : graph.getEdge()) {
                if (sort.get(edg.getFrom().getData()) == Integer.MAX_VALUE) {
                    continue;
                }
                int temp = edg.getWeight() + sort.get(edg.getFrom().getData());
                if (sort.get(edg.getTo().getData()) > temp) {
                    sort.replace(edg.getTo().getData(), temp);
                }
            }
        }
        return sort;
    }
}
