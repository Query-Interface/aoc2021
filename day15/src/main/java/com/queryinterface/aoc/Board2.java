package com.queryinterface.aoc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board2 {
    private Node grid[][];
    private Set<Node> nodes = new HashSet<>();
    
    public static Board2 of(final List<String> data) {
        var board = new Board2();
        int dataSize = data.get(0).length();
        int size = dataSize * 5;
        board.grid = new Node[size][size];
        for (int i=0; i<size; i++) {
            var line = data.get(i%dataSize);
            var characters = line.toCharArray();
            for (int j=0; j<size;j++) {
                board.grid[i][j] = new Node(i, j, getNodeValue(Character.getNumericValue(characters[j%dataSize]), j/dataSize + i/dataSize));
            }
        }
        
        board.initGraph();
        return board;
    }

    private static int getNodeValue(int value, int step) {
        int val = value + step;
        if (val > 9) {
            val = val - 9;
        }
        return val;
    }
    
    public static Board2 copyOf(final Board2 source) {
        Board2 board = new Board2();
        board.grid = source.grid;
        board.initGraph();
        return board;
    }

    int getSize() {
        return grid.length;
    }
    
    private void initGraph() {
        int size = grid.length;
        for (int i = 0; i <size; i ++) {
            for (int j=0; j<size; j++) {
                var node = grid[i][j];
                if (!nodes.contains(node)) {
                    nodes.add(node);
                }
                if (i > 0) {
                    var dest = grid[i-1][j];
                    if (!nodes.contains(dest)) {
                        nodes.add(dest);
                    }
                    node.addDestination(dest, dest.getLevel());
                }
                if (i < size-1) {
                    var dest = grid[i+1][j];
                    if (!nodes.contains(dest)) {
                        nodes.add(dest);
                    }
                    node.addDestination(dest, dest.getLevel());
                }
                if (j > 0) {
                    var dest = grid[i][j-1];
                    if (!nodes.contains(dest)) {
                        nodes.add(dest);
                    }
                    node.addDestination(dest, dest.getLevel());
                }
                if (j < size-1) {
                    var dest = grid[i][j+1];
                    if (!nodes.contains(dest)) {
                        nodes.add(dest);
                    }
                    node.addDestination(dest, dest.getLevel());
                }
            }
        }
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public Node getNode(int i, int j) {
        return grid[i][j];
    }
}
