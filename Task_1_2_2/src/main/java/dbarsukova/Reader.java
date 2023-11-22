package dbarsukova;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;


/**
 * reading graph from text file.
 */

public class Reader {

    /**
     * graph initialization.
     *
     * @param graph graph for reading
     * @param path  path to text file
     */
    public static void read(Graph<String, Integer> graph, String path) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Reader.class.getResourceAsStream(path))));
            String line = reader.readLine();
            String[] number = line.split(" ");
            line = reader.readLine();
            String[] vertices = line.split(" ");
            for (int i = 0; i < Integer.parseInt(number[0]); ++i) {
                graph.addVertex(vertices[i]);
            }
            for (int i = 0; i < Integer.parseInt(number[1]); ++i) {
                line = reader.readLine();
                String[] edge = line.split(" ");
                Vertex<String> v1 = graph.getVertex(edge[0]);
                Vertex<String> v2 = graph.getVertex(edge[1]);
                graph.addEdge(v1, v2, Integer.parseInt(edge[2]));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
