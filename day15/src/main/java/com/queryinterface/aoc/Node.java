package com.queryinterface.aoc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {
    
    private int x;
    private int y;
    private int level;
    
    private List<Node> shortestPath = new LinkedList<>();
        
    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }
 
    public Node(int x, int y, int level) {
        this.x = x;
        this.y = y;
        this.level = level;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }
    
    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return this.adjacentNodes;
    }

    public List<Node> getShortestPath() {
        return this.shortestPath;
    }

    public void setShortestPath(List<Node> sp) {
        this.shortestPath = sp;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('{');
        builder.append(x()).append(", ").append(y()).append(" -> ").append(level);
        builder.append('}');
        return builder.toString();
    }
}
