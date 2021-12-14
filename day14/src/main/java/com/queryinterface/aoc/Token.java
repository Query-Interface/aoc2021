package com.queryinterface.aoc;

import java.util.List;
import java.util.Objects;

public record Token(String pair, char newElement) {
    public static Token of(final String data) {
        var parts = data.split(" -> ");
        return new Token(parts[0], parts[1].charAt(0));
    }

    public List<String> generate() {
        char value[] = new char[2];
        value[0] = this.pair.charAt(0);
        value[1] = newElement;
        var token1 = new String(value);
        value[0] = newElement;
        value[1] = this.pair.charAt(1);
        var token2 = new String(value);
        return List.of(token1, token2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return pair.equals(token.pair);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pair);
    }
}
