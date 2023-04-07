package com.github.yinzhou.dijkstra.algo;


import com.github.yinzhou.dijkstra.graph.DirectedGraph;
import com.github.yinzhou.dijkstra.graph.Graph;
import com.github.yinzhou.dijkstra.models.DirectedEdge;
import com.github.yinzhou.dijkstra.models.Edge;
import com.github.yinzhou.dijkstra.models.Node;
import com.github.yinzhou.dijkstra.models.Solution;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class DijkstraAlgorithmTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testUndirectedGraph() {
        Graph graph = new Graph();
        initUndirectedGraph(graph);
        graph.setSource(graph.getNodes().stream()
            .filter(node -> node.getNodeId().equals("v1")).findFirst().get());
        graph.setDestination(graph.getNodes().stream()
            .filter(node -> node.getNodeId().equals("v3")).findFirst().get());
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        try {
            Solution solution = dijkstraAlgorithm.runWithIncludeAndExcludePolicy(
                Arrays.asList(graph.getNodes().stream()
                    .filter(node -> node.getNodeId().equals("v5")).findFirst().get()),
                Arrays.asList(graph.getNodes().stream()
                    .filter(node -> node.getNodeId().equals("v4")).findFirst().get()),
                graph.getDestination());
            System.out.println(solution);
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }

    @Test
    public void testDirectedGraph() {
        DirectedGraph graph = new DirectedGraph();
        initDirectedGraph(graph);
        graph.setSource(graph.getNodes().stream()
            .filter(node -> node.getNodeId().equals("v1")).findFirst().get());
        graph.setDestination(graph.getNodes().stream()
            .filter(node -> node.getNodeId().equals("v3")).findFirst().get());
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        try {
            Solution solution = dijkstraAlgorithm.runAlgo();
            System.out.println(solution);
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }


    private static void initUndirectedGraph(Graph graph) {
        Node v1 = new Node("v1");
        Node v2 = new Node("v2");
        Node v3 = new Node("v3");
        Node v4 = new Node("v4");
        Node v5 = new Node("v5");
        Node v6 = new Node("v6");
        graph.clear();
        graph.addNode(v1);
        graph.addNode(v2);
        graph.addNode(v3);
        graph.addNode(v4);
        graph.addNode(v5);
        graph.addNode(v6);
        Edge edge16 = new Edge(v1, v6);
        edge16.setWeightFirst(2);
        Edge edge12 = new Edge(v1, v2);
        edge12.setWeightFirst(4);
        Edge edge26 = new Edge(v2, v6);
        edge26.setWeightFirst(3);
        Edge edge65 = new Edge(v6, v5);
        edge65.setWeightFirst(3);
        Edge edge25 = new Edge(v2, v5);
        edge25.setWeightFirst(1);
        Edge edge64 = new Edge(v6, v4);
        edge64.setWeightFirst(6);
        Edge edge24 = new Edge(v2, v4);
        edge24.setWeightFirst(1);
        Edge edge54 = new Edge(v5, v4);
        edge54.setWeightFirst(5);
        Edge edge23 = new Edge(v2, v3);
        edge23.setWeightFirst(6);
        Edge edge43 = new Edge(v4, v3);
        edge43.setWeightFirst(3);
        graph.addEdge(edge16);
        graph.addEdge(edge12);
        graph.addEdge(edge26);
        graph.addEdge(edge65);
        graph.addEdge(edge25);
        graph.addEdge(edge64);
        graph.addEdge(edge24);
        graph.addEdge(edge54);
        graph.addEdge(edge23);
        graph.addEdge(edge43);
    }

    private static void initDirectedGraph(DirectedGraph graph) {
        Node v1 = new Node("v1");
        Node v2 = new Node("v2");
        Node v3 = new Node("v3");
        Node v4 = new Node("v4");
        Node v5 = new Node("v5");
        Node v6 = new Node("v6");
        graph.clear();
        graph.addNode(v1);
        graph.addNode(v2);
        graph.addNode(v3);
        graph.addNode(v4);
        graph.addNode(v5);
        graph.addNode(v6);
        DirectedEdge edge16 = new DirectedEdge(v1, v6);
        edge16.setWeightFirst(3);
        DirectedEdge edge12 = new DirectedEdge(v1, v2);
        edge12.setWeightFirst(10);
        DirectedEdge edge62 = new DirectedEdge(v6, v2);
        edge62.setWeightFirst(2);
        DirectedEdge edge65 = new DirectedEdge(v6, v5);
        edge65.setWeightFirst(1);
        DirectedEdge edge64 = new DirectedEdge(v6, v4);
        edge64.setWeightFirst(6);
        DirectedEdge edge23 = new DirectedEdge(v2, v3);
        edge23.setWeightFirst(7);
        DirectedEdge edge24 = new DirectedEdge(v2, v4);
        edge24.setWeightFirst(5);
        DirectedEdge edge41 = new DirectedEdge(v4, v1);
        edge41.setWeightFirst(3);
        DirectedEdge edge43 = new DirectedEdge(v4, v3);
        edge43.setWeightFirst(4);
        DirectedEdge edge45 = new DirectedEdge(v4, v5);
        edge45.setWeightFirst(7);
        graph.addEdge(edge16);
        graph.addEdge(edge12);
        graph.addEdge(edge62);
        graph.addEdge(edge65);
        graph.addEdge(edge64);
        graph.addEdge(edge23);
        graph.addEdge(edge24);
        graph.addEdge(edge41);
        graph.addEdge(edge43);
        graph.addEdge(edge45);
    }



}