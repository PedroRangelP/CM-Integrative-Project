import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Diego Montaño
 * @author Pedro Rangel
 */

public class Main implements Application {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("test3.txt"));
        String[] states = sc.nextLine().split(",");
        String[] characters = sc.nextLine().split(",");
        String initialState = sc.next(); sc.nextLine();
        String[] finalStates = sc.nextLine().split(",");

        DFA automata = new DFA(states, characters, initialState, finalStates);

        while(sc.hasNextLine()) {
            String[] transition = sc.nextLine().split(",");
            String[] charYState = transition[1].split("=>");
            automata.addTransition(transition[0], charYState[0], charYState[1]);
        }
        sc.close();
        
        automata.getTransitionTable();
        /*System.out.println(automata.processString(""));
        System.out.println(automata.processString("aba"));
        System.out.println(automata.processString("baaaabb"));*/

        automata.minimizeDFA();
        automata.getTransitionTable();
        /*System.out.println(automata.processString(""));
        System.out.println(automata.processString("aba"));
        System.out.println(automata.processString("baaaabb"));*/
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("DFA by Diegod and Pedrito");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}