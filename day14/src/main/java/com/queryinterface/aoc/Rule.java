package com.queryinterface.aoc;

public record Rule(String pair, char newElement) {
    public static Rule of(final String data) {
        var parts = data.split(" -> ");
        return new Rule(parts[0], parts[1].charAt(0));
    }
}