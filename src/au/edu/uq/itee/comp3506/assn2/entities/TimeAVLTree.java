package au.edu.uq.itee.comp3506.assn2.entities;

import java.time.LocalDateTime;

/**
 * Implementation of AVL tree which used to store call records based on time stamp.
 * 
 * Memory efficiency: O(n)
 * 
 * @author Wayne
 */
public class TimeAVLTree {
	private AVLTreeNode root;
	
	/**
	 * Node class of tree.
	 */
	private class AVLTreeNode {			
		LocalDateTime key; // Key should be time stamp.
		ValueList<CallRecord> record = new ValueList<CallRecord>();		
		int height; // Tree height
		AVLTreeNode left; // Left child
		AVLTreeNode right; // Right child

		/**
		 * Runtime efficiency: O(1)
		 */
		public AVLTreeNode(LocalDateTime key, CallRecord cr, AVLTreeNode left, AVLTreeNode right) {
			// TODO Auto-generated constructor stub
			this.key = key;
			record.add(cr);
			this.left = left;
			this.right = right;
			this.height = 0;	
		}
	}
	
	/**
	 * Runtime efficiency: O(1)
	 */
	public TimeAVLTree() {
		root = null;
	}
	
	private int height(AVLTreeNode tree) {
		if (tree != null) {
			return tree.height;
		}
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
	private AVLTreeNode leftLeftRotation(AVLTreeNode k2) {
		AVLTreeNode k1;

		k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;

		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;

		return k1;
	}

	private AVLTreeNode rightRightRotation(AVLTreeNode k1) {
		AVLTreeNode k2;

		k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;

		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		k2.height = Math.max(height(k2.right), k1.height) + 1;

		return k2;
	}

	private AVLTreeNode leftRightRotation(AVLTreeNode k3) {
		k3.left = rightRightRotation(k3.left);

		return leftLeftRotation(k3);
	}

	private AVLTreeNode rightLeftRotation(AVLTreeNode k1) {
		k1.right = leftLeftRotation(k1.right);

		return rightRightRotation(k1);
	}	
	
	private AVLTreeNode insert(AVLTreeNode tree, LocalDateTime key, CallRecord cr) {
		// TODO Auto-generated method stub
		if (tree == null) {
			// Create new node
			tree = new AVLTreeNode(key, cr, null, null);
		} else {
			int cmp = key.compareTo(tree.key);

			if (cmp < 0) { // Insert key to the left
				tree.left = insert(tree.left, key, cr);
				// Rotation
				if (height(tree.left) - height(tree.right) == 2) {
					if (key.compareTo(tree.left.key) < 0) {
						tree = leftLeftRotation(tree);
					} else {
						tree = leftRightRotation(tree);
					}
				}
			} else if (cmp > 0) { // Insert key to the right
				tree.right = insert(tree.right, key, cr);
				// Rotation
				if (height(tree.right) - height(tree.left) == 2) {
					if (key.compareTo(tree.right.key) > 0) {
						tree = rightRightRotation(tree);
					} else {
						tree = rightLeftRotation(tree);
					}
				}
			} else { // Add record to the exist key
				tree.record.add(cr);
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
	 * @param key the key value of the tree.
	 * @param cr the record to be inserted.
	 */
	public void insert(LocalDateTime timeStamp, CallRecord cr) {
		// TODO Auto-generated method stub
		root = insert(root, timeStamp, cr);
	}	
	
	private AVLTreeNode search(AVLTreeNode x, LocalDateTime key) {
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
	public AVLTreeNode search(LocalDateTime key) {
		return search(root, key);
	}
	
	private AVLTreeNode searchClosestStartTime(AVLTreeNode x, LocalDateTime startTime, AVLTreeNode closestStart) {
		if (x == null) {
			return closestStart;
		}
		
		int cmp = startTime.compareTo(x.key);
		
		if (cmp < 0) {
			closestStart = x;
			return searchClosestStartTime(x.left, startTime, closestStart);
		}			
		else if (cmp > 0) {
			return searchClosestStartTime(x.right, startTime, closestStart);
		}			
		else {
			return x;
		}			
	}
	
	/**
	 * Find the closest start time tramp.
	 * 
	 * @param startTime the time tramp need to be found.
	 * @return the closest or the time tramp with same value.
	 */
	private AVLTreeNode searchClosestStartTime(LocalDateTime startTime) {
		AVLTreeNode closestStart = null;
		return searchClosestStartTime(root, startTime, closestStart);	
	}
	
	private AVLTreeNode searchClosestEndTime(AVLTreeNode x, LocalDateTime endTime, AVLTreeNode closestEnd) {
		if (x == null) {
			return closestEnd;
		}
		
		int cmp = endTime.compareTo(x.key);
		
		if (cmp < 0) {			
			return searchClosestEndTime(x.left, endTime, closestEnd);
		}			
		else if (cmp > 0) {
			closestEnd = x;
			return searchClosestEndTime(x.right, endTime, closestEnd);
		}			
		else {
			return x;
		}			
	}
	
	/**
	 * Find the closest end time tramp.
	 * 
	 * @param endTime the time tramp need to be found.
	 * @return the closest or the time tramp with same value.
	 */
	private AVLTreeNode searchClosestEndTime(LocalDateTime endTime) {
		AVLTreeNode closestEnd = null;		
		return searchClosestEndTime(root, endTime, closestEnd);
	}
	
	private ValueList<CallRecord> callsMade(AVLTreeNode tree, AVLTreeNode start, AVLTreeNode end, ValueList<CallRecord> calls) {
		if (tree != null && start != null && end != null) {
			callsMade(tree.left, start, end, calls);
			if (tree.key.compareTo(start.key) >= 0 && tree.key.compareTo(end.key) <= 0) {
				for (CallRecord c: tree.record) {
					calls.add(c);
				}
			} else if (tree.key.compareTo(end.key) > 0) {
				return calls;
			}
			callsMade(tree.right, start, end, calls);
		}
		return calls;
	}
	
	/**
	 * Determine all calls made over a specified time period.
	 * 
	 * Runtime efficiency: O(n^2)
	 * 
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of details of all calls made between start and end time.
	 */
	public ValueList<CallRecord> callsMade(LocalDateTime startTime, LocalDateTime endTime) {
		ValueList<CallRecord> calls = new ValueList<CallRecord>();
		AVLTreeNode start = searchClosestStartTime(startTime);
		AVLTreeNode end = searchClosestEndTime(endTime);
		return callsMade(root, start, end, calls);
	}
}
