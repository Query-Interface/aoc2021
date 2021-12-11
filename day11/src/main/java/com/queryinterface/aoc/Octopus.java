package com.queryinterface.aoc;

import java.util.ArrayList;
import java.util.List;

public class Octopus {
    private int level = 0;
    private int nbFlash = 0;
    private List<Octopus> neighbours = new ArrayList<>();
    private int step;
    private boolean hasFlashed = false;

    public Octopus(final int powerLevel) {
        this.level = powerLevel;
    }

    public void addNeighbour(final Octopus octopus) {
        this.neighbours.add(octopus);
    }

    public void update(final boolean resetFlash) {
        if (resetFlash) {
            this.hasFlashed = false;
        }
        if (!this.hasFlashed) {
            this.level++;
        }
    }


    public void updateFlash() {
        if (!this.hasFlashed) {
            if (this.level > 9) {
                this.hasFlashed = true;
                this.nbFlash++;
                for (Octopus octopus : neighbours) {
                    octopus.update(false);
                    octopus.updateFlash();
                }
                this.level = 0;
            }
        }
    }

    public boolean hasFlashed() {
        return this.hasFlashed;
    }

    public void flash(final int step) {
        this.hasFlashed = true;
        this.level = 0;
        this.nbFlash++;
        for (Octopus octopus : neighbours) {
            octopus.update(false);
        }
    }

    public int getNbFlash() {
        return this.nbFlash;
    }
}
