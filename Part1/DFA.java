public class DFA {
    private State[] setStates;
    private String[] alphabet;
    private String initialState;
    private String[] finalState;
    
    public DFA(String[] states, String[] alphabet, String initialState, String[] finalState) {
        setStates = new State[states.lenght];
        for (int i=0; i<states.lenght; i++) {
            setStates[i] = 
        }

        this.alphabet = alphabet;
        this.initialState = initialState;
        this.finalState = finalState;
    }

    public void addTransition(String startState, String letter, String endState) {

    }
}