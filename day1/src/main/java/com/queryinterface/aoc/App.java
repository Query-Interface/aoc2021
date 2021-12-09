package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class App
{
  public static void main( String[] args ) throws IOException
  {
    int result = 0;
    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat"); 
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
        String firstLine = reader.readLine();
        int previous = Integer.parseInt(firstLine);
        String line;
        while ((line = reader.readLine()) != null) {
          int current = Integer.parseInt(line);
          if (current > previous) {
            result++;
          }
          previous = current;
        }
    }
    System.out.println(result);
  }
}