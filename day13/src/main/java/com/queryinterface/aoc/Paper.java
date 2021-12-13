package com.queryinterface.aoc;

import java.util.List;

public class Paper {

    public final record Point(int x, int y, char value) {};
    public final record Fold (char axis, int index) {};
    
    private Point matrix[][];

    public static Paper of(List<String> lines) {
        var paper = new Paper();
        var points = lines.stream().map(l -> { 
            var parts = l.split(",");
            return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), '#');
        }).toList();
        var xmax = points.stream().map(p -> p.x).max((Integer val1, Integer val2) -> Integer.compare(val1, val2));
        var ymax = points.stream().map(p -> p.y).max((Integer val1, Integer val2) -> Integer.compare(val1, val2));

        paper.matrix = new Point[xmax.get()+1][ymax.get()+1];
        for (int i=0; i<paper.matrix.length; i++) {
            for (int j=0; j<paper.matrix[0].length; j++) {
                paper.matrix[i][j] = new Point(i, j, '.');
            }
        }
        for (Point point : points) {
            paper.matrix[point.x][point.y] = point;
        }
        return paper;
    }

    public int getSize() {
        return matrix[0].length;
    }

    public Paper fold(Fold instruction) {
        var paper = new Paper();
        if (instruction.axis == 'x') {
            paper.matrix = new Point[instruction.index][this.matrix[0].length];
        } else {
            paper.matrix = new Point[this.matrix.length][instruction.index];
        }
        // copy subparts
        for (int i=0; i<paper.matrix.length; i++) {
            for (int j=0; j<paper.matrix[0].length; j++) {
                paper.matrix[i][j]=matrix[i][j];
            }
        }
        // apply folding values
        final int start = instruction.index+1;
        if (instruction.axis == 'x') {
            final int previousMaxIndex = this.matrix.length-1;
            for (int i=start; i<this.matrix.length; i++) {
                for (int j=0; j<this.matrix[0].length; j++) {
                    paper.matrix[previousMaxIndex-i][j]= this.matrix[i][j].value == '#' ? new Point(previousMaxIndex-i, j, '#'):paper.matrix[previousMaxIndex-i][j];
                }
            }
        } else {
            final int previousMaxIndex = this.matrix[0].length-1;
            for (int i=0; i<this.matrix.length; i++) {
                for (int j=start; j<this.matrix[0].length; j++) {
                    paper.matrix[i][previousMaxIndex-j]= this.matrix[i][j].value == '#' ? new Point(i, previousMaxIndex-j, '#'):paper.matrix[i][previousMaxIndex-j];
                }
            }
        }

        return paper;
    }

    public long dots() {
        long count = 0;
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[0].length; j++) {
                if (matrix[i][j].value == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[0].length; j++) {
                builder.append(matrix[i][j].value);
            }
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }
}
