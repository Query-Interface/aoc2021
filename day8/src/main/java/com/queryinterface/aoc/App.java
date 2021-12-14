package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set; 

public class App
{
  public static void main( String[] args ) throws IOException {
    final var data = getData();
    List<Signal> signals = new ArrayList<>();
    for (String s : data) {
      signals.add(Signal.of(s));
    }

    part1(signals);
    part2(signals);
  }

  private static void part1(final List<Signal> signals) {
    final Set<Integer> sizes = new HashSet<>(List.of(2,3,4,7));
    int count = 0;
    for (var signal : signals) {
      count += signal.outputs.stream().filter(s -> sizes.contains(s.length())).count();
    }
    
    System.out.println(count);
  }

  private static void part2(final List<Signal> signals) {
    System.out.println(signals.stream().map(Resolver::new).map(r -> r.decode()).mapToLong(l -> l).sum());

  }

  private static List<String> getData() throws IOException {
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