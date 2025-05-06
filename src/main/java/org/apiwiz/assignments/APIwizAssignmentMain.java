package org.apiwiz.assignments;

import org.apiwiz.assignments.GraphTraversal.GraphInputReader;
import org.apiwiz.assignments.GraphTraversal.GraphTraversal;
import org.apiwiz.assignments.GraphTraversal.GraphTraversalImplementation;
import java.io.IOException;

public class APIwizAssignmentMain {

    public static void main(String[] args) {
        String filePath = "src/main/resources/input.txt";
        runGraphTraversal(filePath);
    }

    private static void runGraphTraversal(String filePath) {
        GraphTraversal graphTraversal = new GraphTraversalImplementation();

        try {
            GraphInputReader.readNodesAndEdges(filePath, graphTraversal);
            graphTraversal.traverse();
        } catch (IOException e) {
            System.err.println("Failed to read input file: " + e.getMessage());
        }
    }
}
