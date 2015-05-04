package com.example.triage;
// class just contains constants that are needed for calculation related to vital signs
/**
 * 
 * Class just contains constants that are needed for calculation related to vital signs.
 *
 */
public class Urgency {

	/**
	 * Constants for hospital policy for categorizing urgency
	 * and calculating urgency score in VitalSignsRecord.
	 */
	
	/**
	 * Patient priority is increased if heart rate below this number.
	 */
	public static final double MIN_HEART_RATE = 50;
	
	/**
	 * Patient priority is increased if heart rate above this number.
	 */
	public static final double MAX_HEART_RATE = 100;

	/**
	 * Patient priority is increased if age is below this number.
	 */
	public static final int AGE = 2;
	
	/**
	 * Patient priority is increased if systolic blood pressure exceeds this number.
	 */
	public static final double SYSTOLIC_BLOOD_PRESSURE = 140;
	
	/**
	 * Patient priority is increased if diastolic blood pressure exceeds this number.
	 */
	public static final double DIASTOLIC_BLOOD_PRESSURE = 90;
	
	/**
	 * Patient priority is increased if temperature exceeds this number.
	 */
	public static final double TEMPERATURE = 39.0;
	
	// hurdle points for categorizing patients according to urgency score
	// urgent = 3 to 4
	/**
	 * Patients are considered Urgent if their urgency score exceeds this number.
	 */
	public static final int URGENT = 3;

	// less urgent = 2
	/**
	 * Patients are considered Less Urgent if their urgency score is this number.
	 */
	public static final int LESS_URGENT = 2;

	// non urgent = 1
	/**
	 * Patients are considered Non-Urgent of their urgency score is this number or below.
	 */
	public static final int NON_URGENT = 1;


	
//	public void setAgePolicy() {
//		
//	}
//	
//	public void setMinHeartRatePolicy() {
//		
//	}
//
//	public void setMaxHeartRatePolicy() {
//		
//	}
//	public void setSBPPolicy() {
//		
//	}
//
//	public void setDBPPolicy() {
//		
//	}
//
//	public void setUrgentPolicy() {
//		
//	}
//
//	public void setLessUrgentPolicy() {
//		
//	}
//
//	public void setNotUrgentPolicy() {
//		
//	}

	
}
