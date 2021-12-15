package com.queryinterface.aoc;

import java.util.ArrayList;
import java.util.List;

public class Cave {
    
    private final Kind kind;
    private final String name;
    private List<Cave> connections = new ArrayList<>();

    public Cave(String name) {
        this.name = name;
        this.kind = name.toUpperCase().equals(name) ? Kind.Big : Kind.Small;
    }

    public String getName() {
        return this.name;
    }

    public boolean isBig() {
        return this.kind == Kind.Big;
    }

    public void addConnection(final Cave cave) {
        this.connections.add(cave);
    }

    public List<Cave> getConnections() {
        return this.connections;
    }

    @Override
    public String toString() {
        return this.name;
    }

    enum Kind {
        Small,
        Big
    }
}
