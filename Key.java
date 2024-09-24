/**
 * This class represents the key of the data items stored in the internal nodes of the binary search tree implementing the ordered dictionary. 
 * Each object of this class will have two instance variables: label and type.
 * 
 * @author Ivory Huo 
 */

public class Key implements Comparable<Key> { 
	
	// Instance variables for the class 
    private String label;
    private int type;

    /**
     * Constructs a new Key object with the specified label and type
     * 
     * @param theLabel: the label of the Key
     * @param theType: the type of the Key
     */
    public Key(String theLabel, int theType) {
        // Convert theLabel to lower case before storing it in instance variable label
        this.label = theLabel.toLowerCase();
        this.type = theType;
    }

    /**
     * Returns the String stored in instance variable label 
     * 
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the value of instance variable type
     * 
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * Compares this Key object with another Key
     * 
     * @param k: the Key to compare with
     * @return 0 if this Key is equal to k, -1 if this Key is smaller than k, and 1 otherwise
     */
    @Override
    public int compareTo(Key k) {
        // Compare labels first
        int labelComparison = this.label.compareTo(k.label);
        
        if (labelComparison == 0) { // If labels are equal, compare types
            if (this.type == k.type) { // Equal 
                return 0; 
            } else if (this.type < k.type) { // Smaller
                return -1; 
            } else { // Larger 
                return 1; 
            }
        } else { // Based on label comparison
            return labelComparison < 0 ? -1 : 1; 
        }
    }

}

