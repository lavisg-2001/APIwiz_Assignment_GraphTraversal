package org.apiwiz.assignments.GraphTraversal;

import java.util.*;
import java.util.concurrent.*;

public class GraphTraversalImplementation implements GraphTraversal {

    private final Map<Integer, Node> nodes = new ConcurrentHashMap<>();
    private final ExecutorService executor;
    private final Set<Integer> executed = ConcurrentHashMap.newKeySet();

    public GraphTraversalImplementation() {
        int threads = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(threads);
    }

    @Override
    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    @Override
    public void traverse() {
        List<Node> readyNodes = new ArrayList<>();
        for (Node node : nodes.values()) {
            if (node.pendingParents.get() == 0) {
                readyNodes.add(node);
            }
        }

        CountDownLatch latch = new CountDownLatch(nodes.size());

        for (Node root : readyNodes) {
            executor.submit(() -> {
                executeNodeStrictOrder(root, latch);
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }

        System.out.println(nodes.size());
    }

    private void executeNodeStrictOrder(Node node, CountDownLatch latch) {
        if (!executed.add(node.id)) {
            latch.countDown();
            return;
        }

        System.out.println(node.name);

        for (int childId : node.children) {
            Node child = nodes.get(childId);
            if (child == null) continue;

            int remaining = child.pendingParents.decrementAndGet();

            if (remaining == 0) {
                executeNodeStrictOrder(child, latch);
            }
        }
        latch.countDown();
    }
}
