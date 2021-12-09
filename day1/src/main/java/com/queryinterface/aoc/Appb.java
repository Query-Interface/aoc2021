package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.IntStream;
import java.util.ArrayList;

public class Appb
{
  public static void main( String[] args ) throws IOException {
    int result = 0;
    List<Integer> first = new ArrayList<>();
    List<Integer> second = new ArrayList<>();
    List<Integer> third = new ArrayList<>();
    List<Integer> windows = new ArrayList<>();

    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat"); 
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
        String line;
        int firstValue = Integer.parseInt(reader.readLine());
        first.add(firstValue);
        int secondValue = Integer.parseInt(reader.readLine());
        first.add(secondValue);
        second.add(secondValue);
        while ((line = reader.readLine()) != null) {
          int value = Integer.parseInt(line);
          first.add(value);
          second.add(value);
          third.add(value);
          if (first.size() == 3) {
            windows.add(first.stream().mapToInt(Integer::intValue).sum());
            first.clear();
          }
          if (second.size() == 3) {
            windows.add(second.stream().mapToInt(Integer::intValue).sum());
            second.clear();
          }
          if (third.size() == 3) {
            windows.add(third.stream().mapToInt(Integer::intValue).sum());
            third.clear();
          }
        }
    }
    System.out.println(windows.size());
    for (int i=1; i<windows.size(); i++) {
      if (windows.get(i) - windows.get(i-1) > 0) {
        result += 1;
      }
    }
    System.out.println(result);
  }
}