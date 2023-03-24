package com.github.yinzhou.models;

import lombok.Data;

@Data
public class Edge {
    private final Node endpoint1;
    private final Node endpoint2;
    private int weight = 1;
    private int originWeight = 1;

    public Edge(Node endpoint1, Node endpoint2) {
        this.endpoint1 = endpoint1;
        this.endpoint2 = endpoint2;
    }

    public void setWeightFirst(int weight) {
        this.weight = weight;
        this.originWeight = weight;
    }

    public boolean hasNode(Node node) {
        return endpoint1 == node || endpoint2 == node;
    }

    // 无向边比较
    public boolean equals(Edge edge) {
        return (endpoint1 == edge.endpoint1 && endpoint2 == edge.endpoint2)
            || (endpoint1 == edge.endpoint2 && endpoint2 == edge.endpoint1);
    }

    @Override
    public String toString() {
        return "Edge ~ "
            + getEndpoint1().getNodeId() + " - "
            + getEndpoint2().getNodeId();
    }
}
