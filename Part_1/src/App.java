import java.util.Scanner;
import java.io.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import dfa.*;

/**
 * @author Pedro Rangel
 * @author Diego Montaño
 */

public class App extends Application {
  DFA automata;
  Label lblResult;

  public void start(Stage mainStage) {
    mainStage.setTitle("DFA by Diegod and Pedrito");

    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt", "*.txt"));

    Label lblFile = new Label("File: not selected");
    Button bttnChoose = new Button("Choose file");

    bttnChoose.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      public void handle(MouseEvent e) {
        File file = fileChooser.showOpenDialog(mainStage);
        if (file!=null) {
          lblFile.setText("File: "+file.getName());
          buildDFA(file);
        }
      }
    });

    TextField txtChain = new TextField();
    Button bttnEvaluate = new Button("Evaluate String");
    lblResult = new Label();

    bttnEvaluate.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      public void handle(MouseEvent e) {
        evaluateString(txtChain.getText());
      }
    });

    GridPane grid = new GridPane();
    grid.add(lblFile, 0, 0);
    grid.add(bttnChoose, 1, 0);
    grid.add(txtChain, 0, 1);
    grid.add(bttnEvaluate, 1, 1);
    grid.add(lblResult, 2, 1);
    Scene scene = new Scene(grid);
    mainStage.setScene(scene);
    mainStage.show();
  }

  public void buildDFA(File file) {
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

    }catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    }
  }

  public void evaluateString(String chain) {
    boolean result = automata.evaluateString(chain);

    if(result==true)
      lblResult.setText("The string is accepted");
    else
      lblResult.setText("The string is not accepted");
  }

  public static void main(String[] args) {
        /*automata.getTransitionTable();
        System.out.println(automata.processString(""));
        System.out.println(automata.processString("aba"));
        System.out.println(automata.processString("baaaabb"));

        automata.minimizeDFA();
        automata.getTransitionTable();
        System.out.println(automata.processString(""));
        System.out.println(automata.processString("aba"));
        System.out.println(automata.processString("baaaabb"));*/
    launch();
  }
}
