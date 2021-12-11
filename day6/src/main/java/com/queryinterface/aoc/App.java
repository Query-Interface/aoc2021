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
    var fishes = getFishes();
    //part1(fishes);
    part2(fishes);
  }

  private static void part1(List<Fish> fishes) {
    for (int i=0; i<80;i++) {
      var babies = new ArrayList<Fish>();
      for (Fish fish : fishes) {
        if (fish.update())
        {
          babies.add(new Fish(8));
        }
      }
      fishes.addAll(babies);
    }
    System.out.println(fishes.size());
  }

  private static void part2(List<Fish> fishes) {
    long daysLeft0 = fishes.stream().filter(f -> f.getDays() == 0).count();
    long daysLeft1 = fishes.stream().filter(f -> f.getDays() == 1).count();
    long daysLeft2 = fishes.stream().filter(f -> f.getDays() == 2).count();
    long daysLeft3 = fishes.stream().filter(f -> f.getDays() == 3).count();
    long daysLeft4 = fishes.stream().filter(f -> f.getDays() == 4).count();
    long daysLeft5 = fishes.stream().filter(f -> f.getDays() == 5).count();
    long daysLeft6 = fishes.stream().filter(f -> f.getDays() == 6).count();
    long daysLeft7 = 0;
    long daysLeft8 = 0;

    for (int i=0; i<256; i++) {
      long temp = daysLeft0;
      daysLeft0 = daysLeft1;
      daysLeft1 = daysLeft2;
      daysLeft2 = daysLeft3;
      daysLeft3 = daysLeft4;
      daysLeft4 = daysLeft5;
      daysLeft5 = daysLeft6;
      daysLeft6 = daysLeft7 + temp;
      daysLeft7 = daysLeft8;
      daysLeft8 = temp;
    }
    long count = daysLeft0+daysLeft1+daysLeft2+daysLeft3+daysLeft4+daysLeft5+daysLeft6+daysLeft7+daysLeft8;
    System.out.println(count);
  }

  private static List<Fish> getFishes() throws IOException {
    List<Fish> fishes = new ArrayList<>();
    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
        String line = reader.readLine();
        var parts = line.split(",");
        for (String p : parts) {
          fishes.add(new Fish(Integer.parseInt(p)));
        }
    }
    return fishes;
  }
}