import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * @author Pedro Rangel
 * @author Diego Montaño
 */

public class Main {
  public static void main(String[] args) throws FileNotFoundException {
    InputReader inputReader = new InputReader();
    String[] nonTerminalSymbols = inputReader.getNonTerminalSymbols();
    String[] terminalSymbols = inputReader.getTerminalSymbols();
    String startSymbol = inputReader.getStartSymbol();
    Hashtable<String, LinkedList<String>> productions = inputReader.getProductions();
    
    TopDownParsing topDownParsing = new TopDownParsing(nonTerminalSymbols, terminalSymbols, startSymbol, productions);

    String testInput = inputReader.getString();
    int maxDepth = inputReader.getMaxDepth();

    topDownParsing.parsingProcess(testInput, maxDepth);
  }
}