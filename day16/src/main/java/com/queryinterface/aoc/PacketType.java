package com.queryinterface.aoc;

import java.util.Arrays;
import java.util.Optional;

public enum PacketType {
    SUM(0),
    PRODUCT(1),
    MINIMUM(2),
    MAXIMUM(3),
    LITERAL(4),
    GREATER_THAN(5),
    LESS_THAN(6),
    EQUAL_TO(7);

    private final int value;

    PacketType(int value) {
        this.value = value;
    }

    public static Optional<PacketType> valueOf(int value) {
        return Arrays.stream(values())
            .filter(type -> type.value == value)
            .findFirst();
    }
}
