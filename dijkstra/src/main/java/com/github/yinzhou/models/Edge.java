package com.github.yinzhou.models;

import lombok.Data;

@Data
public class Edge {
    private final Node one;
    private final Node two;
    private int weight = 1;
    private int originWeight = 1;

    public Edge(Node one, Node two) {
        this.one = one;
        this.two = two;
    }

    public void setWeightFirst(int weight) {
        this.weight = weight;
        this.originWeight = weight;
    }

    public int getOriginWeight() {
        return originWeight;
    }

    public boolean hasNode(Node node) {
        return one == node || two == node;
    }

    public boolean equals(Edge edge) {
        return (one == edge.one && two == edge.two) || (one == edge.two && two == edge.one);
    }

    @Override
    public String toString() {
        return "Edge ~ "
            + getOne().getNodeId() + " - "
            + getTwo().getNodeId();
    }
}
