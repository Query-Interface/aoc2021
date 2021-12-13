package com.queryinterface.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Path {
    
    private List<Cave> caves = new ArrayList<>();
    private final boolean acceptDouble;
    private boolean containsDouble = false;
    
    public Path(boolean acceptDouble) {
        this.acceptDouble = acceptDouble;
    }

    public static Path copyOf(final Path path) {
        Path p = new Path(path.acceptDouble);
        p.caves = new ArrayList<>(path.caves);
        p.containsDouble = path.containsDouble;
        return p;
    }

    public void add(final Cave cave) {
        this.caves.add(cave);
    }

    public Cave get(int index) {
        return this.caves.get(index);
    }

    public boolean canAddCave(final Cave cave) {
        if (!cave.isBig()) {
            if (!this.caves.contains(cave)) {
                return true;
            } else if (acceptDouble && !(cave.getName().equals("start") || cave.getName().equals("end"))) {
                var occurrences = this.caves.stream().filter(c -> !c.isBig()).collect(
                    Collectors.groupingBy(c -> c.getName(), Collectors.summingInt(c -> 1))
                );
                if (occurrences.values().stream().filter(count -> count > 1).count() < 1) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public int size() {
        return this.caves.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (Cave cave : caves) {
            builder.append(cave);
            builder.append(',');
        }
        builder.append(']');
        return builder.toString();
    }
}
