import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import javafx.scene.Group;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import dfa.DFA;

/**
 * @author Pedro Rangel
 * @author Diego Montaño
 */

public class App extends Application {
  private DFA automata;
  private GridPane transitionTable = new GridPane();
  private Label lblResult = new Label();
  private GridPane processSteps = new GridPane();

  public static void main(String[] args) {
    int[][] test = getCenters(4, getBigRadious(4));
    launch(args);
  }

  public void start(Stage mainStage) {
    GridPane dfaControlPane = new GridPane();
    GridPane dfaPane = new GridPane();
    GridPane processPane = new GridPane();

    Label lblDFA = new Label("DFA");
    lblDFA.getStyleClass().add("h1");
    Button bttnMinimize = new Button("Minimize DFA");
    bttnMinimize.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      public void handle(MouseEvent e) {
        minimizeDFA();
        lblDFA.setText("Minimized DFA");
        bttnMinimize.setDisable(true);
      }
    });

    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt", "*.txt"));

    Label lblFile = new Label("File:");
    lblFile.getStyleClass().add("h3");
    Label lblFileName = new Label("not selected");
    Button bttnChoose = new Button("Choose file");
    bttnChoose.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      public void handle(MouseEvent e) {
        File file = fileChooser.showOpenDialog(mainStage);
        if (file!=null) {
          lblFileName.setText(file.getName());
          buildDFA(file);
          lblDFA.setText("DFA");
          dfaControlPane.setVisible(true);
          bttnMinimize.setDisable(false);
          dfaPane.setVisible(true);
          processPane.setVisible(true);
        }
      }
    });

    Label lblTable = new Label("Transition Table");
    lblTable.getStyleClass().add("h3");

    Label lblEvaluate = new Label("Evaluate String");
    lblEvaluate.getStyleClass().add("h2");
    Label lblChain = new Label("Chain:");
    lblChain.getStyleClass().add("h3");
    TextField txtChain = new TextField();
    Button bttnEvaluate = new Button("Process");
    bttnEvaluate.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      public void handle(MouseEvent e) {
        evaluateString(txtChain.getText());
      }
    });


    StackPane stackPane = new StackPane();
    Group test = new Group();

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
    stackPane.getChildren().add(test);
    
    GridPane controlsPane = new GridPane();
    controlsPane.add(lblFile, 0, 0);
    controlsPane.add(lblFileName, 1, 0);
    controlsPane.add(bttnChoose, 2, 0);
    controlsPane.add(bttnMinimize, 0, 1);
    controlsPane.setHgap(10);

    dfaControlPane.add(lblDFA, 0, 0);
    dfaControlPane.add(bttnMinimize, 0, 1);
    dfaControlPane.setVgap(10);
    dfaControlPane.setVisible(false);
    
    dfaPane.add(lblTable, 0, 0);
    dfaPane.add(transitionTable, 0, 1);
    dfaPane.setVgap(10);
    dfaPane.setVisible(false);
  
    processPane.add(lblEvaluate, 0, 0);
    processPane.add(lblChain, 0, 1);
    processPane.add(txtChain, 1, 1);
    processPane.add(bttnEvaluate, 2, 1);
    processPane.add(lblResult, 0, 2);
    processPane.setHgap(10);
    processPane.setVgap(15);
    processPane.setVisible(false);

    VBox base = new VBox();
    base.setSpacing(20);
    base.setPadding(new Insets(20, 20, 20, 20));
    base.getChildren().addAll(stackPane, controlsPane, dfaControlPane, dfaPane, processPane, processSteps);

    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(base);

    Scene scene = new Scene(scrollPane);
    scene.getStylesheets().add("resources/stylesheet.css");
    mainStage.setTitle("DFA by Diegod and Pedrito");
    mainStage.setScene(scene);
    mainStage.setMaximized(true);
    mainStage.show();
  }

  private void buildDFA(File file) {
    try {
      Scanner sc = new Scanner(file);
      String[] states = sc.nextLine().split(",");
      String[] alphabet = sc.nextLine().split(",");
      String initialState = sc.next(); sc.nextLine();
      String[] finalStates = sc.nextLine().split(",");

      automata = new DFA(states, alphabet, initialState, finalStates);

      while(sc.hasNextLine()) {
        String[] transition = sc.nextLine().split(",");
        String[] charYState = transition[1].split("=>");
        automata.addTransition(transition[0], charYState[0], charYState[1]);
      }
      sc.close();

      showTrasitionTable();

    }catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    }
  }

  private void minimizeDFA() {
    automata.minimizeDFA();
    showTrasitionTable();
  }

  private void showTrasitionTable() {
    transitionTable.getChildren().removeAll(transitionTable.getChildren());

    String[][] table = automata.getTransitionTable();

    for (int i=0; i<table.length; i++){
      for (int j=0; j<table[0].length; j++) {
        Label data = new Label(table[i][j]);
        if (i==0 || j==0) {
          data.setStyle("-fx-font-weight: bold");
        }
          
        data.setPadding(new Insets(5, 5, 5, 5));
        transitionTable.add(data, j, i);
      }
    }
    transitionTable.getStyleClass().add("transitionTable");
    transitionTable.setAlignment(Pos.CENTER);
  }

  private void evaluateString(String chain) {
    boolean result = automata.evaluateString(chain);
    ArrayList<String> steps = automata.getProcessSteps();

    if(result==true)
      lblResult.setText("The string is accepted");
    else
      lblResult.setText("The string is not accepted");

    processSteps.getChildren().removeAll(processSteps.getChildren());
    int rowIndex = 0;
    for (String string : steps) {
      Label step = new Label(string);
      processSteps.add(step, 0, rowIndex);
      rowIndex++;
    }
  }

  private static int getBigRadious(int numStates){
    final int STATE_RADIUS = 30;
    final int SPACE_BETWEEN_STATES = 20;
    int radious = (STATE_RADIUS + SPACE_BETWEEN_STATES) / ((int)Math.ceil(Math.tan(Math.toRadians(180/numStates))));
    return radious;
  }

  private static int[][] getCenters(int numStates, int radious) {
    int[][] centers = new int[numStates][2];
    double angle = Math.toRadians(360/numStates);
    double currentAngle = 0;

    for (int i=0; i<numStates; i++) {
      System.out.println("Angle: " + currentAngle);
      double x = radious * Math.cos(currentAngle);
      double y = radious * Math.sin(currentAngle);
      //System.out.println("x"+i+": "+x);
      //System.out.println("y"+i+": "+y);

      centers[i][0] = (int) Math.round(x+radious);
      System.out.println("x"+i+": "+centers[i][0]);

      centers[i][1] = (int) Math.round(y+radious);
      System.out.println("y"+i+": "+centers[i][1]);


      currentAngle+=angle;
    }

    return centers;
  }

}
