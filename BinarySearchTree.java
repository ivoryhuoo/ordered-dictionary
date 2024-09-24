/**
 * This class represents a binary search tree.
 * 
 * @author Ivory Huo
 */

public class BinarySearchTree {
	
    // Root node of binary search tree
    private BSTNode root;

    /**
     * The constructor for the class that creates a leaf node as the root of the tree
     */
    public BinarySearchTree() {
        this.root = null; // Initialize tree with no nodes
    }

    /**
     * Returns the root node of this binary search tree
     * 
     * @return The root node of the tree.
     */
    public BSTNode getRoot() {
        return root;
    }

    /**
     * Returns the node storing the given key; returns null if the key is not stored in the tree with root r
     * 
     * @param r: Starting node for the search
     * @param k: Key of the node to search for
     * @return the node with the specified key, or null if not found
     */
    public BSTNode get(BSTNode r, Key k) {
        if (r == null) { // If starting node is null (empty)
            return null; // Key is not present in the tree
        }
        int comparison = k.compareTo(r.getRecord().getKey());
        if (comparison == 0) { // Node with key found 
            return r; 
        } else if (comparison < 0) { // Search left subtree
            return get(r.getLeftChild(), k); 
        } else { // Search right subtree
            return get(r.getRightChild(), k); 
        }
    }

    /**
     * Adds the record to the binary search tree with root r
     * Throws a DictionaryException if the tree already stores a record with the same key as d
     * 
     * @param r: root node where the insertion starts
     * @param d: the record to insert
     * @throws DictionaryException if a record with the same key already exists
     */
    public void insert(BSTNode r, Record d) throws DictionaryException {
        if (root == null) {
            root = new BSTNode(d); // If tree is empty, inserted node becomes the root
        } else {
            insertRec(r, d); // Otherwise, insert record starting from root
        }
    }

    /**
     * Recursive method to insert a new record into the binary search tree
     * 
     * @param node: current node in the tree during the recursion
     * @param d: record to insert
     * @return The node itself or a new node if the insertion takes place
     * @throws DictionaryException if a record with the same key already exists
     */
    private BSTNode insertRec(BSTNode node, Record d) throws DictionaryException {
        if (node == null) {
            // Base case: Reached a leaf node, insert the new node here
            return new BSTNode(d);
        }
        // Compare the keys to decide whether to go left or right
        int comparison = d.getKey().compareTo(node.getRecord().getKey());
        if (comparison == 0) {
            // A node with this key already exists, throw an exception
            throw new DictionaryException("A record with the given key already exists.");
        } else if (comparison < 0) {
            // The new key is smaller, go to the left subtree
            node.setLeftChild(insertRec(node.getLeftChild(), d));
        } else {
            // The new key is larger, go to the right subtree
            node.setRightChild(insertRec(node.getRightChild(), d));
        }
        return node; // Return the unchanged node pointer
    }

    /**
     * Removes the node with the specified key from the tree starting at the given root
     * This is the public method that initiates the removal process
     * 
     * @param r: root of the subtree from which to remove the node
     * @param k: key of the node to be removed.
     * @throws DictionaryException if the node with the specified key does not exist in the tree.
     */
    public void remove(BSTNode r, Key k) throws DictionaryException {
        root = removeRec(r, k); // Start the recursive removal process from the root
    }

    /**
     * Recursive helper method to remove a node with a specific key from the tree
     * Handles three main cases: node with no child, node with one child, and node with two children
     * 
     * @param node: current node being inspected
     * @param k: key of the node to remove
     * @return The altered subtree with the specified node removed
     * @throws DictionaryException if the key is not found in the subtree rooted at the node
     */
    private BSTNode removeRec(BSTNode node, Key k) throws DictionaryException {
        if (node == null) {
            // Base case: if the node is null, key not found in the tree
            throw new DictionaryException("Key not found");
        }

        int comparison = k.compareTo(node.getRecord().getKey());
        if (comparison < 0) {
            // If key is smaller than the current node's key, go left
            node.setLeftChild(removeRec(node.getLeftChild(), k));
        } else if (comparison > 0) {
            // If key is larger than the current node's key, go right
            node.setRightChild(removeRec(node.getRightChild(), k));
        } else {
            // Found node to remove
            if (node.getLeftChild() == null) {
                // Case 1: No left child (including leaf node)
                return node.getRightChild();
            } else if (node.getRightChild() == null) {
                // Case 2: No right child
                return node.getLeftChild();
            }

            // Case 3: Two children. Find the smallest node in the right subtree, replace the current node with that node, and remove that smallest node
            BSTNode smallest = smallest(node.getRightChild());
            node.setRecord(smallest.getRecord());
            node.setRightChild(removeRec(node.getRightChild(), smallest.getRecord().getKey()));
        }
        return node; // Return the node itself (or its modified form).
    }

