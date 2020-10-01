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

        //DFA automata = new DFA(states, characters, initialState, finalStates);

        while(sc.hasNextLine()){
            String[] transition = sc.nextLine().split(",");
            String[] charYState = transition[1].split("=>");
            addTransition(transition[0], charYState[0], charYState[1]);
        }

        sc.close();
    }

    public static void addTransition(String startState, String letter, String endState) {
        System.out.println(String.format("%s   %s   %s", startState, letter, endState));
    }
}