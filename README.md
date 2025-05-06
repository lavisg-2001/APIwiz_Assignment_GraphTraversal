# Graph Traversal Assignment

## Overview

This project focuses to solve the challenge of **complex graph traversal** using a directed graph that can branch out (diverge) and merge back (converge).

Each node in the graph performs a simple job: **it prints its name**. But the nodes must follow a strict execution order:
- A node runs **only after all of its parent nodes** have completed.
- If a node has **multiple children**, those children are executed **in parallel** using threads.
- Every node is executed **once and only once**, even in complex graphs with shared children.

The graph is built from two inputs:
- A `vertexMap`: maps node IDs to their names.
- An `edgeSet`: defines which nodes connect to which.

This setup simulates **real-world task execution**, where each task may depend on others and needs to run in the right order, sometimes in parallel.

## Table of Contents

- [Overview](#overview)
- [Structure](#structure)
- [Components](#components)
    - [GraphInputReader](#1-graphinputreader)
    - [GraphTraversal Interface](#2-graphtraversal-interface)
    - [GraphTraversalImplementation](#3-graphtraversalimplementation)
    - [Node](#4-node)
    - [APIwizAssignmentMain](#5-apiwizassignmentmain)
- [Design Patterns Used](#design-patterns-used)
- [Usage](#usage)
- [Conclusion](#conclusion)

## Structure

The codebase is cleanly organized into two main packages:
- `org.apiwiz.assignments.GraphTraversal`: Core interfaces and classes that handle graph logic.
- `org.apiwiz.assignments`: The main runner class that kicks off the graph traversal.

## Components

### 1. GraphInputReader

Reads node and edge definitions from an input file and uses them to build the graph.

- `readNodesAndEdges(String filePath, GraphTraversal graph)`: Parses the file and loads node and edge data into the graph.

### 2. GraphTraversal Interface

Defines what a graph traversal class should be able to do:

- Add nodes (`addNode`)
- Add edges between nodes (`addEdge`)
- Access the full set of nodes (`getNodes`)
- Start the traversal (`traverse`)

### 3. GraphTraversalImplementation

This is where the core logic lives. It handles concurrency and dependency checks:

- Maintains a map of nodes.
- Uses a thread pool (`ExecutorService`) to run child nodes in parallel.
- Keeps track of executed nodes to avoid duplicates.
- Main logic is in `traverse()` and `executeNodeStrictOrder()`.

### 4. Node

Represents a single unit of work in the graph.

- Has a unique ID and a name.
- Tracks its children (outgoing edges).
- Uses an `AtomicInteger` to count pending parents (to know when it’s ready to execute).

### 5. APIwizAssignmentMain

The entry point of the app.

- Calls the input reader.
- Builds and runs the graph.
- Can be launched with a file path to test different graphs.

## Design Patterns Used

Several proven design patterns are applied to keep the code clean and scalable:

1. **Singleton** – A single `ExecutorService` is reused across the traversal.
2. **Observer** – Each node reacts to changes in its parent completion count.
3. **Command** – Each node's print action behaves like a command.
4. **Factory** – Graph components are created using clear, separated logic.

## Usage

1. Create an input file (`input.txt`) with the following format:

    ```
    N
    1:A
    2:B
    3:C
    ...
    E
    1:2
    1:3
    2:4
    ...
    ```

   Where:
    - `N` starts the node list: `id:name`
    - `E` starts the edge list: `from:to`

2. Compile and run the application:

    ```bash
    java -cp target/your-artifact-name.jar org.apiwiz.assignments.APIwizAssignmentMain
    ```

3. What happens:
    - The input file is parsed into a graph.
    - Traversal starts from all root nodes.
    - Each node prints its name once it's ready.
    - Child nodes execute in **parallel** when possible.
    - Every node runs **only once**, even with shared dependencies.

## Conclusion

This project demonstrates a clean and efficient way to model and execute workflow-like graph traversal.

The implementation ensures:
- Each node runs **only after its parents finish**.
- **Parallel execution** of child nodes is achieved using threads.
- No node is ever executed more than once.

Key implementation highlights include:
- An `AtomicInteger` on each node to track how many parent completions are pending.
- A shared `ExecutorService` that handles concurrent task execution.
- A `CountDownLatch` to wait for full graph completion before shutting down the thread pool.

All coordination and core logic are handled in the `GraphTraversalImplementation` class. This setup closely mirrors real-world orchestrators where both **dependency management** and **performance through concurrency** are essential.
