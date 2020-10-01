import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("test1.txt"));
        String[] states = sc.nextLine().split(",");
        String[] characters = sc.nextLine().split(",");
        String initialState = sc.next(); sc.nextLine();
        String[] finalStates = sc.nextLine().split(",");

        DFA automata = new DFA(states, characters, initialState, finalStates);

        while(sc.hasNextLine()){
            String[] transition = sc.nextLine().split(",");
            String[] charYState = transition[1].split("=>");
            automata.addTransition(transition[0], charYState[0], charYState[1]);
        }

        sc.close();
    }
}