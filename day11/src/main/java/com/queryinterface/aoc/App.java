package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List; 

public class App
{
  public static void main( String[] args ) throws IOException {
    var grid = getBoard();
    setNeighbours(grid);

    //part1(grid);
    part2(grid);
  }

  private static void part1(final Octopus[][] grid) {
    for (int step=1; step <=100; step ++) {
      for (int i=0; i<10; i++) {
        for (int j=0; j<10; j++) {
          var octopus = grid[i][j];
          octopus.update(true);
        }
      }
      for (int i=0; i<10; i++) {
        for (int j=0; j<10; j++) {
          var octopus = grid[i][j];
          octopus.updateFlash();
        }
      }
    }

    int count = 0;

    for (int i=0; i<10; i++) {
      for (int j=0; j<10; j++) {
        var octopus = grid[i][j];
        count += octopus.getNbFlash();
      }
    }
    System.out.println(count);
  }

  private static void part2(final Octopus[][] grid) {
    int step = 1;
    while (true) {
      for (int i=0; i<10; i++) {
        for (int j=0; j<10; j++) {
          var octopus = grid[i][j];
          octopus.update(true);
        }
      }
      for (int i=0; i<10; i++) {
        for (int j=0; j<10; j++) {
          var octopus = grid[i][j];
          octopus.updateFlash();
        }
      }
      int nbFlash = 0;
      for (int i=0; i<10; i++) {
        for (int j=0; j<10; j++) {
          var octopus = grid[i][j];
          if (octopus.hasFlashed()) {
            nbFlash++;
          }
        }
      }
      if (nbFlash == 100) {
        break;
      }
      step++;
    }

    System.out.println(step);
  }

  private static Octopus[][] getBoard() throws IOException {
    Octopus[][] grid = new Octopus[10][10];
    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      String line;
      int i=0;
      while ((line = reader.readLine()) != null) {
        int j=0;
        var iterator = new StringCharacterIterator(line);
        for (char c = iterator.first(); c != StringCharacterIterator.DONE; c = iterator.next()) {
          grid[i][j] = new Octopus(Character.getNumericValue(c));
          j++;
        }
        i++;
      }
    }
    return grid;
  }

  private static void setNeighbours(final Octopus[][] grid) {
    for (int i=0; i<10; i++) {
      for (int j=0; j<10; j++) {
        var o = grid[i][j];
        if (i > 0) { // up
          o.addNeighbour(grid[i-1][j]);
          if (j > 0) { // top-left
            o.addNeighbour(grid[i-1][j-1]);
          }
          if (j < 9) {  // top-right
            o.addNeighbour(grid[i-1][j+1]);
          }
        }
        if (i < 9) { // down
          o.addNeighbour(grid[i+1][j]);
          if (j > 0) {
            o.addNeighbour(grid[i+1][j-1]);
          }
          if (j < 9) {
            o.addNeighbour(grid[i+1][j+1]);
          }
        }
        if (j > 0) {// left
          o.addNeighbour(grid[i][j-1]);
        }
        if (j < 9) { // right
          o.addNeighbour(grid[i][j+1]);
        }
      }
    }
  }
}