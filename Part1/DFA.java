import java.util.HashTable;

public class DFA {
    private HashTable<String, State> setStates;
    private String[] alphabet;
    private String initialState;
    private String[] setFinalStates;
    
    public DFA(String[] states, String[] alphabet, String initialState, String[] finalState) {
        setStates = new HashTable<>();
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
}