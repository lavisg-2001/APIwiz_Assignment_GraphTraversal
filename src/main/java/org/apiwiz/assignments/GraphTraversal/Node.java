package org.apiwiz.assignments.GraphTraversal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Node {
    final int id;
    final String name;
    final List<Integer> children;
    final AtomicInteger pendingParents;

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
        this.pendingParents = new AtomicInteger(0);
    }
}
