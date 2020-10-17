package dfa;

import java.util.ArrayList;
import java.util.Hashtable;

public class DFA {
  private Hashtable<String, State> dfa;
  private String[] states;
  private String[] alphabet;
  private String initialState;
  private ArrayList<String> processSteps;
  
  
  /**
   * Creates a new DFA with the giving parameters.
   * @param states Set of states of the automata.
   * @param alphabet Set of alphabet symbols.
   * @param initialState Initial state of the automata.
   * @param finalStates Set of final states of the automata.
   */
  public DFA(String[] states, String[] alphabet, String initialState, String[] finalStates) {
    dfa = new Hashtable<>();
    
    this.states = states;
    for (int i=0; i<states.length; i++)
      dfa.put(states[i], new State(states[i]));

    this.initialState = initialState;

    this.alphabet = alphabet;

    for (int i=0; i<finalStates.length; i++)
      dfa.get(finalStates[i]).setFinal();
  }

  /**
   * Returns a Hashtable with all the states in this DFA
   * @return A Hashtable that maps states to State objects
   */
  public Hashtable<String, State> getDfa(){
    return this.dfa;
  }

  
  /**
   * Adds a transition from the given state to the other state processing the character.
   * @param startState The state where is processed the character.
   * @param character The alphabet symbol to process.
   * @param endState The result of the evaluation.
   */
  public void addTransition(String startState, String character, String endState) {
    dfa.get(startState).setTransition(character, dfa.get(endState));
  }

  
  /** 
   * Processes the given string and determines if it is accepted.
   * @param chain The string to process.
   * @return boolean The result if the given chain is accepted by the automata.
   */
  public boolean evaluateString(String chain) {
    processSteps = new ArrayList<>();
    return processStringRecursive(chain, dfa.get(initialState), 0);
  }
  
  private boolean processStringRecursive(String chain, State currentState, int index) {
    State nextState;
    String character="";

    if(index == chain.length()) {
      nextState = currentState;
      processSteps.add("S("+currentState.getId()+", "+character+") = "+nextState.getId());
      return currentState.isFinal();
    }else {
      character = chain.charAt(index)+"";
      nextState = currentState.getTransition(character);
      if(nextState == null) {
        processSteps.add("S("+currentState.getId()+", "+character+") = sink");
        return false;
      }else {
        processSteps.add("S("+currentState.getId()+", "+character+") = "+nextState.getId());
        return processStringRecursive(chain, nextState, index+1);
      } 
    }
  }


  /**
   * Returs an array list with all the steps of evaluating a string.
   * @return An ArrayList with all transitions needed to process the string
   */
  public ArrayList<String> getProcessSteps() {
    return processSteps;
  }


  /**
   * Returns an array of strings with all the states of this DFA.
   * @return Array with all the String representations of the names of the states
   */
  public String[] getStates() {
    return this.states;
  }


  /**
   * Returns an array of strings with the characters of the alphabet.
   * @return An array of Strings with the characters of the alphabet
   */
  public String[] getAlphabet() {
    return alphabet;
  }


  /**
   * Returns the initial state of the automata.
   * @return The String representation of the initial state of this DFA
   */
  public String getInitialState() {
    return initialState;
  }


