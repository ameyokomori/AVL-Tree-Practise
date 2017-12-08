package au.edu.uq.itee.comp3506.assn2.entities;

/**
 * Implementation of AVL tree which used to store switches.
 * 
 * Memory efficiency: O(n)
 * 
 * @author Weiye Zhao 44612975
 */
public class SwitchAVLTree<T extends Comparable<T>> {

	private AVLTreeNode<T> root;

	/**
	 * Node class of tree.
	 */
	@SuppressWarnings("hiding")
	private class AVLTreeNode<T extends Comparable<T>> {
		T key;
		int height; // Tree height
		AVLTreeNode<T> left; // Left child
		AVLTreeNode<T> right; // Right child

		/**
		 * Runtime efficiency: O(1)
		 */
		public AVLTreeNode(T key, AVLTreeNode<T> left, AVLTreeNode<T> right) {
			this.key = key;
			this.left = left;
			this.right = right;
			this.height = 0;
		}
	}
	
	/**
	 * Runtime efficiency: O(1)
	 */
	public SwitchAVLTree() {
		root = null;
	}
	
	private int height(AVLTreeNode<T> tree) {
		if (tree != null)
			return tree.height;

		return 0;
	}

	/**
	 * Return the height of tree.
	 * 
	 * Runtime efficiency: O(1)
	 */
	public int height() {
		return height(root);
	}

	/**
	 * Tree rotation.
	 */
	private AVLTreeNode<T> leftLeftRotation(AVLTreeNode<T> k2) {
		AVLTreeNode<T> k1;

		k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;

		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;

		return k1;
	}

	private AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> k1) {
		AVLTreeNode<T> k2;

		k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;

		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		k2.height = Math.max(height(k2.right), k1.height) + 1;

		return k2;
	}

	private AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> k3) {
		k3.left = rightRightRotation(k3.left);

		return leftLeftRotation(k3);
	}

	private AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> k1) {
		k1.right = leftLeftRotation(k1.right);

		return rightRightRotation(k1);
	}

	private AVLTreeNode<T> insert(AVLTreeNode<T> tree, T key) {
		if (tree == null) {
			// Create new node
			tree = new AVLTreeNode<T>(key, null, null);
		} else {
			int cmp = key.compareTo(tree.key);

			if (cmp < 0) { // Insert key to the left
				tree.left = insert(tree.left, key);
				// Rotation
				if (height(tree.left) - height(tree.right) == 2) {
					if (key.compareTo(tree.left.key) < 0) {
						tree = leftLeftRotation(tree);
					} else {
						tree = leftRightRotation(tree);
					}
				}
			} else if (cmp > 0) { // Insert key to the right
				tree.right = insert(tree.right, key);
				// Rotation
				if (height(tree.right) - height(tree.left) == 2) {
					if (key.compareTo(tree.right.key) > 0) {
						tree = rightRightRotation(tree);
					} else {
						tree = rightLeftRotation(tree);
					}
				}
			} else { // Key already exist.
				System.out.println("Duplicate switch.");				
			}
		}

		tree.height = Math.max(height(tree.left), height(tree.right)) + 1;

		return tree;
	}

	/**
	 * Insert a record.
	 * 
	 * Runtime efficiency: O(log n)
	 * 
	 * @param key and value of the tree.
	 */
	public void insert(T key) {
		root = insert(root, key);
	}
	
	private AVLTreeNode<T> search(AVLTreeNode<T> x, T key) {
		if (x == null) {
			return x;
		}
		
		int cmp = key.compareTo(x.key);
		
		if (cmp < 0) {
			return search(x.left, key);
		}			
		else if (cmp > 0) {
			return search(x.right, key);
		}			
		else {
			return x;
		}			
	}
	
	/**
	 * Search if the key is in the tree.
	 * 
	 * Runtime efficiency: O(log n)
	 * 
	 * @param key the key to be searched.
	 * @return the found node.
	 */
	public AVLTreeNode<T> search(T key) {
		return search(root, key);
	}
}
