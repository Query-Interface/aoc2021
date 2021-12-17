package com.queryinterface.aoc;

public record Target(Range rangeX, Range rangeY) {
    
    public static Target of(final String data) {
        var parts = data.split(",");
        Range rx = Range.of(parts[0].substring(2));
        Range ry = Range.of(parts[1].substring(3));
        return new Target(rx, ry);
    }

    public boolean isNotTooFar(Point point) {
        return point.x() <= rangeX.max && point.y() >= rangeY.min;
    }

    public boolean isReached(Point point) {
        final boolean inXRange = point.x() >= rangeX.min && point.x() <= rangeX.max;
        final boolean inYRange = point.y() >= rangeY.min && point.y() <= rangeY.max;

        return inXRange && inYRange;
    }

    @Override
    public String toString() {
        return "{Rx: "+ rangeX +" , Ry: "+ rangeY +"}";
    }

    public static record Range (long min, long max) {
        public static Range of(final String data) {
            var range =data.split("\\.\\.");
            return new Range(Long.parseLong(range[0]), Long.parseLong(range[1]));
        }

        @Override
        public String toString() {
            return "["+min+".."+max+"]";
        }
    }
}
