package com.github.yinzhou.dijkstra.graph;

import com.github.yinzhou.dijkstra.models.Edge;
import com.github.yinzhou.dijkstra.models.Node;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class Graph {
    private int count = 1;
    private Set<Node> nodes = new HashSet<>();
    private Set<Edge> edges = new HashSet<>();

    private Node source;
    private Node destination;

    private boolean solved = false;

    public boolean isNodeReachable(Node node) {
        for (Edge edge : edges) {
            if (node == edge.getEndpoint1() || node == edge.getEndpoint2()) {
                return true;
            }
        }
        return false;
    }

    public Set<Edge> getNeighbors(Node node) {
        // 一个图的node指针是唯一的，这里可以使用==，也可以使用equals.
        return getEdges().stream()
            .filter(edge -> edge.getEndpoint1() == node || edge.getEndpoint2() == node)
            .collect(Collectors.toSet());
    }

    public Node getAdjacentNode(Edge edge, Node node) {
        if (edge.getEndpoint1() != node && edge.getEndpoint2() != node) {
            return null;
        }

        return node == edge.getEndpoint2() ? edge.getEndpoint1() : edge.getEndpoint2();
    }

    public void setSource(Node node) {
        if (nodes.contains(node)) {
            source = node;
        }
    }

    public void setDestination(Node node) {
        if (nodes.contains(node)) {
            destination = node;
        }
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(Edge newEdge) {
        edges.add(newEdge);
    }

    @SuppressWarnings("unused")
    public void deleteNode(Node node) {
        List<Edge> delete = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.hasNode(node)) {
                delete.add(edge);
            }
        }
        for (Edge edge : delete) {
            edges.remove(edge);
        }
        nodes.remove(node);
    }

    public void clear() {
        count = 1;
        nodes.clear();
        edges.clear();
        solved = false;

        source = null;
        destination = null;
    }
}
