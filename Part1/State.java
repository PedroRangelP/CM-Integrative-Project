import java.util.HashTable;

public class State {
    public String id;
    private boolean isFinal;
    private HashTable<String, State> transitions;

    public State(String id) {
      this.id = id;
      this.isFinal = false;
      this.transitions = new HashTable<>();
    }

    public void setFinal() {
      this.isFinal = true;
    }

    public boolean isFinal() {
      return isFinal;
    }

    public void setTransition(State state, String key) {
        transitions.put(key, state);
    }

    public void getTransition(String key) {
        transitions.get(key);
    }
}