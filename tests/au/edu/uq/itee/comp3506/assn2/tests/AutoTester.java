package au.edu.uq.itee.comp3506.assn2.tests;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import au.edu.uq.itee.comp3506.assn2.api.TestAPI;
import au.edu.uq.itee.comp3506.assn2.entities.CallRecord;
import au.edu.uq.itee.comp3506.assn2.entities.ReadData;
import au.edu.uq.itee.comp3506.assn2.entities.RecordAVLTree;
import au.edu.uq.itee.comp3506.assn2.entities.SwitchAVLTree;
import au.edu.uq.itee.comp3506.assn2.entities.TimeAVLTree;
import au.edu.uq.itee.comp3506.assn2.entities.ValueList;

/**
 * Hook class used by automated testing tool.
 * The testing tool will instantiate an object of this class to test the functionality of your assignment.
 * You must implement the method and constructor stubs below so that they call the necessary code in your application.
 * 
 * Memory efficiency: O(n^2)
 * 
 * @author Weiye Zhao 44612975
 */
public final class AutoTester implements TestAPI {
	// TODO Provide any data members required for the methods below to work correctly with your application.
	ReadData reader;
	SwitchAVLTree<Integer> switchesTree;
	RecordAVLTree<Long> dialTree;
	RecordAVLTree<Long> receiveTree;
	RecordAVLTree<Integer> connectTree;
	TimeAVLTree timeTree;

	/**
	 * Runtime efficiency: O(1)
	 */
	public AutoTester() {
		// TODO Create and initialize any objects required by the methods below.
		reader = new ReadData();
		reader.readAllRecords();
		switchesTree = reader.readSwitchesRecord();
		dialTree = reader.readDialRecord();
		receiveTree = reader.readReceiveRecord();
		timeTree = reader.readTimeRecord();
		connectTree = reader.readConnectRecord();
	}
	
	/**
	 * Tests search 1 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @return List of all the phone numbers called by dialer.
	 *         The list will contain duplicates of the receiver if the dialer called the receiver multiple times.
	 */
	@Override
	public List<Long> called(long dialler) {
		// TODO Auto-generated method stub
		List<Long> result = new ArrayList<Long>();
		ValueList<Long> value = dialTree.findReceiver(dialler);
		for (Long l: value) {
			result.add(l);
		}
		return result;
	}

	/**
	 * Tests search 1 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of all the phone numbers called by dialer between start and end time.
	 *         The list will contain duplicates of the receiver if the dialer called the receiver multiple times.
	 */
	@Override
	public List<Long> called(long dialler, LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		List<Long> result = new ArrayList<Long>();
		ValueList<Long> value = dialTree.findReceiver(dialler, startTime, endTime);
		for (Long l: value) {
			result.add(l);
		}
		return result;
	}

	/**
	 * Tests search 2 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param receiver The phone number that received the calls.
	 * @return List of all the phone numbers that called the receiver.
	 *         The list will contain duplicates of the caller if they called the receiver multiple times.
	 */
	@Override
	public List<Long> callers(long receiver) {
		// TODO Auto-generated method stub
		List<Long> result = new ArrayList<Long>();
		ValueList<Long> value = receiveTree.findDialler(receiver);
		for (Long l: value) {
			result.add(l);
		}
		return result;
	}

	/**
	 * Tests search 2 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param receiver The phone number that received the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of all the phone numbers that called the receiver between start and end time.
	 *         The list will contain duplicates of the caller if they called the receiver multiple times.
	 */
	@Override
	public List<Long> callers(long receiver, LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		List<Long> result = new ArrayList<Long>();
		ValueList<Long> value = receiveTree.findDialler(receiver, startTime, endTime);
		for (Long l: value) {
			result.add(l);
		}
		return result;
	}

	/**
	 * Tests search 3 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @return The list of identifiers of the faulty switches or an empty list if no fault was found.
	 */
	@Override
	public List<Integer> findConnectionFault(long dialler) {
		// TODO Auto-generated method stub
		List<Integer> result = new ArrayList<Integer>();
		ValueList<Integer> value = dialTree.findConnectionFault(dialler);
		for (int l: value) {
			result.add(l);
		}
		return result;
	}

	/**
	 * Tests search 3 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The list of identifiers of the faulty switches;
	 *         or an empty list if no fault was found between start and end time.
	 */
	@Override
	public List<Integer> findConnectionFault(long dialler, LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		List<Integer> result = new ArrayList<Integer>();
		ValueList<Integer> value = dialTree.findConnectionFault(dialler, startTime, endTime);
		for (int l: value) {
			result.add(l);
		}
		return result;
	}

	/**
	 * Tests search 3 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param reciever The phone number that should have received the calls.
	 * @return The list of identifiers of the faulty switches or an empty list if no fault was found.
	 */
	@Override
	public List<Integer> findReceivingFault(long reciever) {
		// TODO Auto-generated method stub
		List<Integer> result = new ArrayList<Integer>();
		ValueList<Integer> value = receiveTree.findReceivingFault(reciever);
		for (int l: value) {
			result.add(l);
		}
		return result;
	}

	/**
	 * Tests search 3 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param reciever The phone number that should have received the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The list of identifiers of the faulty switches;
	 *         or an empty list if no fault was found between start and end time.
	 */
	@Override
	public List<Integer> findReceivingFault(long reciever, LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		List<Integer> result = new ArrayList<Integer>();
		ValueList<Integer> value = receiveTree.findReceivingFault(reciever, startTime, endTime);
		for (int l: value) {
			result.add(l);
		}
		return result;
	}

	/**
	 * Tests search 4 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @return The identifier of the switch that had the most connections.
	 *         If multiple switches have the most connections, the smallest switch identifier is returned.
	 */
	@Override
	public int maxConnections() {
		// TODO Auto-generated method stub
		return connectTree.maxConnections();
	}

	/**
	 * Tests search 4 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The identifier of the switch that had the most connections between start and end time.
	 *         If multiple switches have the most connections, the smallest switch identifier is returned.
	 */
	@Override
	public int maxConnections(LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		return connectTree.maxConnections(startTime, endTime);
	}

	/**
	 * Tests search 5 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @return The identifier of the switch that had the fewest connections.
	 *         If multiple switches have the fewest connections, the smallest switch identifier is returned.
	 */
	@Override
	public int minConnections() {
		// TODO Auto-generated method stub
		return connectTree.minConnections();
	}

	/**
	 * Tests search 5 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n)
	 * 
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The identifier of the switch that had the fewest connections between start and end time.
	 *         If multiple switches have the fewest connections, the smallest switch identifier is returned.
	 */
	@Override
	public int minConnections(LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub
		return connectTree.minConnections(startTime, endTime);
	}

	/**
	 * Tests search 6 from the assignment specification.
	 * 
	 * Runtime efficiency: O(n^2)
	 * 
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of details of all calls made between start and end time.
	 */
	@Override
	public List<CallRecord> callsMade(LocalDateTime startTime, LocalDateTime endTime) {
		// TODO Auto-generated method stub		
		List<CallRecord> result = new ArrayList<CallRecord>();
		ValueList<CallRecord> value = timeTree.callsMade(startTime, endTime);
		for (CallRecord l: value) {
			result.add(l);
		}
		return result;
	}
	
	public static void main(String[] args) {
		AutoTester test = new AutoTester();
		
		System.out.println("AutoTester Stub");
	}
}