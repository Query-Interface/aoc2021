package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List; 

public class App
{
  public static void main( String[] args ) throws IOException {
    var board = getBoard();
    var size = board.getSize();
    int count = 0;
    for (int x=0; x < size; x++) {
      for (int y=0; y < size; y++) {
        int weight = board.getWeight(x, y);
        if (weight >= 2) {
          count++;
        }
      }
    }
    System.out.println(count);   
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