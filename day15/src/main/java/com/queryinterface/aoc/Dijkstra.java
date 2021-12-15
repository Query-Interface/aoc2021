package com.queryinterface.aoc;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Dijkstra {
    private Map<Node, Integer> distances = new HashMap<>();
    private Map<Node, Node> predecessors = new HashMap<>();
    Set<Node> settledNodes = new HashSet<>();
    Set<Node> unsettledNodes = new HashSet<>();

    public Board calculateShortestPathFromSource(Board board) {
        var graph = Board.copyOf(board);
        var source = graph.getNode(0, 0);
        source.setLevel(0);
        distances.put(source, 0);
        unsettledNodes.add(source);
    
        while (unsettledNodes.size() > 0) {
            Node node = getMinimum(unsettledNodes);
            settledNodes.add(node);
            unsettledNodes.remove(node);
            findMinimalDistances(node);
        }
        return graph;
    }

    public Board2 calculateShortestPathFromSource(Board2 graph) {
        var source = graph.getNode(0, 0);
        source.setLevel(0);
        distances.put(source, 0);
        unsettledNodes.add(source);
    
        while (unsettledNodes.size() > 0) {
            Node node = getMinimum(unsettledNodes);
            settledNodes.add(node);
            unsettledNodes.remove(node);
            findMinimalDistances(node);
        }
        return graph;
    }

    private void findMinimalDistances(Node currentNode) {
        for (Entry < Node, Integer> adjacencyPair: 
              currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
        }
    }

    private Node getMinimum(Set<Node> nodes) {
        Node minimum = null;
        for (Node vertex : nodes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getLevel();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        if (getShortestDistance(evaluationNode) > getShortestDistance(sourceNode) + edgeWeigh) {
            distances.put(evaluationNode, getShortestDistance(sourceNode) + edgeWeigh);
            predecessors.put(evaluationNode, sourceNode);
        }
    }

    private int getShortestDistance(Node destination) {
        Integer d = distances.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<Node>();
        Node step = target;
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }
}
