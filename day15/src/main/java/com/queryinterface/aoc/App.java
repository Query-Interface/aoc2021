package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List; 

public class App
{
   private Board board;
   public static final String ANSI_RESET = "\u001B[0m";
   public static final String ANSI_COLOR = "\u001B[31m";

  public static void main( String[] args ) throws IOException {
    var board = Board2.of(getData());
    var app = new Dijkstra();
    var graph = app.calculateShortestPathFromSource(board);
    var path = app.getPath(graph.getNode(board.getSize()-1, board.getSize()-1));
    int count = 0;
    for (Node node : path) {
      count += node.getLevel();
    }
    //display(board, path);
    System.out.println(count);
  }

  public static void display(Board board, List<Node> path) {
    for (int i=0; i< board.getSize(); i++) {
      for (int j=0; j<board.getSize(); j++) {
        var node = board.getNode(i,j);
        if (path.contains(node)) {
          System.out.print(ANSI_COLOR + node.getLevel() + ANSI_RESET);
        } else {
          System.out.print(node.getLevel());
        }
      }
      System.out.print(System.getProperty("line.separator"));
    }
  }
  public static void display(Board2 board, List<Node> path) {
    for (int i=0; i< board.getSize(); i++) {
      for (int j=0; j<board.getSize(); j++) {
        var node = board.getNode(i,j);
        if (path.contains(node)) {
          System.out.print(ANSI_COLOR + node.getLevel() + ANSI_RESET);
        } else {
          System.out.print(node!=null?node.getLevel():'#');
        }
      }
      System.out.print(System.getProperty("line.separator"));
    }
  }

  public App(final Board board) {
    this.board = board;
  }

  private static List<String> getData() throws IOException {
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