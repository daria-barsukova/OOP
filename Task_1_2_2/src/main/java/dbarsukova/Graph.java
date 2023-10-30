package dbarsukova;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * task-1-2-2 working with oriented weighted graphs.
 *
 * @param <T> data type.
 */
public class Graph<T> implements Iterable<Vertex<T>> {
    private final List<Vertex<T>> vertices;
    private Vertex<T> currentVertex;
    private int counter;

    /**
     * creating a graph.
     */
    public Graph() {
        vertices = new ArrayList<>();
        counter = 0;
    }

    /**
     * all vertices of graph.
     */
    public List<Vertex<T>> getVertices() {
        return this.vertices;
    }

    /**
     * number of changes.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Dijkstra iterator for graph.
     */
    @Override
    public Iterator<Vertex<T>> iterator() {
        return new Dijkstra<>(this, currentVertex);
    }

    /**
     * adding a vertex.
     *
     * @param name vertex name.
     */
    public boolean addVertex(T name) {
        Vertex<T> newVertex = new Vertex<>(name);
        vertices.add(newVertex);
        counter++;
        return true;
    }

    /**
     * defining a vertex by name.
     *
     * @param name vertex name.
     */
    public Vertex<T> defineVertex(T name) {
        for (Vertex<T> vertex : vertices) {
            if (name.equals(vertex.getName())) {
                return vertex;
            }
        }
        return null;
    }

    /**
     * checking for presence of a vertex in graph.
     *
     * @param name vertex name.
     */
    public boolean hasVertex(T name) {
        for (Vertex<T> vertex : vertices) {
            if (name.equals(vertex.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * adding an edge.
     *
     * @param from   edge start name.
     * @param to     edge end name.
     * @param weight weight.
     */
    public boolean addEdge(T from, T to, int weight) {
        Vertex<T> start = defineVertex(from);
        Vertex<T> end = defineVertex(to);
        Edge edge = new Edge(weight, start, end);
        start.addEdgeStart(edge);
        end.addEdgeEnd(edge);
        counter++;
        return true;
    }

    /**
     * deleting an edge.
     *
     * @param from edge start name.
     * @param to   edge end name.
     */
    public boolean removeEdge(T from, T to) {
        Vertex<T> start = defineVertex(from);
        Vertex<T> end = defineVertex(to);
        counter++;
        return start.removeEdge(end);
    }

    /**
     * checking for presence of an edge in graph.
     *
     * @param from vertex start name.
     * @param to   vertex end name.
     */
    public boolean hasEdge(T from, T to) {
        for (Vertex<T> vertex : vertices) {
            if (from.equals(vertex.getName())) {
                for (Edge edge : vertex.getStart()) {
                    if (to.equals(edge.getEdgeEnd().getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * getting object corresponding to edge.
     *
     * @param from edge start name.
     * @param to   edge end name.
     */
    public Edge getEdge(T from, T to) {
        Vertex<T> start = defineVertex(from);
        Vertex<T> end = defineVertex(to);
        for (Edge edge : start.getStart()) {
            if (edge.getEdgeEnd() == end) {
                return edge;
            }
        }
        return null;
    }

    /**
     * getting edge weight.
     *
     * @param from edge start name.
     * @param to   edge end name.
     */
    public int getWeight(T from, T to) {
        return getEdge(from, to).getWeight();
    }

    /**
     * setting edge weight.
     *
     * @param from   edge start name.
     * @param to     edge end name.
     * @param weight new weight.
     */
    public boolean setWeight(T from, T to, int weight) {
        for (Vertex<T> vertex : vertices) {
            if (from.equals(vertex.getName())) {
                for (Edge edge : vertex.getStart()) {
                    if (to.equals(edge.getEdgeEnd().getName())) {
                        edge.setWeight(weight);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * getting current vertex.
     *
     * @param from edge start name.
     * @param to   edge end name.
     */
    public Vertex<T> getVertex(T from, T to) {
        this.currentVertex = defineVertex(from);
        for (Iterator<Vertex<T>> iterator = this.iterator(); iterator.hasNext(); ) {
            Vertex<T> temp = iterator.next();
        }
        return defineVertex(to);
    }

    /**
     * finding the shortest path between two vertices using Dijkstra's algorithm.
     *
     * @param from vertex start name.
     * @param to   vertex end name.
     */
    public String getShortestPath(T from, T to) {
        String ans = "";
        Vertex<T> start = defineVertex(from);
        Vertex<T> current = getVertex(from, to);
        while (current != start) {
            ans += current.getName() + " " + current.getWeight() + "←--";
            current = current.getAncestor();
        }
        ans += start.getName() + " " + start.getWeight();
        return ans;
    }

    /**
     * finding the smallest path weight between two vertices.
     *
     * @param from vertex start name.
     * @param to   vertex end name.
     */
    public int getSmallestWeight(T from, T to) {
        Vertex<T> end = getVertex(from, to);
        if (end.getWeight() != Integer.MAX_VALUE) {
            return end.getWeight();
        } else {
            return -1;
        }
    }

    /**
     * getting sorted list of vertices with weights of paths from vertex.
     *
     * @param from vertex start name.
     */
    public String sortVertex(T from) {
        List<Vertex<T>> vertices = new ArrayList<>();
        this.currentVertex = defineVertex(from);
        for (Vertex<T> vertex : this) {
            vertices.add(vertex);
        }
        Collections.sort(vertices);
        String vertexInfo = "";
        for (Vertex<T> vertex : vertices) {
            if (vertex.getWeight() != Integer.MAX_VALUE) {
                vertexInfo += vertex.getName().toString() + "(" + vertex.getWeight() + ") ";
            } else {
                vertexInfo += vertex.getName().toString() + "(∞) ";
            }
        }
        return vertexInfo;
    }
}
