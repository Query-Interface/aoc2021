package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class Appb
{
  public static void main( String[] args ) throws IOException {
    List<String> lines = new ArrayList<>();
    try (InputStream stream = Appb.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    }


    //lines = getSample();
    int bitIndex = 0;
    List<String> copy = List.copyOf(lines);
    while (copy.size() > 1) {
      copy = filter(copy, bitIndex, BitCriteria.Most);
      bitIndex++;
    }
    System.out.println("o2 rating: "+ copy.get(0));
    int o2Rating = Integer.parseInt(copy.get(0), 2);

    bitIndex = 0;
    copy = List.copyOf(lines);
    while (copy.size() > 1) {
      copy = filter(copy, bitIndex, BitCriteria.Least);
      bitIndex++;
    }
    System.out.println("co2 rating: "+ copy.get(0));
    int co2Rating = Integer.parseInt(copy.get(0), 2);

    System.out.println("O2: " + o2Rating);
    System.out.println("CO2: " + co2Rating);
    System.out.println("Support rating: " + o2Rating * co2Rating);
  }

  private static List<String> filter(final List<String> lines, final int bitIndex, final BitCriteria criteria) {
    int result = 0;
    for (String line : lines) {
      if (line.charAt(bitIndex) == '1') {
        result++;
      }
    }
    if (criteria == BitCriteria.Most) {
      final char bit = result *2 >= lines.size() ? '1' : '0';
      return lines.stream().filter(line -> line.charAt(bitIndex) == bit).toList();
    }
    final char bit = result * 2 >= lines.size() ? '0' : '1';
    return lines.stream().filter(line -> line.charAt(bitIndex) == bit).toList();
  }

  private static List<String> getSample() {
    List<String> sample = new ArrayList<>();
    sample.add("00100");
    sample.add("11110");
    sample.add("10110");
    sample.add("10111");
    sample.add("10101");
    sample.add("01111");
    sample.add("00111");
    sample.add("11100");
    sample.add("10000");
    sample.add("11001");
    sample.add("00010");
    sample.add("01010");
    return sample;
  }

  enum BitCriteria {
    Most,
    Least
  }
}