package com.queryinterface.aoc;

public class Fish {
    private int daysBeforeBirth;

    public Fish(final int days) {
        this.daysBeforeBirth = days;
    }

    public int getDays() {
        return this.daysBeforeBirth;
    }

    public boolean update() {
        if (this.daysBeforeBirth == 0) {
            this.daysBeforeBirth = 6;
            return true;
        }
        this.daysBeforeBirth--;
        return false;
    }
}
