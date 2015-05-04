package com.example.triage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.text.format.Time;

/**
 * A record of a patient's visit to the hospital.
 * A visit record is serializable.
 * 
 */
public class VisitRecord extends Record implements Serializable {

	// Collection of VitalSigns
	/**
	 * A collection of all the vital signs of the patient during their visit.
	 */
	VitalSignsRecord vitalSigns;
	String timeSeen;
	
	/**
	 * List of all Vital Signs Records recorded in that one visit. 
	 */
	List<VitalSignsRecord> vsRecords;

	// CONSTRUCTOR
	/**
	 * Creates a visit record with no initial vital signs for the patient. 
	 * Has a date/time it was made.  
	 */
	public VisitRecord() {
		// vitalSigns = new VitalSignsRecord();
		vitalSigns = null;
		Time now = new Time();
		now.setToNow();

		Date date = new Date();
		// Returns the number of milliseconds that have elapsed since January 1,
		// 1970.
		long milliSeconds = date.getTime();
		timeSeen = new String();

		String year = String.valueOf(now.year);
		String month = String.valueOf(now.month);
		String day = String.valueOf(now.monthDay);
		String hour = String.valueOf(now.hour);
		String minute = String.valueOf(now.minute);
		
		recordTime = year + "-" + month + "-" + day + "-" + hour + ":" + minute;
		
		vsRecords = new ArrayList<VitalSignsRecord>();
	}

	/**
	 * Creates a visit record given initial vital signs for the patient. 
	 * Has a date/time it was made.  
	 * @param vs The initial vital signs record.
	 */
	public VisitRecord(VitalSignsRecord vs) {
		vitalSigns = vs;
		vsRecords = new ArrayList<VitalSignsRecord>();
		vsRecords.add(vs);
	}

	/**
	 * Returns a string representation of the visit record with vital signs, 
	 * and time it was created. 
	 */
	@Override
	public String toString() {
		//return "VisitRecord [vitalSigns=" + vitalSigns + ", recordTime=" + recordTime + "]";
		return "VisitRecord [vitalSigns=" + vsRecords + ", recordTime=" + recordTime + "]";
	}

	/**
	 * Returns the vital signs recorded in the visit record.
	 * @return The vital signs recorded in the visit record.
	 */
	public VitalSignsRecord getVitalSigns() {
		return vitalSigns;
	}
	
	/**
	 *  Changes the vital signs recorded in the visit record.
	 * @param vitalSigns The new vital signs of the patient.
	 */
	public void setVitalSigns(VitalSignsRecord vitalSigns) {
		this.vitalSigns = vitalSigns;
	}
	
	public void setTimeSeen(String time){
		this.timeSeen = time;
	}
	
	public String getTimeSeen(){
		return timeSeen;
	}
	
	public void addVSR(VitalSignsRecord vsr){
		vsRecords.add(vsr);
	}
	
	public VitalSignsRecord getVSR(int index){
		return vsRecords.get(index);
	}
	
	public int getVSRSize(){
		return vsRecords.size();
	}
	
	public List<VitalSignsRecord> getVSRs(){
		return vsRecords;
	}
	


}
