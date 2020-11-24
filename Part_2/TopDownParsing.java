import java.util.Hashtable;
import java.util.LinkedList;

/**
 * This class contains all the algorithms needed to process a given string, validate if is accepted in the language and create a string representation of the resulting parsing tree that can be seen using the <a href="http://ironcreek.net/syntaxtree/">jsSyntax Tree</a> tool.
 */
public class TopDownParsing {
  private String[] nonTerminalSymbols;
  private String[] terminalSymbols;
  private String startSymbol;
  private Hashtable<String, LinkedList<String>> productions;
  
  /**
   * Creates an instance of Top Down Parsing with all the necessary information to create a given language
   * @param nonTerminalSymbols an array of Non-terminal Symbols, each symbol must be one character long and be in uppercase
   * @param terminalSymbols an array of Terminal Symbols, each symbol must be one character long and be in lowercase
   * @param startSymbol a String representation of the Start Symbol
   * @param productions a Hashtable representing the productions present in the language
   */
  public TopDownParsing(String[] nonTerminalSymbols, String[] terminalSymbols, String startSymbol, Hashtable<String, LinkedList<String>> productions) {
    this.nonTerminalSymbols = nonTerminalSymbols;
    this.terminalSymbols = nonTerminalSymbols;
    this.startSymbol = startSymbol;
    this.productions = productions;
  }

  /**
   * This method is the implementation of the Top Down Parsing algorithm. It receives a string and will use the productions of the language to try to build that string beginning by the Start Symbol of the language. If the string is accepted this method will print to the console "Accepted". If the string is not accepted or the resulting tree reaches the limit of depth then it will print "Rejected" to the console.
   * In any case it will also print a string representation of the resulting parsing tree formatted to create the graphic tree using the JS Syntax Tree tool
   * @param p The string to be analized, it must contain only lowercase characters
   * @param maxDepth The max depth that the resulting parsign tree can have
   */
  public void parsingProcess(String p, int maxDepth) {
    LinkedList<Leaf> Q = new LinkedList<>();
    LinkedList<Leaf> T = new LinkedList<>();
    String u = "", w = "", v = "";
    //Step 1
    Q.add(new Leaf(startSymbol, 1));
    //Step 2
    while(!(Q.isEmpty() || p.equals(u+w+v))){
      //Step 2.1
      Leaf actual = Q.poll();
      T.add(actual);
      String q = actual.getChain();
      //Step 2.2
      int i = 0;
      /*Step 2.3
      boolean done = false;*/
      //Step 2.4
      String[] qParts = qTouAv(q);
      u = qParts[0];
      String A = qParts[1];
      v = qParts[2];
      if(A.equals(""))
        continue;
        
      int numProductionsForA = productions.get(A).size();
      while(!p.equals(u+w+v)){
        //Step 2.4.1
        if (i == numProductionsForA){
          break;
        }
        //Step 2.4.2
        else {
          w = productions.get(A).get(i);
          String newString = u+w+v;
          //Step 2.4.2.1
          if (prefixMatch(p, newString) && actual.getDepth() < maxDepth) {
            Leaf newLeaf = new Leaf(newString, actual.getDepth()+1);
            actual.addChild(newLeaf);
            //Step 2.4.2.1.1
            Q.add(newLeaf);
            //Step 2.4.2.1.2
            T.add(newLeaf);
          }
        }
        //Step 2.4.3
        i++;
      }
    }
    printTree(T.get(0));

    //Step 3
    if (p.equals(u+w+v)) {
      System.out.println("\n---- The string is accepted ----");
    } else {
      System.out.println("\n---- The string is rejected ----");
    }
  }

  private boolean prefixMatch(String word, String newString) {
    int i = 0;
    int n = word.length();
    int m = newString.length();
    while(i < n && i < m){
      if (isUppercase(newString.charAt(i))) {
        return true;
      } else if(newString.charAt(i) != word.charAt(i)){
        return false;
      }
      i++;
    }
    return true;
  }

  private boolean isUppercase(char letter){
    return letter > 64 && letter < 91;
  }

  private String[] qTouAv(String q){
    String[] array = {"", "", ""};
    int qLength = q.length();
    int indexOfLeftmostNonTerminal = qLength;
    for (int i = 0; i < qLength; i++) {
      char actualSymbol = q.charAt(i);
      if(isUppercase(actualSymbol)){
        indexOfLeftmostNonTerminal = i;
        break;
      }
    }
    array[0] = q.substring(0, indexOfLeftmostNonTerminal);
    if (indexOfLeftmostNonTerminal < qLength)
      array[1] = q.substring(indexOfLeftmostNonTerminal, indexOfLeftmostNonTerminal+1);
    if (indexOfLeftmostNonTerminal+1 < qLength)
      array[2] = q.substring(indexOfLeftmostNonTerminal+1);
    return array;
  }

  private void printTree(Leaf root) {
    System.out.println("\n---- Tree -----");
    System.out.println(printTreeRecursive(root));
  }

  private String printTreeRecursive(Leaf parent) {
    String toString = "["+parent.getChain()+" ";

    if (!parent.getChildren().isEmpty()) {
      for (Leaf child : parent.getChildren()) {
        toString+= printTreeRecursive(child);      
      }
    }
    toString+="]";
    return toString;
  }
}
