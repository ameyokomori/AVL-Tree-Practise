package au.edu.uq.itee.comp3506.assn2.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of AVL tree which used to store call records.
 * 
 * Memory efficiency: O(n)
 * 
 * @author Wayne
 */
public class RecordAVLTree<T extends Comparable<T>> {
	private AVLTreeNode<T> root;
	private int max; // Max size of connection.
	private int maxSwitch; // The switch which has most connections.
	private int min; // Min size of connection.
	private int minSwitch; // The switch which has fewest connections.
	
	/**
	 * Node class of tree.
	 */
	@SuppressWarnings("hiding")
	private class AVLTreeNode<T extends Comparable<T>> {			
		T key;	
		ValueList<CallRecord> record = new ValueList<CallRecord>();
		int height; // Tree height
		AVLTreeNode<T> left; // Left child
		AVLTreeNode<T> right; // Right child

		/**
		 * Runtime efficiency: O(1)
		 */
		public AVLTreeNode(T key, CallRecord cr, AVLTreeNode<T> left, AVLTreeNode<T> right) {
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
	public RecordAVLTree() {
		root = null;
		max = -1;
		min = Integer.MAX_VALUE;
		maxSwitch = 0;
		minSwitch = 0;
	}
	
	private int height(AVLTreeNode<T> tree) {
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

	private AVLTreeNode<T> insert(AVLTreeNode<T> tree, T key, CallRecord cr) {
		if (tree == null) {
			// Create new node
			tree = new AVLTreeNode<T>(key, cr, null, null);
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
	public void insert(T key, CallRecord cr) {
		// TODO Auto-generated method stub
		root = insert(root, key, cr);
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
	
	/**
	 * Find all receiving phone numbers called from a single dialing phone number.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @return List of all the phone numbers called by dialer.
	 *         The list will contain duplicates of the receiver if the dialer called the receiver multiple times.
	 */
	public ValueList<Long> findReceiver(T dialler) {
		AVLTreeNode<T> tree = search(dialler);
		ValueList<Long> receiver = new ValueList<Long>(); // log n
		if (tree == null) {
			return receiver;
		}
		for (CallRecord t: tree.record) { // n
			receiver.add(t.getReceiver());
		}
		return receiver;
		// log n + n
	}
	
	/**
	 * Find all receiving phone numbers called from a single dialing phone number over a specified period of time.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of all the phone numbers called by dialer between start and end time.
	 *         The list will contain duplicates of the receiver if the dialer called the receiver multiple times.
	 */
	public ValueList<Long> findReceiver(T dialler, LocalDateTime startTime, LocalDateTime endTime) {
		AVLTreeNode<T> tree = search(dialler);
		ValueList<Long> receiver = new ValueList<Long>();
		if (tree == null) {
			return receiver;
		}
		for (CallRecord t: tree.record) {
			if (t.getTimeStamp().compareTo(startTime) >= 0 && t.getTimeStamp().compareTo(endTime) <= 0) {
				receiver.add(t.getReceiver());
			}			
		}
		return receiver;
	}
	
	/**
	 * Find all phone numbers that dialed a single receiving phone number.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param receiver The phone number that received the calls.
	 * @return List of all the phone numbers that called the receiver.
	 *         The list will contain duplicates of the caller if they called the receiver multiple times.
	 */
	public ValueList<Long> findDialler(T receiver) {
		AVLTreeNode<T> tree = search(receiver);
		ValueList<Long> dialler = new ValueList<Long>();
		if (tree == null) {
			return dialler;
		}
		for (CallRecord t: tree.record) {
			dialler.add(t.getDialler());
		}
		return dialler;
	}
	
	/**
	 * Find all phone numbers that dialed a single receiving phone number over a specified period of time.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param receiver The phone number that received the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of all the phone numbers that called the receiver between start and end time.
	 *         The list will contain duplicates of the caller if they called the receiver multiple times.
	 */
	public ValueList<Long> findDialler(T receiver, LocalDateTime startTime, LocalDateTime endTime) {
		AVLTreeNode<T> tree = search(receiver);
		ValueList<Long> dialler = new ValueList<Long>();
		if (tree == null) {
			return dialler;
		}
		for (CallRecord t: tree.record) {
			if (t.getTimeStamp().compareTo(startTime) >= 0 && t.getTimeStamp().compareTo(endTime) <= 0) {
				dialler.add(t.getDialler());
			}	
		}
		return dialler;
	}
	
	/**
	 * For a dialing phone number determine if there is a fault in the network for any of its connection attempts.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @return The list of identifiers of the faulty switches or an empty list if no fault was found.
	 */
	public ValueList<Integer> findConnectionFault(T dialler) {
		ValueList<Integer> faults = new ValueList<Integer>();
		List<Integer> connectionPath = new ArrayList<Integer>();
		int receiveSwitch = 0;
		AVLTreeNode<T> tree = search(dialler);
		if (tree == null) {
			return faults;
		}		
		for (CallRecord t: tree.record) {
			connectionPath = new ArrayList<Integer>();
			receiveSwitch = t.getReceiverSwitch();
			connectionPath = t.getConnectionPath();
			
			if (connectionPath.isEmpty()) {
				faults.add(t.getDiallerSwitch());
			} else {
				int lastConnection = connectionPath.get(connectionPath.size()-1); //
				if (lastConnection != receiveSwitch) {
					faults.add(lastConnection);
				}
			}
		}	
		return faults;
	}
	
	/**
	 * For a dialing phone number determine if there is a fault in the network for any of its connection attempts 
	 * over a specified period of time.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The list of identifiers of the faulty switches;
	 *         or an empty list if no fault was found between start and end time.
	 */
	public ValueList<Integer> findConnectionFault(T dialler, LocalDateTime startTime, LocalDateTime endTime) {
		ValueList<Integer> faults = new ValueList<Integer>();
		List<Integer> connectionPath = new ArrayList<Integer>();
		int receiveSwitch = 0;
		AVLTreeNode<T> tree = search(dialler);
		if (tree == null) {
			return faults;
		}
		for (CallRecord t: tree.record) {
			if (t.getTimeStamp().compareTo(startTime) >= 0 && t.getTimeStamp().compareTo(endTime) <= 0) {
				connectionPath = new ArrayList<Integer>();
				receiveSwitch = t.getReceiverSwitch();
				connectionPath = t.getConnectionPath();
				
				if (connectionPath.isEmpty()) {
					faults.add(t.getDiallerSwitch());
				} else {
					int lastConnection = connectionPath.get(connectionPath.size()-1); //
					if (lastConnection != receiveSwitch) {
						faults.add(lastConnection);
					}
				}
			}					
		}	
		return faults;
	}
	
	/**
	 * For a receiving phone number determine if there is a fault in the network for any of its connection attempts.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param reciever The phone number that should have received the calls.
	 * @return The list of identifiers of the faulty switches or an empty list if no fault was found.
	 */
	public ValueList<Integer> findReceivingFault(T receiver) {
		ValueList<Integer> faults = new ValueList<Integer>();
		List<Integer> connectionPath = new ArrayList<Integer>();
		int receiveSwitch = 0;
		AVLTreeNode<T> tree = search(receiver);
		if (tree == null) {
			return faults;
		}		
		for (CallRecord t: tree.record) {
			connectionPath = new ArrayList<Integer>();
			receiveSwitch = t.getReceiverSwitch();
			connectionPath = t.getConnectionPath();
			
			if (connectionPath.isEmpty()) {
				faults.add(t.getDiallerSwitch());
			} else {
				int lastConnection = connectionPath.get(connectionPath.size()-1); //
				if (lastConnection != receiveSwitch) {
					faults.add(lastConnection);
				}
			}		
		}	
		return faults;
	}
	
	/**
	 * For a receiving phone number determine if there is a fault in the network for any of its connection attempts 
	 * over a specified period of time.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param reciever The phone number that should have received the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The list of identifiers of the faulty switches;
	 *         or an empty list if no fault was found between start and end time.
	 */
	public ValueList<Integer> findReceivingFault(T receiver, LocalDateTime startTime, LocalDateTime endTime) {
		ValueList<Integer> faults = new ValueList<Integer>();
		List<Integer> connectionPath = new ArrayList<Integer>();
		int receiveSwitch = 0;
		AVLTreeNode<T> tree = search(receiver);
		if (tree == null) {
			return faults;
		}
		for (CallRecord t: tree.record) {
			if (t.getTimeStamp().compareTo(startTime) >= 0 && t.getTimeStamp().compareTo(endTime) <= 0) {
				connectionPath = new ArrayList<Integer>();
				receiveSwitch = t.getReceiverSwitch();
				connectionPath = t.getConnectionPath();
				
				if (connectionPath.isEmpty()) {
					faults.add(t.getDiallerSwitch());
				} else {
					int lastConnection = connectionPath.get(connectionPath.size()-1); //
					if (lastConnection != receiveSwitch) {
						faults.add(lastConnection);
					}
				}
			}					
		}	
		return faults;
	}
	
	/**
	 * Reset max and max switch.
	 * 
	 * Runtime efficiency: O(1)
	 */
	public void resetMax() {
		this.max = -1;
		this.maxSwitch = 0;
	}
	
	/**
	 * Reset min and min switch.
	 * 
	 * Runtime efficiency: O(1)
	 */
	public void resetMin() {
		this.min = Integer.MAX_VALUE;
		this.minSwitch = 0;
	}
	
	private int maxConnections(AVLTreeNode<T> tree) {
		if (tree != null) {
			int count = tree.record.size();
			if (count > max) {
				max = count;
				maxSwitch = (int) tree.key;
			} else if (count == max) {
				if (maxSwitch > (int) tree.key) {
					maxSwitch = (int) tree.key;
				}
			}
			maxConnections(tree.left);
			maxConnections(tree.right);
		}
		return maxSwitch;
	}
	
	/**
	 * Determine which switch has the most connections.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @return The identifier of the switch that had the most connections.
	 *         If multiple switches have the most connections, the smallest switch identifier is returned.
	 */
	public int maxConnections() {
		resetMax();
		return maxConnections(root);
	}
	
	private int maxConnections(AVLTreeNode<T> tree, LocalDateTime startTime, LocalDateTime endTime) {
		if (tree != null) {
			int flag = 0;
			for (CallRecord t: tree.record) {				
				if (t.getTimeStamp().compareTo(startTime) >= 0 && t.getTimeStamp().compareTo(endTime) <= 0) {
					flag++;
				}
			}
			if (flag > max && flag != 0) {
				max = flag;
				maxSwitch = (int) tree.key;
			} else if (flag == max) {
				if (maxSwitch > (int) tree.key) {
					maxSwitch = (int) tree.key;
				}
			}
			
			maxConnections(tree.left, startTime, endTime);
			maxConnections(tree.right, startTime, endTime);
		}
		return maxSwitch;
	}
	
	/**
	 * Determine which switch has the most connections over a specified period of time.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The identifier of the switch that had the most connections between start and end time.
	 *         If multiple switches have the most connections, the smallest switch identifier is returned.
	 */
	public int maxConnections(LocalDateTime startTime, LocalDateTime endTime) {
		resetMax();
		return maxConnections(root, startTime, endTime);
	}
	
	private int minConnections(AVLTreeNode<T> tree) {
		if (tree != null) {
			int count = tree.record.size();
			if (count < min) {
				min = count;
				minSwitch = (int) tree.key;
			} else if (count == min) {
				if (minSwitch > (int) tree.key) {
					minSwitch = (int) tree.key;
				}
			}
			minConnections(tree.left);
			minConnections(tree.right);
		}
		return minSwitch;
	}
	
	/**
	 * Determine which switch has the fewest connections.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @return The identifier of the switch that had the fewest connections.
	 *         If multiple switches have the fewest connections, the smallest switch identifier is returned.
	 */
	public int minConnections() {
		resetMin();
		return minConnections(root);
	}
	
	private int minConnections(AVLTreeNode<T> tree, LocalDateTime startTime, LocalDateTime endTime) {
		if (tree != null) {
			int flag = 0;
			for (CallRecord t: tree.record) {
				if (t.getTimeStamp().compareTo(startTime) >= 0 && t.getTimeStamp().compareTo(endTime) <= 0) {
					flag++;
				}
			}
			
			if (flag < min && flag != 0) {
				min = flag;
				minSwitch = (int) tree.key;
			} else if (flag == min) {
				if (minSwitch > (int) tree.key) {
					minSwitch = (int) tree.key;
				}
			}
			
			minConnections(tree.left, startTime, endTime);
			minConnections(tree.right, startTime, endTime);
		}
		return minSwitch;
	}
	
	/**
	 * Determine which switch has the fewest connections over a specified period of time.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The identifier of the switch that had the fewest connections between start and end time.
	 *         If multiple switches have the fewest connections, the smallest switch identifier is returned.
	 */
	public int minConnections(LocalDateTime startTime, LocalDateTime endTime) {
		resetMin();
		return minConnections(root, startTime, endTime);
	}
}
