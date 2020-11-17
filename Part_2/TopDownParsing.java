import java.util.Hashtable;
import java.util.LinkedList;

public class TopDownParsing {
  private String[] nonTerminalSymbols;
  private String[] terminalSymbols;
  private String startSymbol;
  private Hashtable<String, LinkedList<String>> productions;
  private LinkedList<String> queue;
  
  public TopDownParsing(String[] nonTerminalSymbols, String[] terminalSymbols, String startSymbol, Hashtable<String, LinkedList<String>> productions) {
    this.nonTerminalSymbols = nonTerminalSymbols;
    this.terminalSymbols = nonTerminalSymbols;
    this.startSymbol = startSymbol;
    this.productions = productions;
    System.out.println("VAMOS BIEN");
  }

  public void parsingProcess(String chain, int maxDepth) {

  }

  private void algoritmo() {

  }
}
