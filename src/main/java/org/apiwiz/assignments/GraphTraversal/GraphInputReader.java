package org.apiwiz.assignments.GraphTraversal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphInputReader {

    public static void readNodesAndEdges(String filePath, GraphTraversal graph) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            int N = Integer.parseInt(br.readLine().trim());

            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                if (line == null) break;
                String[] parts = line.split(":");
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                graph.addNode(id, name);
            }

            int E = Integer.parseInt(br.readLine().trim());

            for (int i = 0; i < E; i++) {
                String line = br.readLine();
                if (line == null) break;
                String[] parts = line.split(":");
                int from = Integer.parseInt(parts[0].trim());
                int to = Integer.parseInt(parts[1].trim());
                graph.addEdge(from, to);
            }
        }
    }
}
