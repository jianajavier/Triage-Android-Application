package com.example.triage;

import java.io.Serializable;
import java.util.Date;

import android.text.format.Time;
/**
 * 
 * An abstract class that is the parent class of VitalSignsRecord.
 * Records are seriablizable and have the time they were made.
 *
 */
public abstract class Record implements Serializable{


	/**
	 * The time the Record was made.
	 */
	public String recordTime;
	
	/**
	 * Constructor for Record object, 
	 * although Record will not be instantiated
	 * only it's subclasses
	 * took the time constructor from Visit Record
	 * @author brianna
	 */
	public Record(){
		Time now = new Time();
		now.setToNow();

		Date date = new Date();
		// Returns the number of milliseconds that have elapsed since January 1,
		// 1970.
		long milliSeconds = date.getTime();
		

		String year = String.valueOf(now.year);
		String month = String.valueOf(now.month);
		String day = String.valueOf(now.monthDay);
		String hour = String.valueOf(now.hour);
		String minute = String.valueOf(now.minute);

		
		
		recordTime = year + "-" + month + "-" + day + "-" + hour + ":" + minute;
	};
	
	/**
	 * Returns the time the record was made.
	 * @return The time the file was made.
	 */
	public String getTime() {
		return recordTime;
	}
}
