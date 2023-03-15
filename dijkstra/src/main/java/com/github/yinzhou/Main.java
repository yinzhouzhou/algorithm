package com.github.yinzhou;

import com.github.yinzhou.algo.DijkstraAlgorithm;

import java.util.Arrays;
import javax.swing.*;
import com.github.yinzhou.models.Edge;
import com.github.yinzhou.models.Solution;
import com.github.yinzhou.graph.UndirectedGraph;
import com.github.yinzhou.models.Node;

public class Main {

    public static void main(String[] args) {
        UndirectedGraph graph = new UndirectedGraph();
        initGraph(graph);
        graph.setSource(graph.getNodes().get(0));
        graph.setDestination(graph.getNodes().get(2));
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        try {
            Solution solution = dijkstraAlgorithm.runWithIncludeAndExcludePolicy(
                Arrays.asList(graph.getNodes().get(4)),
                Arrays.asList(graph.getNodes().get(3)),
                graph.getDestination());
            System.out.println(solution);
        } catch (IllegalStateException ise) {
            JOptionPane.showMessageDialog(null, ise.getMessage());
        }

//        graph = new UndirectedGraph();
//        initGraph(graph);
//        DijkstraAlgorithm dijkstraAlgorithm2 = new DijkstraAlgorithm(graph);
//        try {
//            dijkstraAlgorithm2.runWithExclude(graph.getNodes().get(4), graph.getDestination());
//            // System.out.println(dijkstraAlgorithm.getDestinationPath());
//        } catch (IllegalStateException ise) {
//            JOptionPane.showMessageDialog(null, ise.getMessage());
//        }

    }


    private static void initGraph(UndirectedGraph graph) {
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

}
