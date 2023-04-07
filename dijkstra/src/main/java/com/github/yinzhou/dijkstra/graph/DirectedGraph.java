package com.github.yinzhou.dijkstra.graph;

import com.github.yinzhou.dijkstra.models.Edge;
import com.github.yinzhou.dijkstra.models.Node;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;


@Data
public class DirectedGraph extends Graph {

    @Override
    public Set<Edge> getNeighbors(Node node) {
        Set<Edge> neighbors = new HashSet<>();

        for (Edge edge : getEdges()) {
            if (edge.getEndpoint1() == node)
                neighbors.add(edge);
        }

        return neighbors;
    }

    @Override
    public Node getAdjacent(Edge edge, Node node) {
        assert edge.getEndpoint1() == node;
        return edge.getEndpoint2();
    }
}
