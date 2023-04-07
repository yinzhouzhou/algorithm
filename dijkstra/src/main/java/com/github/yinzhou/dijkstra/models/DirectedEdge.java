package com.github.yinzhou.dijkstra.models;

public class DirectedEdge extends Edge {

    public DirectedEdge(Node endpointStart, Node endpointEnd) {
        super(endpointStart, endpointEnd);
    }

    // 有向边比较，用==确保节点是同一个对象指针
    @Override
    public boolean equals(Edge edge) {
        return getEndpoint1() == edge.getEndpoint1()
            && getEndpoint2() == edge.getEndpoint2();
    }

    @Override
    public String toString() {
        return "DirectedEdge ~ "
            + getEndpoint1().getNodeId() + " -> "
            + getEndpoint2().getNodeId();
    }
}
