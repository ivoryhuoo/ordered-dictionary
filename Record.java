/**
 * This class represents the records to be stored in the internal nodes of the binary search tree. 
 * Each object of this class will have two instance variables: Key theKey and String data.
 * 
 * @author Ivory Huo 
 */

public class Record {
	
	// Instance variables for the class
    private Key theKey;
    private String data;

    /**
     * Constructor which initializes a new Record object with the specified parameters
     * 
     * @param k        the Key of the Record
     * @param theData  the data string associated with the Key
     */
    public Record(Key k, String theData) {
        this.theKey = k;
        this.data = theData;
    }

    /**
     * Returns theKey
     * 
     * @return the Key of this Record
     */
    public Key getKey() {
        return theKey;
    }

    /**
     * Returns data
     * 
     * @return the data string of this Record
     */
    public String getDataItem() {
        return data;
    }

}
