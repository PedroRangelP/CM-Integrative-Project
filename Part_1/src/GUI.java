import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class GUI extends Application {

  public String[] states = {"q0", "q1", "q2", "q3"};
  public static void main(String[] args) {
    launch();
  }

  public void start(Stage mainStage){
    StackPane stackPane = new StackPane();
    Scene scene = new Scene(stackPane);
    drawStateCircles(stackPane, states.length);
    mainStage.setScene(scene);
    mainStage.setMaximized(true);
    mainStage.show();
  }

  private void drawStateCircles(StackPane pane, int numStates){
    Group group = new Group();
    int bigRadious = getBigRadious(numStates);
    int[][] centers = getCenters(numStates, bigRadious);
    int i = 0;
    for (int[] point : centers) {
      int posX = point[0];
      int posY = point[1];
      Circle stateCircle = new Circle(posX, posY, 30, Color.YELLOW);
      Text idState = new Text(posX, posY, states[i]);
      group.getChildren().addAll(stateCircle, idState);
      ++i;
    }
    pane.getChildren().add(group);
    //System.out.println(pane.getChildren().size());
  }

  private static int getBigRadious(int numStates){
    System.out.println(numStates);
    final int STATE_RADIUS = 30;
    final int SPACE_BETWEEN_STATES = 30;

    double angle = Math.toRadians((360/numStates)/2);
    double apothem = (STATE_RADIUS + SPACE_BETWEEN_STATES) / Math.tan(angle);
    int radius = (int)Math.round(apothem * (1/Math.cos(angle)));
    
    /*double cosine = Math.cos(Math.toRadians(360/numStates));
    System.out.println(String.format("coseno: %f", cosine));
    int radius = (int)Math.round((STATE_RADIUS + SPACE_BETWEEN_STATES) / cosine);*/
    System.out.println(String.format("big radius: %d", radius));
    return radius;
  }

  private static int[][] getCenters(int numStates, int radius) {
    int[][] centers = new int[numStates][2];
    double angle = Math.toRadians(360/numStates);
    double currentAngle = 0;

    for (int i=0; i<numStates; i++) {
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

}