    /**
     * Finds the successor of the given key starting from the provided node
     * The successor is the next-larger key in the tree
     * Returns the node storing the successor of the given key in the tree with root r; returns null if the successor does not exist
     * 
     * @param r: Starting node for the search
     * @param k: Key whose successor is to be found
     * @return The successor node, or null if no successor exists
     */
    public BSTNode successor(BSTNode r, Key k) {
    	// Find the node with the given key
        BSTNode current = get(r, k); 
        if (current == null) {
            return null; // If the node doesn't exist, there's no successor
        }
        if (current.getRightChild() != null) {
            // If there's a right subtree, the successor is the smallest key in that subtree
            return smallest(current.getRightChild());
        }

        // If no right subtree, the successor is up the tree
        // Find the deepest ancestor for which the given node is in its left subtree
        BSTNode successor = null;
        BSTNode ancestor = root;
        while (ancestor != current) {
            if (current.getRecord().getKey().compareTo(ancestor.getRecord().getKey()) < 0) {
                successor = ancestor; // This could be the next larger key
                ancestor = ancestor.getLeftChild();
            } else {
                ancestor = ancestor.getRightChild();
            }
        }
        return successor; // Return the found successor, or null if it doesn't exist
    }
    
    /**
     * Finds the predecessor of the given key starting from the provided node
     * The predecessor is the node with the largest key less than the given key
     * Returns the node storing the predecessor of the given key in the tree with root r; returns null if the predecessor does not exist
     * 
     * @param r: starting node for the search
     * @param k: key whose predecessor is to be found
     * @return The predecessor node, or null if no predecessor exists
     */
    public BSTNode predecessor(BSTNode r, Key k) {
        BSTNode current = get(root, k); // Start by finding the node with the given key
        if (current == null) {
            return null; // If the node with the given key does not exist, return null
        }
        
        // If the left child is not null, the predecessor is the largest node in the left subtree
        if (current.getLeftChild() != null) {
            return largest(current.getLeftChild());
        }
        
        // Otherwise, move up the tree from the current node until we find a node that is a right child
        // The parent of such a node is the predecessor
        BSTNode predecessor = null;
        BSTNode ancestor = root;
        while (ancestor != current) {
            if (k.compareTo(ancestor.getRecord().getKey()) > 0) {
                predecessor = ancestor; // Update predecessor as we move down the tree
                ancestor = ancestor.getRightChild();
            } else {
                ancestor = ancestor.getLeftChild();
            }
        }
        return predecessor;
    }

    /**
     * Finds the node with the smallest key in the subtree rooted at the given node
     * The smallest node is the leftmost node in the subtree
     * Returns the node with the smallest key in tree with root r
     * 
     * @param r: root of the subtree from which to find the smallest node
     * @return The node with the smallest key in the subtree, or null if the subtree is empty
     */
    public BSTNode smallest(BSTNode r) {
        if (r == null) {
            return null; // Subtree is empty
        }
        BSTNode current = r;
        while (current.getLeftChild() != null) {
            current = current.getLeftChild(); // Move to the left child until we reach the leftmost node
        }
        return current; // The leftmost node is the smallest
    }

    /**
     * Finds the node with the largest key in the subtree rooted at the given node
     * The largest node is the rightmost node in the subtree
     * Returns the node with the largest key in tree with root r
     * 
     * @param r: root of the subtree from which to find the largest node
     * @return The node with the largest key in the subtree, or null if the subtree is empty
     */
    public BSTNode largest(BSTNode r) {
        if (r == null) {
            return null; // Subtree is empty
        }
        BSTNode current = r;
        while (current.getRightChild() != null) {
            current = current.getRightChild(); // Move to the right child until we reach the rightmost node
        }
        return current; // The rightmost node is the largest
    }


}
