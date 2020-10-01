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
        while(sc.hasNextLine()){
            String[] transitionRaw = sc.nextLine().clip();
            String[] 
        }
        DFA automata = new DFA();

        sc.close()
    }
}