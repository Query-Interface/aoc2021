package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fxyz3d.scene.paint.Palette;
import org.fxyz3d.shapes.primitives.Surface3DMesh;
import org.fxyz3d.geometry.Point3D;

import javafx.application.Application;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

//mostly from https://stackoverflow.com/questions/56943420/need-help-on-creating-triangle-mesh-from-height-map-javafx

public class App3D extends Application
{
  private static final List<Color> COLOR_LIST = Arrays.asList(Color.web("#000080"),
    Color.web("#0000CD"), Color.web("#4169E1"), Color.web("#1E90FF"), Color.web("#00BFFF"),
    Color.web("#87CEFA"), Color.web("#87CEEB"), Color.web("#ADD8E6"), Color.web("#B0E0E6"), Color.web("#B0C4DE"));

  private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
  private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
  
  private double mousePosX;
  private double mousePosY;
  private double mouseOldX;
  private double mouseOldY;

  public static void main( String[] args ) throws IOException {
    /*var board = getBoard();
    //var size = board.getSize();
    var points = board.getPoints();
    System.out.println(points.stream().filter(p -> p.value() < p.left().value()
                                                && p.value() < p.right().value()
                                                && p.value() < p.up().value()
                                                && p.value() < p.down().value())
                                      .flatMapToInt(p -> IntStream.of(p.value() + 1))
                                      .sum());

    exportBoard(board);*/
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Group sceneRoot = new Group();
    PerspectiveCamera camera = new PerspectiveCamera(true);
    camera.setNearClip(0.1);
    camera.setFarClip(10000.0);

    Scene scene = new Scene(sceneRoot, 1200, 800, true, SceneAntialiasing.BALANCED);
    scene.setCamera(camera);

    var board = getBoard();
    List<Point3D> data = processData(board);
    Surface3DMesh heightMapMesh = new Surface3DMesh(data);
    heightMapMesh.setDrawMode(DrawMode.FILL);
    heightMapMesh.setTextureModeVertices3D(new Palette.ListColorPalette(COLOR_LIST), p -> p.y);

    Surface3DMesh wireframe = new Surface3DMesh(data);
    wireframe.setTextureModeNone(Color.BLACK);

    Group mapGroup = new Group(heightMapMesh, wireframe);
    mapGroup.getTransforms().add(new Translate(0, 0, 0));
    sceneRoot.getChildren().addAll(mapGroup, new AmbientLight());
    sceneRoot.getTransforms().addAll(rotateX, rotateY);
    // set starting position
    rotateX.setAngle(-110);
    rotateY.setAngle(-45);
    sceneRoot.setTranslateX(0);
    sceneRoot.setTranslateY(-70);
    sceneRoot.setTranslateZ(260);

    scene.setOnMousePressed(event -> {
        mousePosX = event.getSceneX();
        mousePosY = event.getSceneY();
    });

    scene.setOnMouseDragged(event -> {
      mousePosX = event.getSceneX();
      mousePosY = event.getSceneY();
      if (event.getButton() == MouseButton.SECONDARY) {
        rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
        rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
      }
      mouseOldX = mousePosX;
      mouseOldY = mousePosY;
    });

    primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
      switch(event.getCode()){
          case LEFT:
            rotateY.setAngle(rotateY.getAngle() + 10);
              break;
          case RIGHT:
            rotateY.setAngle(rotateY.getAngle() - 10);
              break;
          case UP:
            rotateX.setAngle(rotateX.getAngle() + 10);
              break;
          case DOWN:
            rotateX.setAngle(rotateX.getAngle() - 10);
          case W: //w/s is for z
            sceneRoot.setTranslateZ(sceneRoot.getTranslateZ() + 10);
              break;
          case S:
            sceneRoot.setTranslateZ(sceneRoot.getTranslateZ() - 10);
              break;
          case A:// a/d is x axis
            primaryStage.setTitle("Height Map - A");
            sceneRoot.setTranslateX(sceneRoot.getTranslateX() + 10);
              break;
          case D:
            sceneRoot.setTranslateX(sceneRoot.getTranslateX() - 10);
              break;
          case SHIFT:// shift/contr is for y axis
            sceneRoot.setTranslateY(sceneRoot.getTranslateY() + 10);
              break;
          case CONTROL:
            sceneRoot.setTranslateY(sceneRoot.getTranslateY() - 10);
              break;
      }
      primaryStage.setTitle(getTitle(sceneRoot));
  });


    primaryStage.setTitle("Height Map ");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private String getTitle(Group sceneRoot) {
    StringBuilder builder = new StringBuilder();
    builder.append("Height Map - ");
    builder.append("Tx: ").append(sceneRoot.getTranslateX()).append("; ");
    builder.append("Ty: ").append(sceneRoot.getTranslateY()).append("; ");
    builder.append("Tz: ").append(sceneRoot.getTranslateZ()).append("; ");
    builder.append("Rx: ").append(rotateX.getAngle()).append("; ");
    builder.append("Ry: ").append(rotateY.getAngle()).append("; ");
    return builder.toString();
  }

  private List<Point3D> processData(final Board board) {
    var points = new ArrayList<Point3D>();
    for (int x = 0; x < 100; x++) {
      for (int y = 0; y < 100; y++) {
        points.add(new Point3D((float) x, board.getPoint(x, y).value(), (float) 100-y));
      }
    }
    return points;
  }

  private static Board getBoard() throws IOException {
    List<String> lines = new ArrayList<>();
    try (InputStream stream = App3D.class.getClassLoader().getResourceAsStream("input.dat");
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
      String line;
      while ((line = reader.readLine()) != null) {
          lines.add(line);
      }
    }
    return Board.of(lines);
  }


}