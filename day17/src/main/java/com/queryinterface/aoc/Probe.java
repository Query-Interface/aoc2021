package com.queryinterface.aoc;

public class Probe {
    private long x = 0;
    private long y = 0;
    private long vx = 0;
    private long vy = 0;

    public Probe(long velocitX, long velocityY) {
        this.vx = velocitX;
        this.vy = velocityY;
    }

    public Point getPosition() {
        return new Point(this.x, this.y);
    }

    public long getVx() {
        return this.vx;
    }

    public long getVy() {
        return this.vy;
    }

    public void update() {
        x += vx;
        y += vy;
        if (vx > 0) {
            vx--;
        } else if (vx < 0) {
            vx++;
        }
        vy--;
    }
}
