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
        String[] finalStates = new String[states.length];
        int finalCounter = 0;
        String[][] minimizedTable = minimizeTransitionTable(newTransitionTable());

        for (int i=0; i<minimizedTable.length; i++) {
            if (dfa.get(minimizedTable[i][0]).isFinal()&minimizedTable[i][0].equals(states[i])) {
                finalStates[finalCounter] = states[i];
                finalCounter++;
            }
            
            if (minimizedTable[i][0].equals(states[i])==false) {
                String currentState = minimizedTable[i][0];

                if (dfa.get(currentState).isFinal()) {
                    finalStates[finalCounter] = states[i];
                    finalCounter++;
                }

                System.out.println("State "+currentState+ " renamed to "+states[i]+"\n");

                for (int a=0; a<minimizedTable.length; a++) {
                    for (int b=0; b<alphabet.length+1; b++) {
                        minimizedTable[a][b] = (minimizedTable[a][b].equals(currentState)) ? states[i] : minimizedTable[a][b];
                        System.out.print(minimizedTable[a][b]+"   ");
                    }
                    System.out.println("");
                }
                System.out.println("");
            }
        }

        //TODO Update the hashtable with the minimized transition table
        /*dfa = new Hashtable<>();

        for (int i=0; i<minimizedTable.length; i++) {
            states = new String[minimizedTable.length];
            dfa.put(minimizedTable[i][0], new State(minimizedTable[i][0]));
            states[i] = minimizedTable[i][0];

        }

        for (int i=0; i<minimizedTable.length; i++)
            for (int j=1; j<alphabet.length+1; j++)
                dfa.get(minimizedTable[i][0]).setTransition(alphabet[j-1], dfa.get(minimizedTable[i][j-1]));

        for (int i=0; i<finalStates.length; i++) {
            if (finalStates[i]!=null)
                dfa.get(finalStates[i]).setFinal();
            else
                break;
        }*/

        //printTransitionTable(minimizedTable);
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
                        //System.out.println(row);
                        for (int b=0; b<alphabet.length+1; b++) {
                            if (a==j)
                                transitionTable[a][b] = "";
                            else {
                                transitionTable[a][b] = (transitionTable[a][b].equals(states[j])) ? states[i] : transitionTable[a][b];
                                minimizedTable[row][b] = transitionTable[a][b];
                                System.out.print(minimizedTable[row][b]+"   ");
                            }
                        }
                        if (transitionTable[a][0].equals("")==false) {
                            row++;
                            System.out.println("");
                        }
                    }
                    System.out.println("");
                }
            }
        return minimizedTable;
    }

    public String[][] newTransitionTable() {
        String[][] transitionTable = new String[states.length][alphabet.length+1];

        for (int i=0; i<states.length; i++) {
            //transitionTable[i][0] = (states[i-1].equals(initialState)) ? "->"+states[i-1] : "  "+states[i-1];
            //transitionTable[i][0] = (dfa.get(states[i-1]).isFinal()) ? transitionTable[i][0]+"*" : transitionTable[i][0]+" ";
            transitionTable[i][0] = states[i];

            for (int j=1; j<alphabet.length+1; j++) {
                //transitionTable[0][j] = alphabet[j-1];

                State result = dfa.get(states[i]).getTransition(alphabet[j-1]);
                transitionTable[i][j] = (result==null) ? "" : result.getId();
            }
        }
        return transitionTable;
    }

    /*private void printTransitionTable(String[][] transitionTable) {
        for (int i=0; i<transitionTable.length; i++){
            for (int j=0; j<alphabet.length+1; j++)
                System.out.print(transitionTable[i][j]+"   ");
            System.out.println("");
        }
    }*/
}