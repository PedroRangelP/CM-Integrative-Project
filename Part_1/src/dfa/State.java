package dfa;

import java.util.Hashtable;

public class State {
  private String id;
  private boolean isFinal;
  private Hashtable<String, State> transitions;

  /**
   * Creates a new state with an id and no transitions.
   * @param id The name of the state
   */
  public State(String id) {
    this.id = id;
    this.isFinal = false;
    this.transitions = new Hashtable<>();
  }

    
  /**
   * Gets the current id of this state.
   * @return String
   */
  public String getId() {
    return id;
  }


  /**
   * Sets this state as a final state.
   */
  public void setFinal() {
    this.isFinal = true;
  }


  /**
   * Returns true if this state is a final state.
   * @return boolean
   */
  public boolean isFinal() {
    return isFinal;
  }


  /** Adds a transition from this state to another state.
   * @param character The character of the alphabet that makes the transition.
   * @param state The state that this transition leads to.
   */
  public void setTransition(String character, State state) {
    transitions.put(character, state);
  }

    
  /** 
   * Returns the resulting state after processing the character.
   * @param character The character that is going to be processed.
   * @return State The resulting state.
   */
  public State getTransition(String character) {
    return transitions.get(character);
  }
}
