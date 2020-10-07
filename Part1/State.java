import java.util.Hashtable;

public class State {
    public String id;
    private boolean isFinal;
    private Hashtable<String, State> transitions;

    public State(String id) {
      this.id = id;
      this.isFinal = false;
      this.transitions = new Hashtable<>();
    }

    public String getId() {
      return id;
    }

    public void setFinal() {
      this.isFinal = true;
    }

    public boolean isFinal() {
      return isFinal;
    }

    public void setTransition(String character, State state) {
        transitions.put(character, state);
    }

    public State getTransition(String character) {
        return transitions.get(character);
    }
}