/**
 * This class represents a node of the binary search tree
 * 
 * @author Ivory Huo 
 */

public class BSTNode {
	
	// Instance variables 
    private Record item;
    private BSTNode leftChild;
    private BSTNode rightChild;
    private BSTNode parent;

    /**
     * Constructor of the class
     * 
     * @param item the Record to be stored in this node
     */
    public BSTNode(Record item) {
        this.item = item;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    /**
     * Returns the Record object stored in this node
     * 
     * @return the Record object
     */
    public Record getRecord() {
        return item;
    }
    
    /**
     * Stores the given record in this node
     * 
     * @param d the Record to store
     */
    public void setRecord(Record d) {
        this.item = d;
    }

    /**
     * Returns the left child
     * 
     * @return the left child node
     */
    public BSTNode getLeftChild() {
        return leftChild;
    }

    /**
     * Returns the right child
     * 
     * @return the right child node
     */
    public BSTNode getRightChild() {
        return rightChild;
    }

    /**
     * Returns the parent
     * 
     * @return the parent node
     */
    public BSTNode getParent() {
        return parent;
    }

    /**
     * Sets the left child to the specified value
     * 
     * @param u the node to set as the left child
     */
    public void setLeftChild(BSTNode u) {
        this.leftChild = u; // Set to u
        if (u != null) { // If not null, set parent 
            u.setParent(this);
        }
    }

    /**
     * Sets the right child to the specified value
     * 
     * @param u the node to set as the right child
     */
    public void setRightChild(BSTNode u) {
        this.rightChild = u; // Set to u
        if (u != null) { // If not null, set parent 
            u.setParent(this);
        }
    }

    /**
     * Sets the parent to the specified value
     * 
     * @param u the node to set as the parent
     */
    public void setParent(BSTNode u) {
        this.parent = u;
    }

    /**
     * Returns true if this node is a leaf; false otherwise. A node is a leaf if both of its children are null.
     * 
     * @return true if this node is a leaf, false otherwise
     */
    public boolean isLeaf() {
        return leftChild == null && rightChild == null; // If both children are null
    }

	

}
