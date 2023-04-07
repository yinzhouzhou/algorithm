package com.github.yinzhou.dijkstra.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Node {

    private String nodeId;

    @Override
    public String toString() {
        return "Node " + nodeId;
    }
}
