package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List; 

public class App
{
  private List<Path> paths = new ArrayList<>();
  private final List<Cave> caves;

  public static void main( String[] args ) throws IOException {
    var caves = getCaves();
    var app = new App(caves);
    app.getPaths(true);
    var startToEndPaths = app.paths.stream().filter(l -> l.get(l.size()-1).getName().equals("end")).toList();
    System.out.println(startToEndPaths.size());   
  }

  public App(final List<Cave> caves) {
    this.caves = caves;
  }

  private void getPaths(boolean acceptDouble) {
    paths = new ArrayList<>();
    var start = getCaveByName(caves, "start");
    Path path = new Path(acceptDouble);
    paths.add(path);
    completePath(start, path);
  }

  private void completePath(final Cave node, Path currentPath) {
    currentPath.add(node);
    if (!node.getName().equals("end")) {
      var copy = Path.copyOf(currentPath);
      boolean isFirst = true;
      for (int i=0; i < node.getConnections().size(); i++) {
        var cave = node.getConnections().get(i);
        if (!copy.canAddCave(cave)) {
          continue;
        }
        if (isFirst) {
          isFirst = false;
          completePath(cave, currentPath);
        } else {
          var newPath = Path.copyOf(copy);
          paths.add(newPath);
          completePath(cave, newPath);
        }
      }
    }
  }

  private static List<Cave> getCaves() throws IOException {
    List<Cave> caves = new ArrayList<>();
    try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      String line;
      while ((line = reader.readLine()) != null) {
        Cave start, end;  
        var parts = line.split("-");
        start = getCaveByName(caves, parts[0]);
        end = getCaveByName(caves, parts[1]);
        start.addConnection(end);
        end.addConnection(start);
      }
    }
    return caves;
  }

  private static Cave getCaveByName(List<Cave> caves, final String name) {
    Cave cave;
    var result = caves.stream().filter(c -> c.getName().equals(name)).findFirst();
    if (result.isPresent()) {
      cave = result.get();
    } else {
      cave = new Cave(name);
      caves.add(cave);
    }
    return cave;
  }
}