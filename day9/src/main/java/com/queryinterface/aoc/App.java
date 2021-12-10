package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream; 

public class App
{
  public static void main( String[] args ) throws IOException {
    var board = getBoard();
    //var size = board.getSize();
    var points = board.getPoints();
    System.out.println(points.stream().filter(p -> p.value() < p.left().value()
                                                && p.value() < p.right().value()
                                                && p.value() < p.up().value()
                                                && p.value() < p.down().value())
                                      .flatMapToInt(p -> IntStream.of(p.value() + 1))
                                      .sum());
  }

  private static Board getBoard() throws IOException {
    List<String> lines = new ArrayList<>();
    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      String line;
      while ((line = reader.readLine()) != null) {
          lines.add(line);
      }
    }
    return Board.of(lines);
  }
}