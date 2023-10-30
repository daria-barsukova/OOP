package dbarsukova;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class for reading a graph from text file.
 */
public class Reader {

    /**
     * presentation method.
     */
    public enum TypePresentation {
        ADJ_MATRIX, INC_MATRIX, ADJ_LIST
    }

    /**
     * reading graph from text file.
     *
     * @param file file.
     * @param type presentation method.
     */
    public Graph<String> readGraph(String file, TypePresentation type) {
        Graph<String> graph = new Graph<>();
        try {
            Scanner reader = new Scanner(new File(file));
            String[] vertices = reader.nextLine().split(" ");
            for (String elem : vertices) {
                graph.addVertex(elem);
            }
            switch (type) {
                case ADJ_LIST -> {
                    for (String name : vertices) {
                        String[] data = (reader.nextLine()).split(",");
                        for (String vertex : data) {
                            graph.addEdge(name, vertex, 1);
                        }
                    }
                }
                case ADJ_MATRIX -> {
                    for (String name : vertices) {
                        String[] data = reader.nextLine().split(" ");
                        for (int i = 0; i < data.length; i++) {
                            if (Integer.parseInt(data[i]) > 0) {
                                graph.addEdge(name, vertices[i], Integer.parseInt(data[i]));
                            }
                        }
                    }
                }
                case INC_MATRIX -> {
                    while (reader.hasNextLine()) {
                        String[] data = reader.nextLine().split(" ");
                        graph.addEdge(data[0], data[1], Integer.parseInt(data[2]));
                    }
                }
                default -> throw new IllegalStateException();
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return graph;
    }
}
