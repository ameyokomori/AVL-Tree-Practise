package au.edu.uq.itee.comp3506.assn2.entities;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class TimeExampleUsage {

	public static void main(String[] args) {
		LocalDateTime time = LocalDateTime.parse("2017-02-15T10:49:06.193");
		
		System.out.println("Local Date Time is: " + time);
		System.out.println("Year is: " + time.getYear());
		System.out.println("Month is: " + time.getMonthValue());
		System.out.println("Day is: " + time.getDayOfMonth());
		System.out.println("Day is: " + time.getDayOfYear());
		System.out.println("Hour is: " + time.getHour());
		System.out.println("Minute is: " + time.getMinute());
		System.out.println("Second is: " + time.getSecond());
		System.out.println("Nano is: " + time.getNano());
	}
}