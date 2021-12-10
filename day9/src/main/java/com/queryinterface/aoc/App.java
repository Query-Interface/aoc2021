package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream; 
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;

import com.queryinterface.aoc.Board.PointAdv;

public class App
{
  public static void main( String[] args ) throws IOException {
    var board = getBoard();
    var points = board.getPoints();
    System.out.println(points.stream().filter(p -> p.value() < p.left().value()
                                                && p.value() < p.right().value()
                                                && p.value() < p.up().value()
                                                && p.value() < p.down().value())
                                      .flatMapToInt(p -> IntStream.of(p.value() + 1))
                                      .sum());
    var lowPoints = points.stream().filter(p -> p.value() < p.left().value()
                                    && p.value() < p.right().value()
                                    && p.value() < p.up().value()
                                    && p.value() < p.down().value())
                                    .toList();
    List<Set<PointAdv>> bassins = new ArrayList<>();
    for (var lp : lowPoints) {
      bassins.add(getBassin(board, lp));
    }
    Comparator<Set<PointAdv>> comparator = (b1, b2) -> Integer.compare(b1.size(), b2.size());
    bassins.sort(comparator.reversed());
    var values = bassins.stream().limit(3).flatMapToInt(b -> IntStream.of(b.size())).toArray();
    System.out.println(values[0] * values [1] * values [2]);
    exportBoard(board);
  }

  private static Set<PointAdv> getBassin(final Board board, final PointAdv point) {
    var bassin = new HashSet<PointAdv>();
    addPoints(board, bassin, point);
    return bassin;
  }

  private static void addPoints(final Board board, Set<PointAdv> bassin, PointAdv point) {
    if (point.value() < 9) {
      if (!bassin.contains(point)) {
        bassin.add(point);
        if (!point.up().equals(Board.NONE)) {
          addPoints(board, bassin, board.getPoint(point.up()));
        }
        if (!point.down().equals(Board.NONE)) {
          addPoints(board, bassin, board.getPoint(point.down()));
        }
        if (!point.left().equals(Board.NONE)) {
        addPoints(board, bassin, board.getPoint(point.left()));
        }
        if (!point.right().equals(Board.NONE)) {
        addPoints(board, bassin, board.getPoint(point.right()));
        }
      }
    }
  }

  private static Board getBoard() throws IOException {
    List<String> lines = new ArrayList<>();
    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      String line;
      while ((line = reader.readLine()) != null) {
          lines.add(line);
      }
    }
    return Board.of(lines);
  }

  private static void exportBoard(final Board board) throws IOException {
    BufferedImage bi = new BufferedImage(100*5, 100*5, BufferedImage.TYPE_INT_ARGB);
    var graphics = bi.createGraphics();
    for (int x = 0; x < 100; x++) {
      for (int y = 0; y < 100; y++) {
        graphics.setColor(getColor(board.getPoint(x, y).value()));
        graphics.fillRect(x*5, y*5, 5, 5);
      }
    }
    ImageIO.write(bi, "PNG", new File("c:\\temp\\map.PNG"));
  }

  private static Color getColor(final int value) {
    switch (value) {
      case 0:
        return Color.decode("0X000080");
      case 1:
        return Color.decode("0X0000CD");
      case 2:
        return Color.decode("0X4169E1");
      case 3:
        return Color.decode("0X1E90FF");
      case 4:
        return Color.decode("0X00BFFF");
      case 5:
        return Color.decode("0X87CEFA");
      case 6:
        return Color.decode("0X87CEEB");
      case 7:
        return Color.decode("0XADD8E6");
      case 8:
        return Color.decode("0XB0E0E6");
      default: // 9
        return Color.decode("0XB0C4DE");
    }
  }
}