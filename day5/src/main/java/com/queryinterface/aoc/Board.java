package com.queryinterface.aoc;

import java.util.List;
import java.util.ArrayList;

public class Board {
    private List<Segment> segments = new ArrayList<>();
    private WeightedPoint[][] matrix;
    
    private final record Point(int x, int y) {}
    private final record Segment(Point start, Point end) {
        boolean isHorizontal() {
            return start.y == end.y;
        }
        boolean isVertical() {
            return start.x == end.x;
        }
        boolean isDiagonal() {
            return Math.abs(start.x - end.x) == Math.abs(start.y - end.y);
        }
    }
    private final record WeightedPoint(Point p, int weight) {}
    private final record Tuple(int min, int max) {}

    public static Board of(List<String> lines) {
        var board = new Board();
        for (String line : lines) {
            var points = line.split(" -> ");
            var coordinates = points[0].split(",");
            var start = new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
            coordinates = points[1].split(",");
            var end = new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
            board.segments.add(new Segment(start, end));
        }
        board.createMatrix();
        return board;
    }

    public int getSize() {
        return matrix[0].length;
    }

    public int getWeight(final int x, final int y) {
        var point = matrix[x][y];
        if (point != null) {
            return point.weight;
        }
        return 0;
    }

    private final int getMax() {
        int max = 0;
        for (Segment segment : segments) {
            if (segment.start.x > max) {
                max = segment.start.x;
            }
            if (segment.start.y > max) {
                max = segment.start.y;
            }
        }
        return max;
    }

    private void createMatrix() {
        var size = this.getMax()+1;
        this.matrix = new WeightedPoint[size][size];
        for (var segment : this.segments) {
            if (segment.isHorizontal()) {
                final int y = segment.start.y;
                var tuple = getTuple(segment, true);
                for (int i = tuple.min; i <= tuple.max; i++) {
                    if (this.matrix[i][y] == null) {
                        this.matrix[i][y] = new WeightedPoint(new Point(i, y), 1);
                    } else {
                        this.matrix[i][y] = new WeightedPoint(new Point(i, y), this.matrix[i][y].weight() + 1);
                    }
                }
            } else if (segment.isVertical()) {
                final int x = segment.start.x;
                var tuple = getTuple(segment, false);
                for (int i = tuple.min; i <= tuple.max; i++) {
                    if (this.matrix[x][i] == null) {
                        this.matrix[x][i] = new WeightedPoint(new Point(x, i), 1);
                    } else {
                        this.matrix[x][i] = new WeightedPoint(new Point(x, i), this.matrix[x][i].weight() + 1);
                    }
                }
            } else if (segment.isDiagonal()) {
                int incrementX = 1;
                int incrementY = 1;
                if (segment.start.x > segment.end.x) {
                    incrementX = -1;
                }
                if (segment.start.y > segment.end.y) {
                    incrementY = -1;
                }
                int x = segment.start.x;
                int y = segment.start.y;
                do {
                    if (this.matrix[x][y] == null) {
                        this.matrix[x][y] = new WeightedPoint(new Point(x, y), 1);
                    } else {
                        this.matrix[x][y] = new WeightedPoint(new Point(x, y), this.matrix[x][y].weight() + 1);
                    }
                    x += incrementX;
                    y += incrementY;
                } while (x != segment.end.x + incrementX && y != segment.end.y + incrementY);
                // add increment to also tick the last point of the segment
            }
        }
    }

    private Tuple getTuple(Segment segment, boolean isHorizontal) {
        Tuple tuple;
        if (isHorizontal) {
            if (segment.start.x < segment.end.x) {
                tuple = new Tuple(segment.start.x, segment.end.x);
            } else {
                tuple = new Tuple(segment.end.x, segment.start.x);
            }
        } else {
            if (segment.start.y < segment.end.y) {
                tuple = new Tuple(segment.start.y, segment.end.y);
            } else {
                tuple = new Tuple(segment.end.y, segment.start.y);
            }
        }
        return tuple;
    }
}
