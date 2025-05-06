package org.apiwiz.assignments.GraphTraversal;

import java.util.Map;

public interface GraphTraversal {

    default void addNode(Map<Integer, Node> nodes, int id, String name) {
        nodes.put(id, new Node(id, name));
    }

    default void addNode(int id, String name) {
        getNodes().put(id, new Node(id, name));
    }


    default void addEdge(int from, int to) {
        Map<Integer, Node> nodes = getNodes();
        Node fromNode = nodes.get(from);
        Node toNode = nodes.get(to);
        if (fromNode != null && toNode != null) {
            synchronized (fromNode.children) {
                fromNode.children.add(to);
            }
            toNode.pendingParents.incrementAndGet();
        }
    }

    Map<Integer, Node> getNodes();

    void traverse();
}
