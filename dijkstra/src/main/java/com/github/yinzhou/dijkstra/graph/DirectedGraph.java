package com.github.yinzhou.dijkstra.graph;

import com.github.yinzhou.dijkstra.models.Edge;
import com.github.yinzhou.dijkstra.models.Node;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;


public class DirectedGraph extends Graph {

    @Override
    public Set<Edge> getNeighbors(Node node) {
        return getEdges().stream()
            .filter(edge -> edge.getEndpoint1() == node)
            .collect(Collectors.toSet());
    }

    @Override
    public Node getAdjacentNode(Edge edge, Node node) {
        if (edge.getEndpoint1() != node) {
            return null;
        }
        return edge.getEndpoint2();
    }
}
