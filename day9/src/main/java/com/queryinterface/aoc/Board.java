package com.queryinterface.aoc;

import java.util.List;
import java.util.ArrayList;

public class Board {
    private int[][] matrix;
    
    final record Point(int x, int y, int value) {};
    final record PointAdv(int x, int y, int value, Point up, Point down, Point left, Point right) {}
    static final Point NONE = new Point(-1, -1, 10);

    public static Board of(List<String> lines) {
        var board = new Board();
        board.matrix = new int[100][100];
        for (int x=0; x < lines.size(); x++) {
            String line = lines.get(x);
            for (int i=0; i<line.length(); i++) {
                board.matrix[x][i] = Character.getNumericValue(line.charAt(i)); 
            }
        }
        return board;
    }

    public int getSize() {
        return 100;
    }

    public String getHeightMap() {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<100; i++) {
            for (int j=0; j<100; j++) {
                builder.append(matrix[i][j]);
            }
            builder.append("\\n");
        }
        return builder.toString();
    }

    public List<PointAdv> getPoints() {
        var points = new ArrayList<PointAdv>();
        for (int i=0; i<100; i++) {
            for (int j=0; j<100; j++) {
                points.add(getPoint(i, j));
            }
        }
        return points;
    }

    public PointAdv getPoint(int x, int y) {
        Point left=NONE, up=NONE, down=NONE, right=NONE;
        if (x == 0) {
            left = NONE;
        }
        if (x == 99) {
            right = NONE;
        }
        if (y == 0) {
            up = NONE;
        }
        if (y == 99) {
            down = NONE;
        }
        if (x > 0) {
            left = new Point(x-1, y, matrix[x-1][y]);
        }
        if (x < 99) {
            right = new Point(x+1, y, matrix[x+1][y]);
        }
        if (y > 0) {
            up = new Point(x, y-1, matrix[x][y-1]);
        }
        if (y < 99) {
            down = new Point(x, y+1, matrix[x][y+1]);
        }

        return new PointAdv(x, y, matrix[x][y], up, down, left, right);
    }
}
