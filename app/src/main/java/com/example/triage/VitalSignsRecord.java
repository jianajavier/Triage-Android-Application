package com.example.triage;

import java.io.Serializable;
/**
	 * The class VitalSignsRecord holds the information 
	 * of the patient's vital signs recorded at a certain time.
	 * It is serializable.
	 */
public class VitalSignsRecord extends Record implements Serializable{

	/**
	 * The serialization id of VitalSignsRecord.
	 */
	private static final long serialVersionUID = -3661184257701988831L;
	
	/**
	 * The patient's age at the time of recording.
	 */
	private int age;
	
	/**
	 * The patient's temperature at the time of recording.
	 */
	private double temperature;
	
	/**
	 * The patient's systolic blood pressure at the time of recording.
	 */
	private double systolicBloodPressure;
	
	/**
	 * The patient's diastolic blood pressure at the time of recording.
	 */
	private double diastolicBloodPressure;
	
	/** 
	 * the patient's heart rate at the time of recording. 
	 */
	private double heartRate;


	/**
	 * Time the Vital Signs Record was recorded
	 */
	private String time;
	
	/**
	 * Creates a VitalSignsRecord using the age, temperature, systolic blood pressure,
	 * diastolic blood pressure, and heart rate given.
	 * @param a The age of the patient.
	 * @param t The temperature of the patient.
	 * @param sbp The systolic blood pressure of the patient.
	 * @param dbp the diastolic blood pressure of the patient.
	 * @param hr The heart rate of the patient.
	 * @param rt The time the new vital signs record was recorded
	 */
	public VitalSignsRecord(int a, double t, double sbp, double dbp, double hr, String arrivalTime) {
	
		// should record time stays with visit record?
		time = arrivalTime;
	
		age = a;
		temperature = t;
		systolicBloodPressure = sbp;
		diastolicBloodPressure = dbp;
		heartRate = hr;
	
	
		urgencyScore = calculateUrgencyScore();
	}

	/**
	 * The urgency score of the patient calculated based on the vital signs of the patient at a certain time.
	 */
	private int urgencyScore;

	/**
	 * Returns the age of the patient at a certain time.
	 * @return The age of the patient.
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Returns the temperature of the patient at a certain time. 
	 * @return Temperature of the patient at a certain time.
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * Return the systolic blood pressure of the patient at a certain time.
	 * @return The systolic blood pressure of the patient at a certain time.
	 */
	public double getSystolicBloodPressure() {
		return systolicBloodPressure;
	}

	/**
	 * Return the diastolic blood pressure of the patient at a certain time.
	 * @return The diastolic blood pressure of the patient at a certain time.
	 */
	public double getDiastolicBloodPressure() {
		return diastolicBloodPressure;
	}

	/**
	 * Return the heart rate of the patient at a certain time.
	 * @return The heart rate of the patient at a certain time.
	 */
	public double getHeartRate() {
		return heartRate;
	}

	/**
	 * Return the urgency score of the patient at a certain time.
	 * @return The urgency score the patient at a certain time.
	 */
	public int getUrgencyScore() {
		return urgencyScore;
	}
	
	public String getTime(){
		return time;
	}

	/**
	 * Changes the age of the patient recorded in the visit record.
	 * Also recalculates the urgency score based on the new vital signs.
	 * @param newAge The new age of the patient.
	 */
	public void setAge(int newAge) {
		age = newAge;

		// update urgencyScore to reflect the change
		urgencyScore = calculateUrgencyScore();
	}

	/**
	 * Changes the Temperature of the patient recorded in the visit record. 
	 * Also recalculates the urgency score based on the new vital signs. 
	 * @param temperature2 The new temperature of the patient.
	 */
	public void setTemperature(double temperature2) {
		temperature = temperature2;
		// update urgencyScore to reflect the change
		urgencyScore = calculateUrgencyScore();
	}

	/**
	 * Changes the urgency score.
	 * @param urgencyScore The new urgency score of the patient.
	 */
	public void setUrgencyScore(int urgencyScore) {
		this.urgencyScore = urgencyScore;
	}

	/**
	 * Changes the systolic blood pressure of the patient recorded in the visit record. 
	 * Also recalculates the urgency score based on the new vital signs. 
	 * @param sbp The new systolic blood pressure of the patient.
	 */
	public void setSystolicBloodPressure(double sbp) {
		systolicBloodPressure = sbp;

		// update urgencyScore to reflect the change
		urgencyScore = calculateUrgencyScore();

	}

	/**
	 * Changes the diastolic blood pressure of the patient recorded in the visit record. 
	 * Also recalculates the urgency score based on the new vital signs. 
	 * @param dbp The new diastolic blood pressure of the patient.
	 */
	public void setDiastolicBloodPressure(double dbp) {
		diastolicBloodPressure = dbp;
		// update urgencyScore to reflect the change
		urgencyScore = calculateUrgencyScore();

	}

	/**
	 * Changes the heart rate of the patient recorded in the visit record. 
	 * Also recalculates the urgency score based on the new vital signs. 
	 * @param hr The new heart rate of the patient.
	 */
	public void setHeartRate(double hr) {
		heartRate = hr;
		// update urgencyScore to reflect the change
		urgencyScore = calculateUrgencyScore();

	}

	/**
	 * Returns a string with the visit record information.
	 */
	@Override
	public String toString() {
		return "VitalSignsRecord [age=" + age + ", temperature=" + temperature
				+ ", systolicBloodPressure=" + systolicBloodPressure
				+ ", diastolicBloodPressure=" + diastolicBloodPressure
				+ ", heartRate=" + heartRate + ", urgencyScore=" + urgencyScore
				+ "]";
	}

	/**
	 * Calculates urgencyScore according to the hospital policy for categorizing
	 * urgency.
	 * 
	 * @return The urgency score of the patient according to their vital signs.
	 */
	public int calculateUrgencyScore() {
		urgencyScore = 0;

		if (age < Urgency.AGE) {
			urgencyScore++;
		}

		if (temperature >= Urgency.TEMPERATURE) {
			urgencyScore++;
		}

		if (systolicBloodPressure >= Urgency.SYSTOLIC_BLOOD_PRESSURE) {
			urgencyScore++;
		}

		if (diastolicBloodPressure >= Urgency.DIASTOLIC_BLOOD_PRESSURE) {
			urgencyScore++;
		}

		if (heartRate <= Urgency.MIN_HEART_RATE
				|| heartRate >= Urgency.MAX_HEART_RATE) {
			urgencyScore++;
		}

		return urgencyScore;
	}
}
