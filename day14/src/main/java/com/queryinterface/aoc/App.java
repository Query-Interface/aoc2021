package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream; 

public class App
{
  public static void main( String[] args ) throws IOException {
    var data = getLines();
    String template = data.get(0);
    var rules = data.subList(2, data.size()).stream().map(Rule::of).toList();

    part2(template, rules);
  }

  private static void part2(String template, List<Rule> rules) {
    Polymerizr2 p = new Polymerizr2(template, rules);
    for (int i=0; i<40; i++) {
      p.apply();
    }

    Map<Character, Long> occurences = new HashMap<>();
    var map = p.getResult();
    for (var entry: map.entrySet()) {
      var pair = entry.getKey().pair();
      for (var character : pair.toCharArray()) {
        if (occurences.containsKey(character)) {
          long count = occurences.get(character) + entry.getValue();
          occurences.put(character, count);
        } else {
          occurences.put(character, entry.getValue());
        }
      }
    }

    var key = occurences.keySet().iterator().next();
    long max = occurences.get(key);
    long min = max;

    for (var entry: occurences.entrySet()) {
      System.out.println(entry.getKey() + " " + entry.getValue());
      if (entry.getValue() > max) {
        max = entry.getValue();
      } else if (entry.getValue() < min) {
        min = entry.getValue();
      }
    }
    System.out.println(min);
    System.out.println(max);
    System.out.println(Math.ceil((max-min)/2));
  }

  private static void part1(String template, List<Rule> rules) {
    Polymerizr p = new Polymerizr(template, rules);
    for (int i=0; i<10; i++) {
      p.apply();
    }
    System.out.println(p.getResult());
    Stream<Character> characterStream = p.getResult().chars().mapToObj(c -> (char) c);
    Map<Character, Integer> map = characterStream.collect(Collectors.groupingBy(c -> c, Collectors.summingInt(c -> 1)));
    long max = map.values().stream().mapToLong(e -> e).max().getAsLong();
    long min = map.values().stream().mapToLong(e -> e).min().getAsLong();

    System.out.println(max - min);
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