package com.github.yinzhou.dijkstra.models;

import java.util.Iterator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Solution {
    private final List<Node> shortestPath;
    private final Integer shortestDist;
    private final long timeSpent;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Iterator<Node> it = shortestPath.iterator();
        if (!it.hasNext()) {
            builder.append("shortestPath=[]");
        } else {
            builder.append("shortestPath=[");
            for (; ; ) {
                Node e = it.next();
                builder.append(e);
                if (!it.hasNext()) {
                    builder.append(']');
                    break;
                }
                builder.append("--->").append(' ');
            }
        }
        builder.append(", shortestDist=").append(shortestDist);
        builder.append(", timeSpent=").append(timeSpent).append("ms");
        return builder.toString();
    }
}
