import java.util.Hashtable;

public class DFA {
    private Hashtable<String, State> dfa;
    private String[] states;
    private String[] alphabet;
    private String initialState;
    
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

    public void addTransition(String startState, String character, String endState) {
        dfa.get(startState).setTransition(character, dfa.get(endState));
    }

    public boolean processString(String chain) {
        return processStringRecursive(chain, dfa.get(initialState), 0);
    }

    private boolean processStringRecursive(String chain, State currentState, int index) {
        State nextState;
        String character="";

        if(index == chain.length()) {
            nextState = currentState;
            //System.out.println("S("+currentState.getId()+","+character+") = "+nextState.getId());
            return currentState.isFinal();
        }else {
            character = chain.charAt(index)+"";
            nextState = currentState.getTransition(character);
            if(nextState == null) {
                //System.out.println("S("+currentState.getId()+","+character+") = sink");
                return false;
            }else {
                //System.out.println("S("+currentState.getId()+","+character+") = "+nextState.getId());
                return processStringRecursive(chain, nextState, index+1);
            } 
        }
    }

    public void minimizeDFA() {
        String[][] minimized = minimizeTransitionTable(newTransitionTable());

        for (int i=0; i<minimized.length; i++)
            for (int j=0; j<alphabet.length; j++)
                System.out.println(states[i] + " => " + minimized[i][j]);
    }

    private String[][] minimizeTransitionTable(String[][] transitionTable) {
        for (int i=0; i<transitionTable.length; i++)
            for (int j=0; j<transitionTable.length; j++) {
                boolean areEqual = false;

                for (int k=0; k<alphabet.length; k++) {
                    String r1 = transitionTable[i][k];
                    String r2 = transitionTable[j][k];  
                    boolean sameResult = r1.equals(r2);
                    boolean emptyRow = (sameResult == true & (r1+r2).equals("")) ? true : false;
                    boolean sameType = ((dfa.get(states[i]).isFinal()==true & dfa.get(states[j]).isFinal()==true)||(dfa.get(states[i]).isFinal()==false & dfa.get(states[j]).isFinal()==false)) ? true : false;

                    if (i!=j & sameResult & sameType & !emptyRow)
                        areEqual = true;
                    else{
                        areEqual = false;
                        break;
                    }    
                }

                if (areEqual) {
                    System.out.println("Row "+i+" is equal to row "+j+" | State "+states[j]+ " changed to "+states[i]);
                    for (int a=0; a<transitionTable.length; a++)
                        for (int b=0; b<alphabet.length; b++) {
                            if (a==j)
                                transitionTable[a][b] = "";
                            else
                                transitionTable[a][b] = (transitionTable[a][b].equals(states[j])) ? states[i] : transitionTable[a][b];
                        }
                }
            }
        
        //TODO Delete empty rows & rename remaining states in the minimized transition table

        return transitionTable;
    }

    private String[][] newTransitionTable() {
        String[][] transitionTable = new String[states.length][alphabet.length];

        for (int i=0; i<states.length; i++)
            for (int j=0; j<alphabet.length; j++){
                State result = dfa.get(states[i]).getTransition(alphabet[j]);
                transitionTable[i][j] = (result==null) ? "" : result.getId();
            }

        return transitionTable;
    }
}