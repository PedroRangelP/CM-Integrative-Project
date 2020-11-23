import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;

public class InputReader {
  private Scanner sc;
  private Scanner consola;

  public InputReader() throws FileNotFoundException {
    consola = new Scanner(System.in);
    String[] availableFiles = {"test1.txt", "test2.txt", "test3.txt", "test4.txt"};
    for(int i = 0; i < availableFiles.length; i++)
      System.out.println(i+1 + " " + availableFiles[i]);
    System.out.print("Choose a file: ");
    int indexFile = Integer.parseInt(consola.nextLine()) - 1;
    sc = new Scanner(new File("test/" + availableFiles[indexFile]));
  }

  public String[] getNonTerminalSymbols(){
    return sc.nextLine().split(",");
  }

  public String[] getTerminalSymbols(){
    return sc.nextLine().split(",");
  }

  public String getStartSymbol(){
    return sc.nextLine();
  }

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

  public String getString(){
    System.out.print("String: ");
    return consola.nextLine();
  }

  public int getMaxDepth(){
    System.out.print("Maximum depth: ");
    return Integer.parseInt(consola.nextLine());
  }

}
