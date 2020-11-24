import java.util.LinkedList;

/**
 * This class is used to build the leaves that make up the Parsing Tree.
 */
public class Leaf {
  private int depth;
  private String chain;
  private LinkedList<Leaf> children = new LinkedList<>();

  /**
   * Creates a new leaf with the given parameters
   * @param chain The string that represents the name of the leaf
   * @param depth The depth where the leaf is located
   */
  public Leaf(String chain, int depth) {
    this.chain = chain;
    this.depth = depth;
  }

  /**
   * This method gets the depth where the leaf is located
   * @return an integer representating the depth
   */
  public int getDepth() {
    return depth;
  }

  /**
   * This method gets the name of the leaf
   * @return a String representating the name of the leaf
   */
  public String getChain() {
    return chain;
  }

  /**
   * This method adds a child to the current leaf
   * @param leaf The leaf to add as a child
   */
  public void addChild(Leaf leaf) {
    children.add(leaf);
  }

  /**
   * This method gets all children of a leaf
   * @return the children of the current leaf
   */
  public LinkedList<Leaf> getChildren() {
    return children;
  }
}
