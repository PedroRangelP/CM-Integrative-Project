import java.util.Hashtable;
import java.util.LinkedList;

public class TopDownParsing {
  private String[] nonTerminalSymbols;
  private String[] terminalSymbols;
  private String startSymbol;
  private Hashtable<String, LinkedList<String>> productions;
  
  public TopDownParsing(String[] nonTerminalSymbols, String[] terminalSymbols, String startSymbol, Hashtable<String, LinkedList<String>> productions) {
    this.nonTerminalSymbols = nonTerminalSymbols;
    this.terminalSymbols = nonTerminalSymbols;
    this.startSymbol = startSymbol;
    this.productions = productions;
  }

  public void parsingProcess(String p, int maxDepth) {
    LinkedList<Leaf> Q = new LinkedList<>();
    LinkedList<Leaf> T = new LinkedList<>();
    String u = "", w = "", v = "";
    //Step 1
    T.add(new Leaf(startSymbol, 1));
    Q.add(new Leaf(startSymbol, 1));
    //Step 2
    while(!(Q.isEmpty() || p.equals(u+w+v))){
      //Step 2.1
      Leaf actual = Q.poll();
      String q = actual.getChain();
      //Step 2.2
      int i = 0;
      //Step 2.3
      boolean done = false;
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
          if (/*!p.equals(newString) && */prefixMatch(p, newString) && actual.getDepth() < maxDepth) {
            //Step 2.4.2.1.1
            Q.add(new Leaf(newString, actual.getDepth()+1));
            //Step 2.4.2.1.2
            T.add(new Leaf(newString, actual.getDepth()+1));
          }
        }
        //Step 2.4.3
        i++;
      }
    }
    //Step 3
    printTree(T);
    if (p.equals(u+w+v)) {
      System.out.println("Accepted");
    } else {
      System.out.println("Rejected");
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

  private void printTree(LinkedList<Leaf> T) {
    int current = 0;

    for (Leaf leaf : T) {
      if (leaf.getDepth() > current) {
        current++;
        System.out.println("-----Level "+ current+"-----");
      }
      System.out.println(leaf.getChain());
    }
  }

}
