package com.queryinterface.aoc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
    private Node grid[][] = new Node[10][10];
    private Set<Node> nodes = new HashSet<>();
    
    public static Board of(final List<String> data) {
        var board = new Board();
        int size = data.get(0).length();
        board.grid = new Node[size][size];
        for (int i=0; i<size; i++) {
            var line = data.get(i);
            var characters = line.toCharArray();
            for (int j=0; j<characters.length;j++) {
                board.grid[i][j] = new Node(i, j, Character.getNumericValue(characters[j]));
            }
        }
        board.initGraph();
        return board;
    }
    
    public static Board copyOf(final Board source) {
        Board board = new Board();
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
