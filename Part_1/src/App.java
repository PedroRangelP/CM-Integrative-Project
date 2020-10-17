import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import dfa.DFA;
import dfa.DrawDFA;

/**
 * @author Pedro Rangel
 * @author Diego Montaño
 */

public class App extends Application {
  private DFA automata;
  private GridPane transitionTable = new GridPane();
  private Label lblResult = new Label();
  private GridPane processSteps = new GridPane();
  private StackPane canvasDFA = new StackPane();

  public static void main(String[] args) {
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
    bttnMinimize.getStyleClass().add("button");

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
    bttnChoose.getStyleClass().add("button");

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
    bttnEvaluate.getStyleClass().add("button");
    
    GridPane controlsPane = new GridPane();
    controlsPane.add(lblFile, 0, 0);
    controlsPane.add(lblFileName, 1, 0);
    controlsPane.add(bttnChoose, 2, 0);
    controlsPane.add(bttnMinimize, 0, 1);
    controlsPane.setHgap(10);

    dfaControlPane.add(lblDFA, 0, 0);
    dfaControlPane.add(bttnMinimize, 0, 1);
    dfaControlPane.setVgap(10);
    dfaControlPane.setHgap(30);
    dfaControlPane.setVisible(false);
    
    dfaPane.add(lblTable, 0, 0);
    dfaPane.add(transitionTable, 0, 1);
    dfaPane.add(canvasDFA, 1, 0, 1, 2);
    dfaPane.setVgap(10);
    dfaPane.setVisible(false);
  
    processPane.add(lblEvaluate, 0, 0, 3, 1);
    processPane.add(lblChain, 0, 1);
    processPane.add(txtChain, 1, 1);
    processPane.add(bttnEvaluate, 2, 1);
    processPane.add(lblResult, 0, 2, 3, 1);
    processPane.setHgap(10);
    processPane.setVgap(15);
    processPane.setVisible(false);

    VBox base = new VBox();
    base.setSpacing(20);
    base.setPadding(new Insets(20, 20, 20, 20));
    base.getChildren().addAll(controlsPane, dfaControlPane, dfaPane, processPane, processSteps);

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
      
      drawDFA();
      showTrasitionTable();

    }catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    }
  }

  private void drawDFA() {
    DrawDFA drawDFA = new DrawDFA(automata);
    drawDFA.getDFACanvas(canvasDFA);
  }

  private void minimizeDFA() {
    automata.minimizeDFA();
    drawDFA();
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
    transitionTable.setAlignment(Pos.TOP_CENTER);
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
}
