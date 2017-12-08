package au.edu.uq.itee.comp3506.assn2.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CallRecordExampleUsage {

	public static void main(String[] args) {
		LocalDateTime time = LocalDateTime.parse("2017-02-15T10:49:06.193");
		List<Integer> path = new ArrayList<>();
		path.add(1);
		path.add(10);
		path.add(100);
		path.add(1000);
		path.add(10000);
		
		CallRecord callRec = new CallRecord(1234567890L, 9876543210L, 1, 10000, path, time);
		System.out.println("The call record is: " + callRec);
		System.out.println("Has switch 10: " + callRec.hasSwitch(10));
		System.out.println("Does not have switch 11: " + callRec.hasSwitch(11));
	}
}