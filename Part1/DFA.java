import java.util.Hashtable;

public class DFA {
    private Hashtable<String, State> setStates;
    private String[] alphabet;
    private String initialState;
    private String[] setFinalStates;
    
    public DFA(String[] states, String[] alphabet, String initialState, String[] finalState) {
        setStates = new Hashtable<>();
        for (int i=0; i<states.length; i++) {
            setStates.put(states[i], new State(states[i]));
        }

        this.initialState = initialState;


        for (int i=0; i<finalState.length; i++) {
            setStates.get(finalState[i]).setFinal();
        }
    }

    public void addTransition(String startState, String letter, String endState) {
        setStates.get(startState).setTransition(setStates.get(endState), letter);
    }

    public boolean processString(String chain){
        return processStringRecursive(chain, initialState, 0);
    }

    public boolean processStringRecursive(String chain, String currentState, int character) {
        if(character == chain.length()){
            return setStates.get(currentState).isFinal();
        } else {
            State nextState = setStates.get(currentState).getTransition(chain.charAt(character) + "");
            if(nextState == null){
                return false;
            } else {
                return processStringRecursive(chain, nextState.getId(), character+1);
            }
        }
    }
}