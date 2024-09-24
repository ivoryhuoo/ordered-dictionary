/**
 * This class implements an ordered dictionary using a binary search tree. 
 * Uses a Record object to store the data contained in each internal node of the tree. 
 * Only the internal nodes will store information & the leaves must be nodes storing null Record objects.
 * The key for an internal node will be the Key object from the Record stored in that node.
 * Implement all public methods specified in the BSTDictionaryADT interface and the constructor
 * 
 * @author Ivory Huo
 */
public class BSTDictionary implements BSTDictionaryADT {
	
    private BinarySearchTree bst;

    /**
     * Constructor for the BSTDictionary class
     * Initializes a new instance of a binary search tree to store the records.
     */
    public BSTDictionary() {
        this.bst = new BinarySearchTree(); // Initialize the underlying binary search tree
    }

    /**
     * Returns the Record with key k, or null if the Record is not in the dictionary
     *
     * @param k: key of the record to retrieve
     * @return The Record associated with the key if it exists, otherwise null
     */
    @Override
    public Record get(Key k) {
        BSTNode node = bst.get(bst.getRoot(), k); // Use BST get method to find the node
        return node != null ? node.getRecord() : null; // Return the record if node found
    }

    /**
     * Inserts d into the ordered dictionary. It throws a DictionaryException if a Record with the same Key as d is already in the dictionary.
     *
     * @param dL record to insert into the dictionary
     * @throws DictionaryException if a record with the same key already exists in the dictionary
     */
    @Override
    public void put(Record d) throws DictionaryException {
        try {
            bst.insert(bst.getRoot(), d); // Attempt to insert the record
        } catch (DictionaryException e) { // Throw exception 
            throw new DictionaryException("Record with the same Key already exists.");
        }
    }

    /**
     * Removes the Record with Key k from the dictionary. It throws a DictionaryException if the Record is not in the dictionary. 
     *
     * @param k: key of the record to remove
     * @throws DictionaryException if the record is not found in the dictionary
     */
    @Override
    public void remove(Key k) throws DictionaryException {
        try {
            bst.remove(bst.getRoot(), k); // Attempt to remove record
        } catch (DictionaryException e) { // Throw exception
            throw new DictionaryException("Record not in the dictionary.");
        }
    }

    /**
     * Returns the successor of k (the Record from the ordered dictionary with smallest Key larger than k); it returns null if the given Key has no successor 
     * The given Key DOES NOT need to be in the dictionary
     *
     * @param k: key to find the successor for
     * @return The successor record, or null if no successor exists.
     */
    @Override
    public Record successor(Key k) {
        BSTNode succNode = bst.successor(bst.getRoot(), k); // Find successor node
        return succNode != null ? succNode.getRecord() : null; // Return record if found
    }

    /**
     * Returns the predecessor of k (the Record from the ordered dictionary with largest key smaller than k; it returns null if the given Key has no predecessor
     * The given Key DOES NOT need to be in the dictionary
     *
     * @param k: key to find the predecessor for
     * @return The predecessor record, or null if no predecessor exists
     */
    @Override
    public Record predecessor(Key k) {
        BSTNode predNode = bst.predecessor(bst.getRoot(), k); // Find predecessor node
        return predNode != null ? predNode.getRecord() : null; // Return record if found
    }

    /**
     * Returns the Record with smallest key in the ordered dictionary. Returns null if the dictionary is empty.
     *
     * @return The record with the smallest key, or null if the dictionary is empty.
     */
    @Override
    public Record smallest() {
        BSTNode smallestNode = bst.smallest(bst.getRoot()); // Find node with the smallest key
        return smallestNode != null ? smallestNode.getRecord() : null; // Return record if found
    }

    /**
     * Returns the Record with largest key in the ordered dictionary. Returns null if the dictionary is empty
     *
     * @return The record with the largest key, or null if the dictionary is empty
     */
    @Override
    public Record largest() {
        BSTNode largestNode = bst.largest(bst.getRoot()); // Find node with the largest key
        return largestNode != null ? largestNode.getRecord() : null; // Return record if found
    }

}
