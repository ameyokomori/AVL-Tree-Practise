package au.edu.uq.itee.comp3506.assn2.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A class designed to read switches and call records from file and sort the valid records.
 * 
 * Memory efficiency: O(n^2)
 * 
 * @author Weiye Zhao 44612975
 *
 */
public class ReadData {
	
	SwitchAVLTree<Integer> switchesTree;
	RecordAVLTree<Long> dialTree;
	RecordAVLTree<Long> receiveTree;
	RecordAVLTree<Integer> connectTree;
	TimeAVLTree timeTree;
	CallRecord cr;
	ValueList<CallRecord> rl;
	
	int timecount = 0;
	int dialcount = 0;
	
	CallRecord records;
	
	int switches;
	long dialler;
	long receiver;
	List<Integer> connectionPath;
	int diallerSwitch;
	int receiverSwitch;
	LocalDateTime timeStamp;
	int connectedSwitches;
	
	/**
	 * Runtime efficiency: O(1)
	 */
	public void ReadDate() {
		switches = -1;
		dialler = -1;
		receiver = -1;
		connectionPath = new ArrayList<Integer>();
		diallerSwitch = -1;
		receiverSwitch = -1;
		connectedSwitches = 0;
		timeStamp = LocalDateTime.parse("0000-01-01T00:00:00.000");
		
	}
	
	private SwitchAVLTree<Integer> readSwitches() {
		switchesTree = new SwitchAVLTree<Integer>();
		int count = 0;
		String filePath = "data/switches.txt";		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String calls = null;
		
			while((calls = reader.readLine()) != null) {
				//System.out.println(calls);
				count++;
				if (count == 1) {
					continue;
				}
				
				switches = Integer.parseInt(calls);
				
				switchesTree.insert(switches);
			}
			reader.close();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return switchesTree;
	}
	
	/**
	 * Read all data from file, store the valid records in data structures.
	 * 
	 * Runtime efficiency: O(n^2)
	 */
	public void readAllRecords() {
		timeTree = new TimeAVLTree();
		dialTree = new RecordAVLTree<Long>();
		receiveTree = new RecordAVLTree<Long>();
		connectTree = new RecordAVLTree<Integer>();
		rl = new ValueList<CallRecord>();
		
		readSwitches();
		
		String filePath = "data/call-records.txt";
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(filePath)); 
			String calls = null;
		
			while((calls = reader.readLine())!=null) {
				boolean valid = true;
				int size = 0;
				int flag = 0;
				
				ValueList<String> vl = new ValueList<String>();

				String[] d = calls.split(" ");
				
				for (int i = 0; i < d.length; i++) {
					if (!d[i].equals("")) {
						vl.add(d[i]);						
					} else {
						flag++;
					}
				}
				
				if (flag != 0) {
					size = vl.size();
					d = new String[size];					
					for(String a: vl) {
						d[--size] = a;
					}
				}
				
				valid = FindCorruptRecords(d);
				
				if (valid) {
					connectionPath = new ArrayList<Integer>();
					
					dialler = Long.parseLong(d[0]);
					receiver = Long.parseLong(d[d.length-2]);
					
					for (int i = 2; i < d.length-3; i++) {
						connectionPath.add(Integer.parseInt(d[i]));
					}
					
					diallerSwitch = Integer.parseInt(d[1]);
					receiverSwitch = Integer.parseInt(d[d.length-3]);
					
					timeStamp = LocalDateTime.parse(d[d.length-1]);
					
					cr = new CallRecord(dialler, receiver, diallerSwitch, receiverSwitch, connectionPath, timeStamp);
					rl.add(cr);
					
				} else {
					continue;
				}
			}
			reader.close();
		} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (CallRecord c: rl) {
			timeTree.insert(c.getTimeStamp(), c);
			dialTree.insert(c.getDialler(), c);
			receiveTree.insert(c.getReceiver(), c);
			for (int i: c.getConnectionPath()) {
				connectTree.insert(i, c);
			}
		}		
	}
	
	private boolean FindCorruptRecords(String[] d) {
		if (d.length > 5) {			
			if (d[0].length() != 10 && d[d.length-2].length() != 10 && d[1].length() != 5 && d[d.length-3].length() != 5) {
				return false;
			}			
			if (!d[1].equals(d[2])) {
				return false;
			}			
			for (int i = 1; i < d.length-2; i++) {
				if (d[i].equals("") || d[i].length() != 5 || switchesTree.search(Integer.parseInt(d[i])) == null) {
					return false;					
				} else if (d.length > 6 && i >= 2 && i <= d.length-5) {
					if (d[i].equals(d[i+1])) {
						return false;
					}
				}
			}			
		} else if (d.length == 5) {
			if (d[0].length() != 10 && d[3].length() != 10 && d[1].length() != 5 && d[2].length() != 5) {
				return false;
			}			
			for (int i = 1; i <= 2; i++) {
				if (switchesTree.search(Integer.parseInt(d[i])) == null) {
					return false;							
				}
			}
		} else if (d.length < 5) {
			return false;
		}		
		return true;
	}
	
	/**
	 * Runtime efficiency: O(1)
	 * 
	 * @return switchesTree the tree which take valid switches as key.
	 */
	public SwitchAVLTree<Integer> readSwitchesRecord() {
		return switchesTree;
	}
	
	/**
	 * Runtime efficiency: O(1)
	 * 
	 * @return timeTree the tree which take time stamp as key.
	 */
	public TimeAVLTree readTimeRecord() {
		return timeTree;
	}
	
	/**
	 * Runtime efficiency: O(1)
	 * 
	 * @return dialTree the tree which take dialing number as key.
	 */
	public RecordAVLTree<Long> readDialRecord() {
		return dialTree;
	}
	
	/**
	 * Runtime efficiency: O(1)
	 * 
	 * @return receiveTree the tree which take receiving number as key.
	 */
	public RecordAVLTree<Long> readReceiveRecord() {
		return receiveTree;
	}
	
	/**
	 * Runtime efficiency: O(1)
	 * 
	 * @return connectTree the tree which take switches in call records as key.
	 */
	public RecordAVLTree<Integer> readConnectRecord() {
		return connectTree;
	}
}
