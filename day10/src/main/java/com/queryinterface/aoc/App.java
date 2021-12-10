package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class App
{
  public static void main( String[] args ) throws IOException {
    var board = getLines();
    var errorCount = board.stream().filter(l -> l.hasError())
                  .flatMapToInt(l -> IntStream.of(l.getError().getScore()))
                  .sum();
    System.out.println("Error syntax: " + errorCount);   

    var autoCompleteScores = board.stream().filter(l -> !l.hasError())
                  .map(l -> l.getAutoCompleteScore())
                  .sorted()
                  .collect(Collectors.toList());

    System.out.println("Autocomplete:" + autoCompleteScores.get((int)Math.floor(autoCompleteScores.size()/2)));
  }

  

  private static List<Line> getLines() throws IOException {
    List<Line> lines = new ArrayList<>();
    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      String line;
      while ((line = reader.readLine()) != null) {
          lines.add(Line.of(line));
      }
    }
    return lines;
  }
}