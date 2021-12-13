package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream; 

public class App
{
  public static void main( String[] args ) throws IOException {
    var data = getData();
    var crabs = Stream.of(data.split(",")).map(s -> new Crab(Integer.parseInt(s))).toList();
    int max = crabs.stream().map(c -> c.position()).max((Integer val1, Integer val2) -> Integer.compare(val1, val2)).get();

    part1(crabs, max);
    part2(crabs, max);
  }

  private static void part1(List<Crab> crabs, int max) {
    List<Integer> paths = new ArrayList<>();
    for (int i=0; i<max; i++) {
      var workingCopy = new ArrayList<Crab>(crabs);
      int fuel = 0;
      for (Crab crab : workingCopy) { 
        fuel += Math.abs(crab.position() - i);
      }
      paths.add(fuel);
    }

    var leastFuel = paths.stream().min((Integer val1, Integer val2) -> Integer.compare(val1, val2)).get();
    System.out.println(leastFuel);
  }

  private static void part2(List<Crab> crabs, int max) {
    List<Long> paths = new ArrayList<>();
    for (int i=0; i<max; i++) {
      var workingCopy = new ArrayList<Crab>(crabs);
      long fuel = 0;
      for (Crab crab : workingCopy) { 
        var nbMoves = Math.abs(crab.position() - i);
        int increment=1;
        for (int m=0;m<nbMoves;m++) {
          fuel += increment++;
        }
      }
      paths.add(fuel);
    }

    var leastFuel = paths.stream().min((Long val1, Long val2) -> Long.compare(val1, val2)).get();
    System.out.println(leastFuel);
  }

  private static String getData() throws IOException {
    String line;
    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
       line = reader.readLine();
    }
    return line;
  }

  
}