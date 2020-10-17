package dfa;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class DrawDFA {
  private DFA dfa;
  private String[] states;
  
  public DrawDFA (DFA dfa) {
    this.dfa = dfa;
    states = dfa.getStates();
  }

  public void getDFACanvas(StackPane canvasDFA) {
    int[][] centers = drawStateCircles(states.length, canvasDFA);
    drawTransitions(canvasDFA, centers);
   }

  private int linearSearch(String element){
    for (int i = 0; i < states.length; i++)
      if(states[i].equals(element))
        return i;
    return -1;
  }

   private void drawTransitions(StackPane pane, int[][] centers) {
     Color[] colours = {Color.INDIGO, Color.FUCHSIA, Color.FORESTGREEN, Color.DARKBLUE, Color.BLUEVIOLET, Color.TOMATO, Color.CHARTREUSE, Color.DARKORANGE};
     for (int i = 0; i < states.length; i++) {
       State currState = dfa.getDfa().get(states[i]);
       for (String character : dfa.getAlphabet()) {
         State destino = currState.getTransition(character);

         if (destino != null) {
          Group group = (Group)pane.getChildren().get(0);
          int colorAleatorio = (int)((Math.random()*10)%colours.length);
          System.out.println(colorAleatorio);
          if (destino.getId().equals(states[i])) {
            QuadCurve curve = new QuadCurve(centers[i][0]+15, centers[i][1]-15, centers[i][0], centers[i][1]-120, centers[i][0]-15, centers[i][1]-15);
            curve.setStroke(colours[colorAleatorio]);
            curve.setFill(null);
            drawArrowHeads(pane, colours[colorAleatorio], centers[i][0], centers[i][1]-120, centers[i][0]-15, centers[i][1]-15);
            Text texto = new Text(centers[i][0], centers[i][1]-70, character);
            texto.setFill(colours[colorAleatorio]);
            group.getChildren().addAll(curve, texto);
          }else {
            int indexEnStates = linearSearch(destino.getId());
            int[] point = getMiddlePoint(centers[i][0], centers[i][1], centers[indexEnStates][0], centers[indexEnStates][1]);
            Line transition = new Line(centers[i][0], centers[i][1], centers[indexEnStates][0], centers[indexEnStates][1]);
            transition.setStroke(colours[colorAleatorio]);
            drawArrowHeads(pane, colours[colorAleatorio], centers[i][0], centers[i][1], centers[indexEnStates][0], centers[indexEnStates][1]);
            System.out.println(String.format("char: %s srcX: %d srcY: %d destX: %d destY: %d",character, centers[i][0], centers[i][1], centers[indexEnStates][0], centers[indexEnStates][1]));
            Text texto = new Text(point[0]+5, point[1]+5, character);
            texto.setFill(colours[colorAleatorio]);
            group.getChildren().addAll(transition, texto); 
          }
         }
       }
     }
   }

  public void drawArrowHeads(StackPane pane, Color color, int srcX, int srcY, int destX, int destY) {
    Group group = (Group)pane.getChildren().get(0);

    double angle = Math.atan2((destY - srcY), (destX - srcX)) - Math.PI / 2.0;
    double sin = Math.sin(angle);
    double cos = Math.cos(angle);
    double arrowHeadSize = 15;

    double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + destX;
    double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + destY;
    double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + destX;
    double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + destY;
    
    Line h1 = new Line(x1, y1, destX, destY);
    h1.setStroke(color);
    Line h2 = new Line(x2, y2, destX, destY);
    h2.setStroke(color);

    group.getChildren().addAll(h1, h2);
  }

   public static int[] getMiddlePoint(int a, int b, int c, int d){
     int co = d - b, ca = c - a;
     int[] coordinate = {a+(5*ca/12), b+(5*co/12)};
     return coordinate;
   }

  private int[][] drawStateCircles(int states, StackPane stackPane) {
    stackPane.getChildren().removeAll(stackPane.getChildren());

    Group group = new Group();
    int bigRadious = getBigRadious(states);
    int[][] centers = getCenters(states, bigRadious);
    int i = 0;

    for (int[] point : centers) {
      int posX = point[0];
      int posY = point[1];
      State currState = dfa.getDfa().get(this.states[i]);
      Circle stateCircle = new Circle(posX, posY, 30, Color.YELLOW);
      stateCircle.setStroke(Color.BLACK);
      Text idState = new Text(posX, posY, this.states[i]);
      group.getChildren().addAll(stateCircle, idState);
      if (currState.isFinal()) {
        System.out.println(String.format("%s is final", this.states[i]));
        Circle finalCircle = new Circle(posX, posY, 25, new Color(1, 1, 0, 0));
        finalCircle.setStroke(Color.BLACK);
        group.getChildren().addAll(finalCircle);
      }
      ++i;
    }
    stackPane.getChildren().add(group);
    
    return centers;
  }

  private static int getBigRadious(int states){
    System.out.println(states);
    final int STATE_RADIUS = 30;
    final int SPACE_BETWEEN_STATES = 30;

    double angle = Math.toRadians((360/states)/2);
    double apothem = (STATE_RADIUS + SPACE_BETWEEN_STATES) / Math.tan(angle);
    int radius = (int)Math.round(apothem * (1/Math.cos(angle)));
    
    System.out.println(String.format("big radius: %d", radius));
    return radius;
  }

  private static int[][] getCenters(int states, int radius) {
    int[][] centers = new int[states][2];
    double angle = Math.toRadians(360/states);
    double currentAngle = 0;

    for (int i=0; i<states; i++) {
      System.out.println("Angle: " + currentAngle);
      double x = radius * Math.cos(currentAngle);
      double y = radius * Math.sin(currentAngle);

      centers[i][0] = (int) Math.round(x+radius);
      System.out.println("x"+i+": "+centers[i][0]);

      centers[i][1] = (int) Math.round(y+radius);
      System.out.println("y"+i+": "+centers[i][1]);


      currentAngle+=angle;
    }

    return centers;
  }

  /*Group test = new Group();

    //TEST STATE
    Circle q1 = new Circle(50,60,30);
    Circle q2 = new Circle(200,60,30);

    q2.setFill(Color.YELLOW);
    q2.setStroke(Color.BLACK);

    
    //TEST TRANSITION
    QuadCurve curve = new QuadCurve(80, 60, 125, 10, 170, 60);
    curve.setStroke(Color.BLUE);
    curve.setFill(null);

    //TEST ARROW
    //double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
    double angle = Math.atan2((60 - 10), (170 - 125)) - Math.PI / 2.0;
    double sin = Math.sin(angle);
    double cos = Math.cos(angle);

    //double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
    double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * 10 + 170;
    double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * 10 + 60;

    double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * 10 + 170;
    double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * 10 + 60;

    Line h1 = new Line(x1, y1,170,60);
    Line h2 = new Line(x2, y2,170,60);

    //TEST TEXT
    Text transition = new Text(125,30,"a");
    Text name = new Text(200,60,"q1");
    
    stackPane.setStyle("-fx-border-color: blue;");

    test.getChildren().addAll(curve,transition,h1,h2,q1,q2,name);
    stackPane.getChildren().add(test);*/

}