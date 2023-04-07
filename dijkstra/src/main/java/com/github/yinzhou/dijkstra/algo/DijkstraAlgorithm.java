package com.github.yinzhou.dijkstra.algo;

import com.github.yinzhou.dijkstra.graph.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import com.github.yinzhou.dijkstra.models.Edge;
import com.github.yinzhou.dijkstra.models.Solution;
import com.github.yinzhou.dijkstra.models.Node;


@SuppressWarnings("unused")
public class DijkstraAlgorithm {
    private final Graph graph;

    // 使用优先队列遍历，保证每次都是当前计算距离最小的元素先出列。比较方法: NodeComparator.
    // inProcess出列之后加入completed, completed表示已经锁定了最短路径结果的节点。
    private PriorityQueue<Node> inProcess;
    private final HashSet<Node> completed = new HashSet<>();

    // 最短路径计算结果: 仅保存前置节点，反向查找可得全路径
    private final Map<Node, Node> predecessors = new HashMap<>();

    // 最短路径长度计算结果
    private final Map<Node, Integer> distances = new HashMap<>();

    private final List<String> exceptions = new ArrayList<>();

    public static final Integer BIG_NUMBER = 10 * 1000 * 1000;

    public class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            return distances.get(node1) - distances.get(node2);
        }
    }

    public DijkstraAlgorithm(Graph graph) {
        this.graph = graph;
    }

    private void clearContext() {
        distances.clear();
        for (Node node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        predecessors.clear();
        inProcess = new PriorityQueue<>(graph.getNodes().size(), new NodeComparator());
        completed.clear();
        graph.setSolved(false);
    }

    public void run() {
        if (!evaluate()) {
            throw new IllegalStateException(exceptions.toString());
        }

        this.clearContext();

        Node source = graph.getSource();
        distances.put(source, 0);
        completed.add(source);

        for (Edge neighbor : graph.getNeighbors(source)) {
            Node adjacent = graph.getAdjacent(neighbor, source);
            if (adjacent == null) {
                continue;
            }

            distances.put(adjacent, neighbor.getWeight());
            predecessors.put(adjacent, source);
            inProcess.add(adjacent);
        }

        while (!inProcess.isEmpty()) {
            // 上一步的最优解
            Node current = inProcess.poll();
            inProcess.remove(current);
            completed.add(current);

            // 如果当前节点==目的节点，可提前结束循环.
            if (Objects.equals(current, graph.getDestination())) {
                break;
            }

            // 下一跳: 计算邻居的最短路径
            int distance = distances.get(current);
            for (Edge neighbor : graph.getNeighbors(current)) {
                Node adjacent = graph.getAdjacent(neighbor, current);
                if (completed.contains(adjacent)) {
                    continue;
                }

                int currentDist = distances.get(adjacent);
                int newDist = distance + neighbor.getWeight();

                if (newDist < currentDist) {
                    distances.put(adjacent, newDist);
                    predecessors.put(adjacent, current);
                    inProcess.add(adjacent);
                }
            }
        }

        graph.setSolved(true);
    }

    public Solution runAlgo() {
        long start = System.currentTimeMillis();
        this.run();
        List<Node> shortestPath = getDestShortestPath();
        Integer shortestDist = getDestinationDistance();
        return new Solution(shortestPath, shortestDist, System.currentTimeMillis() - start);
    }

    // 包含策略: 使用分段计算方法解决包含策略。
    public Solution runWithIncludePolicy(List<Node> includes, Node dest) {
        List<Node> shortestPath = new ArrayList<>();
        Integer shortestDist = 0;
        Node preInclude = null;
        long start = System.currentTimeMillis();
        for (Node include : includes) {
            shortestDist += calculateSegment(include, preInclude, shortestPath, Collections.emptySet());
            // 重置源节点
            graph.setSource(include);
            preInclude = include;
        }
        // 计算最后一段
        shortestDist += calculateSegment(dest, preInclude, shortestPath, Collections.emptySet());
        return new Solution(shortestPath, shortestDist, System.currentTimeMillis() - start);
    }

    public Solution runWithIncludeAndExcludePolicy(List<Node> includes, List<Node> excludes, Node dest) {
        Set<Edge> originExcludeEdges = new HashSet<>();
        for (Node exclude : excludes) {
            Set<Edge> neighbors = graph.getNeighbors(exclude);
            neighbors.forEach(edge -> edge.setWeight(BIG_NUMBER));
            originExcludeEdges.addAll(neighbors);
        }
        List<Node> shortestPath = new ArrayList<>();
        Integer shortestDist = 0;
        Node preInclude = null;
        long start = System.currentTimeMillis();
        for (Node include : includes) {
            shortestDist += calculateSegment(include, preInclude, shortestPath, originExcludeEdges);
            // 重置源节点
            graph.setSource(include);
            preInclude = include;
        }
        // 计算最后一段
        shortestDist += calculateSegment(dest, preInclude, shortestPath, originExcludeEdges);
        return new Solution(shortestPath, shortestDist, System.currentTimeMillis() - start);
    }

    private Integer calculateSegment(Node dest, Node preInclude, List<Node> shortestPath,
                                     Set<Edge> excludeEdges) {
        graph.setDestination(dest);
        // 已经算出来的路径需要排斥掉(上一次计算路径的最后一个节点也就是下一次的源节点不能排斥)，避免重复进入同一节点。
        List<Node> needExcludes = shortestPath.stream()
            .filter(exclude -> !Objects.equals(exclude, graph.getSource()))
            .collect(Collectors.toList());
        runWithExcludePolicy(needExcludes, dest, excludeEdges);
        // 拼接路径
        List<Node> currentSegmentSPath = getDestShortestPath();
        if (preInclude != null) {
            currentSegmentSPath.remove(preInclude);
        }
        shortestPath.addAll(currentSegmentSPath);
        // 返回该段的最短距离
        return getDestinationDistance();
    }

    // 包含：分段计算。 排斥：将边的权重置为无穷大
    public void runWithExcludePolicy(List<Node> excludes, Node dest, Set<Edge> excludeEdges) {
        // 将排斥的边权重置为"非常大", 后续统一计算最短路径所以要恢复权重
        Map<Node, Set<Edge>> originalEdges = new HashMap<>();
        for (Node exclude : excludes) {
            Set<Edge> neighbors = graph.getNeighbors(exclude);
            originalEdges.put(exclude, neighbors);
            neighbors.forEach(edge -> edge.setWeight(BIG_NUMBER));
        }
        graph.setDestination(dest);

        this.run();

        // 恢复排除策略前的边权值, 如果是初始化排斥边则不恢复
        for (Node exclude : excludes) {
            Set<Edge> neighbors = originalEdges.get(exclude);
            neighbors.forEach(edge -> {
                if (!excludeEdges.contains(edge)) {
                    edge.setWeight(edge.getOriginWeight());
                }
            });
        }
    }

    public Integer getDestinationDistance() {
        return distances.get(graph.getDestination());
    }

    public List<Node> getDestShortestPath() {
        return getPath(graph.getDestination());
    }

    public List<Node> getPath(Node node) {
        List<Node> path = new ArrayList<>();

        Node current = node;
        path.add(current);
        while (current != graph.getSource()) {
            current = predecessors.get(current);
            path.add(current);
        }

        Collections.reverse(path);

        return path;
    }

    private boolean evaluate() {
        if (graph.getSource() == null) {
            exceptions.add("Source must be present in the graph");
            return false;
        }

        for (Node node : graph.getNodes()) {
            if (!graph.isNodeReachable(node)) {
                exceptions.add("Graph contains unreachable nodes");
                return false;
            }
        }
        return true;
    }
}
