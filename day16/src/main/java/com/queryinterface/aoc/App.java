package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class App
{
  public static void main( String[] args ) throws IOException {
    var data = getData();
    var parser = new Parser();
    var packets = parser.parse(data);
    //var packets = parser.parse("9C0141080250320F1802104A08");
    int count = 0;
    for (Packet packet : packets) {
      count += getVersionCount(packet);
    }
    System.out.println("#packets: "+ count);
    System.out.println("packet value: "+ packets.get(0).getValue());
  }

  private static int getVersionCount(final Packet packet) {
    int count = 0;
    count += packet.getVersion();
    for (Packet p : packet.getPackets()) {
        count += getVersionCount(p);
    }
    return count;
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