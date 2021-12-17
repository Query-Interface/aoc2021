package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class App
{

  public static void main( String[] args ) throws IOException {
    var data = getData();
    var target = Target.of(data);
    System.out.println(target);

    long vxMax = target.rangeX().max();
    long vyMin = target.rangeY().min();
    long vyMax = -target.rangeY().min(); //since when y=0, a higher y will overshoot the target area

    long maxHeight = 0;
    long count = 0;
    for (long vx = 0; vx <= vxMax; vx++) {
      for (long vy = vyMin; vy <= vyMax; vy++) {
        var probe = new Probe(vx, vy);
        long maxY = 0;
        while (target.isNotTooFar(probe.getPosition())) {
          probe.update();
          var pos = probe.getPosition();
          maxY = Math.max(maxY, pos.y());
          if (target.isReached(pos)) {
            maxHeight = Math.max(maxHeight, maxY);
            count++;
            break;
          }
        }
      }
    }
    System.out.println(maxHeight);
    System.out.println(count);
  }

  private static String getData() throws IOException {
    String line;
    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      line = reader.readLine();
    }
    return line.substring("target area: ".length());
  }
}