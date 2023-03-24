package com.github.yinzhou.models;

import lombok.Data;

@Data
public class DirectedEdge {
    private final Node endpointStart;
    private final Node endpointEnd;
    private int weight = 1;
    private int originWeight = 1;

    public DirectedEdge(Node endpointStart, Node endpointEnd) {
        this.endpointStart = endpointStart;
        this.endpointEnd = endpointEnd;
    }

    public void setWeightFirst(int weight) {
        this.weight = weight;
        this.originWeight = weight;
    }

    public boolean hasNode(Node node) {
        return endpointStart == node || endpointEnd == node;
    }

    // 有向边比较
    public boolean equals(DirectedEdge edge) {
        return (endpointStart == edge.endpointStart && endpointEnd == edge.endpointEnd);
    }

    @Override
    public String toString() {
        return "DirectedEdge ~ "
            + getEndpointStart().getNodeId() + " -> "
            + getEndpointEnd().getNodeId();
    }
}
