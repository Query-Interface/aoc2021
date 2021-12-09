package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays; 

public class App
{
  public static void main( String[] args ) throws IOException {
    int digits[] = new int[12];
    int gamma[] = new int[12];
    int epsilon[] = new int[12];
    int nbLines = 0;
    Arrays.fill(digits, 0);
    //List<String> lines = new ArrayList<>();
    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      String line;
      while ((line = reader.readLine()) != null) {
        //lines.add(line);
        nbLines++;
        for (int i=0; i<12; i++) {
          if (line.charAt(i) == '1') {
            digits[i]++;
          }
        }
      }
    }
    for (int i=0; i<12; i++) {
        if (digits[i] > nbLines/2) {
          gamma[i] = 1;
          epsilon[i] = 0;
        } else {
            gamma[i] = 0;
            epsilon[i] = 1;
        }
    }
    int dGamma = convertToDecimal(gamma);
    int dEpsilon = convertToDecimal(epsilon);
    System.out.println("gamma: " + dGamma);
    System.out.println("epsilon: " + dEpsilon);
    System.out.println(dGamma * dEpsilon);
  }

  private static int convertToDecimal(int[] digits) {
    int result=0;
    for (int i=11; i >= 0; i--) {
      result += digits[i] * (int) Math.pow(2, (11-i));
    }
    return result;
  }
}