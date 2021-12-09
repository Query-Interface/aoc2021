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
    List<Board> boards = new ArrayList<>();
    String drawOrder;
    // create boards
    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      drawOrder = reader.readLine();
      reader.readLine();
      List<String> lines = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        if (line == null || line.isEmpty()) {
          boards.add(Board.of(lines));
          lines.clear();
        } else {
          lines.add(line);
        }
      }
    }
    
    String[] draws = drawOrder.split(",");
    for (String draw : draws) {
      int value = Integer.parseInt(draw);
      for (Board board: boards) {
        if (board.checkValue(value)) {
          if (board.isCompleted()) {
            System.out.println(board.getScore() * value);
            return;
          }
        }
      }
    }
  }
}