  /**
   * Minimizes the DFA and updates the transitions in the automata
   */
  public void minimizeDFA() {
    System.out.println("*-*-*-*-*-*-*-*-*-* Minimizing DFA *-*-*-*-*-*-*-*-*-*");
    String[][] minimizedTable = minimizeTransitionTable(newTransitionTable());
    String[] finalStates = new String[minimizedTable.length];
    int totalFinal = 0;

    for (int i=0; i<minimizedTable.length; i++) {
      String currentState = minimizedTable[i][0];

      if (dfa.get(currentState).isFinal()) {
        finalStates[totalFinal] = states[i];
        totalFinal++;
      }
      
      if (minimizedTable[i][0].equals(states[i])==false) {
        System.out.println("State "+currentState+ " renamed to "+states[i]+"\n");

        for (int a=0; a<minimizedTable.length; a++)
          for (int b=0; b<alphabet.length+1; b++)
            minimizedTable[a][b] = (minimizedTable[a][b].equals(currentState)) ? states[i] : minimizedTable[a][b];
      }
    }
    System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
    
    dfa.clear();
    states = new String[minimizedTable.length];

    for (int i=0; i<minimizedTable.length; i++) {
      states[i] = minimizedTable[i][0];
      dfa.put(minimizedTable[i][0], new State(minimizedTable[i][0]));
    }

    for (int i=0; i<minimizedTable.length; i++) {
      for (int j=1; j<alphabet.length+1; j++)
        if (!minimizedTable[i][j].equals(""))
          addTransition(minimizedTable[i][0], alphabet[j-1], minimizedTable[i][j]);

      if (finalStates[i]!=null)
        dfa.get(finalStates[i]).setFinal();
    }
  }

  private String[][] minimizeTransitionTable(String[][] transitionTable) {
    int totalStates = states.length;
    String[][] minimizedTable = null;

    for (int i=0; i<transitionTable.length; i++)
      for (int j=0; j<transitionTable.length; j++) {
        boolean areEqual = false;

        for (int k=1; k<alphabet.length+1; k++) {
          String r1 = transitionTable[i][k];
          String r2 = transitionTable[j][k];  
          boolean sameResult = r1.equals(r2);
          boolean emptyRow = (sameResult == true & (r1+r2).equals("")) ? true : false;
          boolean sameType = ((dfa.get(states[i]).isFinal()==true & dfa.get(states[j]).isFinal()==true)||(dfa.get(states[i]).isFinal()==false & dfa.get(states[j]).isFinal()==false)) ? true : false;

          if (i!=j & sameResult & sameType & !emptyRow) {
            areEqual = true;
          }else {
            areEqual = false;
            break;
          }    
        }

        if (areEqual) {
          totalStates--;
          minimizedTable = new String[totalStates][alphabet.length+1];
          int row = 0;

          System.out.println("Row "+(i+1)+" is equal to row "+(j+1)+" | State "+states[j]+ " renamed to "+states[i]+"\n");
      
          for (int a=0; a<transitionTable.length; a++) {
            for (int b=0; b<alphabet.length+1; b++) {
              if (a==j)
                transitionTable[a][b] = "";
              else {
                transitionTable[a][b] = (transitionTable[a][b].equals(states[j])) ? states[i] : transitionTable[a][b];
                minimizedTable[row][b] = transitionTable[a][b];
              }
            }
            if (transitionTable[a][0].equals("")==false)
              row++;
          }
        }
      }
    return minimizedTable;
  }

  private String[][] newTransitionTable() {
    String[][] transitionTable = new String[states.length][alphabet.length+1];

    for (int i=0; i<states.length; i++) {
      transitionTable[i][0] = states[i];

      for (int j=1; j<alphabet.length+1; j++) {
        State result = dfa.get(states[i]).getTransition(alphabet[j-1]);
        transitionTable[i][j] = (result==null) ? "" : result.getId();
      }
    }
    return transitionTable;
  }

  
  /**
   * Returns the transition table of the automata.
   * @return String[][]
   */
  public String[][] getTransitionTable() {
    String[][] content = newTransitionTable();
    String[][] transitionTable = new String[states.length+1][alphabet.length+1];
    transitionTable[0][0] = " ";

    for (int i=0; i<transitionTable.length; i++) {
      if (i>0) {
        transitionTable[i][0] = (content[i-1][0].equals(initialState)) ? "->"+content[i-1][0] : content[i-1][0];
        transitionTable[i][0] = (dfa.get(content[i-1][0]).isFinal()) ? transitionTable[i][0]+"*" : transitionTable[i][0];
      }
      
      for (int j=0; j<alphabet.length+1; j++) {
        if (j>0) {
          transitionTable[0][j] = alphabet[j-1];
          transitionTable[i][j] = (i>0) ? content[i-1][j] : transitionTable[i][j];
        }
      }
    }

    return transitionTable;
  }
}
