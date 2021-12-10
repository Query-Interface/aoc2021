package com.queryinterface.aoc;

import java.util.ArrayList;
import java.util.List;

public class Chunk {
    private final char delimiter;
    private State state;
    private List<Chunk> children = new ArrayList<>();

    public Chunk(final char delimiter) {
        this.delimiter = delimiter;
        this.state = State.OPEN;
    }

    public char getDelimiter() {
        return this.delimiter;
    }

    public State getState() {
        return this.state;
    }

    public void setState(final State state) {
        this.state = State.CLOSE;
    }

    public void addChunk(final Chunk chunk) {
        if (this.state == State.CLOSE) {
            throw new RuntimeException("Chunk is closed");
        }
        this.children.add(chunk);
    }

    public Chunk getOpenChunk() {
        if (children.size() > 0) {
            var lastChunk = children.get(children.size() - 1);
            if (lastChunk.getState() == State.OPEN) {
                var child = lastChunk.getOpenChunk();
                if (child != null) {
                    return child;
                } else {
                    return lastChunk;
                }
            }
        }
        return null;
    }

    public char getCloseTag() {
        switch (delimiter) {
            case ChunkDelimiter.OPEN_ROUND_BRACKET:
                return ChunkDelimiter.CLOSE_ROUND_BRACKET;
            case ChunkDelimiter.OPEN_SQUARE_BRACKET:
                return ChunkDelimiter.CLOSE_SQUARE_BRACKET;
            case ChunkDelimiter.OPEN_CURLY_BRACKET:
                return ChunkDelimiter.CLOSE_CURLY_BRACKET;
            case ChunkDelimiter.OPEN_COMPARISON:
                return ChunkDelimiter.CLOSE_COMPARISON;
            default:
                return ';';
        }
    }
}
