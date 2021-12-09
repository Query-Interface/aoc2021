package com.queryinterface.aoc;

import java.util.List;
import java.util.stream.IntStream;
import java.util.ArrayList;

public class Board {
    private List<List<Cell>> rows = new ArrayList<>();

    public static Board of(final List<String> lines) {
        Board board = new Board();
        for (String line : lines) {
            List<Cell> row = new ArrayList<>();
            String[] values = line.split(" ");
            for (String value : values) {
                if (!value.isEmpty()) {
                    row.add(new Cell(Integer.parseInt(value), false));
                }
            }
            board.rows.add(row);
        }
        return board;
    }

    public boolean checkValue(final int value) {
        boolean foundAtListOneValue = false;
        for (var cells: this.rows) {
            for (int i=0; i < cells.size(); i++) {
                Cell c = cells.get(i);
                if (c.value() == value) {
                    cells.set(i, new Cell(value, true));
                    foundAtListOneValue = true;
                }
            }
        }
        return foundAtListOneValue;
    }

    public boolean isCompleted() {
        // check if one row is completed
        for (List<Cell> cells : rows) {
            if (cells.stream().filter(c -> c.checked()).count() == cells.size()) {
                return true;
            }
        }
        // check column
        for (int i=0; i<rows.get(0).size(); i++) {
            if (isColumnCompleted(i)) {
                return true;
            }
        }
        return false;
    }

    public int getScore() {
        int score = 0;
        for (List<Cell> cells : rows) {
            score += cells.stream().filter(c -> !c.checked()).flatMapToInt(c -> IntStream.of(c.value())).sum();
        }
        return score;
    }

    private boolean isColumnCompleted(final int index) {
        List<Cell> column = new ArrayList<>();
        for (List<Cell> cells : rows) {
            column.add(cells.get(index));
        }
        return column.stream().filter(c -> c.checked()).count() == column.size();
    }

    private final record Cell(int value, boolean checked) {}
}
