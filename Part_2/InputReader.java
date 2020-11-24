import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;

/**
  * This class is used to read input from the console and from the different files given as examples to store the rules of the language
  * This class creates two instances of the Scanner, one of them will read the users input from console and the other reads the contents of the example files.
  */
public class InputReader {
  private Scanner sc;
  private Scanner consola;

  /**
   * When an instance of Input Reader is created first it waits for the user's input to indicate from which example file should the parser read to create the language.
   * @throws FileNotFoundException if for some reason one of the four examples files are missing from the test directory
   */
  public InputReader() throws FileNotFoundException {
    System.out.println("\nTO TEST ANOTHER FILE JUST ADD IT TO THE 'test' FOLDER AND RUN AGAIN THE PROGRAM\n");
    consola = new Scanner(System.in);
    File files = new File("test/");
    String[] availableFiles = files.list();
    for(int i = 0; i < availableFiles.length; i++)
      System.out.println("(" + (i+1) + ") " + availableFiles[i]);
    System.out.print("\nChoose a file: ");
    int indexFile = Integer.parseInt(consola.nextLine()) - 1;
    sc = new Scanner(new File("test/" + availableFiles[indexFile]));
  }

  /**
   * This method reads the line of the input file that contains the Non terminal Symbols and returns an array of those symbols
   * @return an array of the non terminal symbols as Strings
   */
  public String[] getNonTerminalSymbols(){
    return sc.nextLine().split(",");
  }

  /**
   * This method reads the line of the input file that contains the list of Terminal Symbols
   * @return an array of the terminal symbols as Strings
   */
  public String[] getTerminalSymbols(){
    return sc.nextLine().split(",");
  }

  /**
   * This method reads the line of the input file that contains the Start Symbol of the Tree
   * @return a String representation of the Start Symbol
   */
  public String getStartSymbol(){
    return sc.nextLine();
  }

  /**
   * This method reads all the remaining lines of the example which contain the productions of the language and represents them as a Hashtable of productions
   * @return a representacion of the productions of the language where the key String is a Non-terminal symbol which maps to a LinkedList containing all the productions of that symbol
   */
  public Hashtable<String, LinkedList<String>> getProductions(){
    Hashtable<String, LinkedList<String>> productions = new Hashtable<>();
    while(sc.hasNext()){
      String[] input = sc.nextLine().split("->");
      if(!productions.containsKey(input[0])){
        productions.put(input[0], new LinkedList<String>());
      }
      productions.get(input[0]).push(input[1]);
    }
    sc.close();
    return productions;
  }

  /**
   * This method reads from the console a String that should be validated by the parser
   * @return the string read from the console
   */
  public String getString(){
    System.out.print("String: ");
    return consola.nextLine();
  }

  /**
   * This method asks for the maximum depth that the created tree must have. Then reads from the console for the user input
   * @return an integer representing the maximum depth of the parsing tree
   */
  public int getMaxDepth(){
    System.out.print("Maximum depth: ");
    return Integer.parseInt(consola.nextLine());
  }

}
