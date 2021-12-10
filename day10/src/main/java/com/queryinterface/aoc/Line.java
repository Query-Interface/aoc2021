package com.queryinterface.aoc;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class Line {
    private TagError error = null;
    private List<Chunk> chunks = new ArrayList<>();
    private List<Character> missingTags;

    public static Line of(String s) {
        Line line = new Line();
        line.parse(s);
        return line;
    }

    public List<Character> getMissingTags() {
        if (this.missingTags == null) {
            this.missingTags = new ArrayList<>();
            Chunk chunk;
            while ((chunk = getOpenChunk()) != null) {
                missingTags.add(chunk.getCloseTag());
                chunk.setState(State.CLOSE);
            }
        }
        return missingTags;
    }

    public long getAutoCompleteScore() {
        long score = 0;
        var tags = getMissingTags();
        for (Character tag : tags) {
            score *= 5;
            score += getAutoCompleteScoreForTag(tag);
        }
        return score;
    }

    private int getAutoCompleteScoreForTag(char tag) {
        int score = 0;
        switch (tag) {
            case ChunkDelimiter.CLOSE_ROUND_BRACKET:
                score = 1;
                break;
            case ChunkDelimiter.CLOSE_SQUARE_BRACKET:
                score = 2;
                break;
            case ChunkDelimiter.CLOSE_CURLY_BRACKET:
                score = 3;
                break;
            case ChunkDelimiter.CLOSE_COMPARISON:
                score = 4;
                break;
        }
        return score;
    }

    private void parse(String s) {
        var iterator = new StringCharacterIterator(s);
        for (char tag = iterator.first(); tag != StringCharacterIterator.DONE; tag = iterator.next()) {
            if (isOpenTag(tag)) {
                var parent = getOpenChunk();
                Chunk chunk = new Chunk(tag);
                if (parent == null) {
                    this.chunks.add(chunk);
                } else {
                    parent.addChunk(chunk);
                }
            } else {
                var chunk = getOpenChunk();
                if (chunk == null) {
                    this.error = new TagError(tag);
                    break;
                }
                if (chunk.getCloseTag() != tag) {
                    this.error = new TagError(tag);
                    break;
                }
                chunk.setState(State.CLOSE);
            }
        }
    }

    private boolean isOpenTag(char tag) {
        switch(tag) {
            case ChunkDelimiter.OPEN_ROUND_BRACKET:
            case ChunkDelimiter.OPEN_SQUARE_BRACKET:
            case ChunkDelimiter.OPEN_CURLY_BRACKET:
            case ChunkDelimiter.OPEN_COMPARISON:
                return true;
            default:
                return false;
        }
    }

    private Chunk getOpenChunk() {
        if (chunks.size() > 0) {
            var lastChunk = chunks.get(chunks.size() - 1);
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

    public boolean hasError() {
        return this.error != null;
    }

    public TagError getError() {
        return this.error;
    }

    final record TagError(char error) {
        public int getScore() {
            int score = 0;
            switch (error) {
                case ChunkDelimiter.CLOSE_ROUND_BRACKET:
                    score = 3;
                    break;
                case ChunkDelimiter.CLOSE_SQUARE_BRACKET:
                    score = 57;
                    break;
                case ChunkDelimiter.CLOSE_CURLY_BRACKET:
                    score = 1197;
                    break;
                case ChunkDelimiter.CLOSE_COMPARISON:
                    score = 25137;
                    break;
            }
            return score;
        }
    }
}
