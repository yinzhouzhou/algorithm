package com.github.yinzhou.graph;

import com.github.yinzhou.models.Edge;
import com.github.yinzhou.models.Node;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class UndirectedGraph {
    private int count = 1;
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    private Node source;
    private Node destination;

    private boolean solved = false;

    public boolean isNodeReachable(Node node) {
        for (Edge edge : edges) {
            if (node == edge.getOne() || node == edge.getTwo())
                return true;
        }
        return false;
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

    public void addEdge(Edge new_edge) {
        boolean added = false;
        for (Edge edge : edges) {
            if (edge.equals(new_edge)) {
                added = true;
                break;
            }
        }
        if (!added)
            edges.add(new_edge);
    }

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
