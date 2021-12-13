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
    var data = getLines();
    var separatorIndex = data.indexOf("");

    var paperData = data.subList(0, separatorIndex);
    var foldData = data.subList(separatorIndex+1, data.size());

    var paper = Paper.of(paperData);
    var instructions = getInstructions(foldData);

    var result = paper.fold(instructions.get(0));
    System.out.println(result.dots());

    var workingPaper = paper;
    for (var instruction : instructions) {
      workingPaper = workingPaper.fold(instruction);
    }
    System.out.println(workingPaper);  // AHGCPGAU
  }


  private static List<Paper.Fold> getInstructions(final List<String> lines) {
    return lines.stream().map(l -> {
      var parts = l.split(" ");
      var instructions = parts[2].split("=");
      return new Paper.Fold(instructions[0].charAt(0), Integer.parseInt(instructions[1]));
    }).toList();
  }

  private static List<String> getLines() throws IOException {
    List<String> lines = new ArrayList<>();
    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      String line;
      while ((line = reader.readLine()) != null) {
          lines.add(line);
      }
    }
    return lines;
  }
}