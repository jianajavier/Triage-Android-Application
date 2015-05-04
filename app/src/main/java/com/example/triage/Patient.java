package com.example.triage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import android.text.format.Time;

/**
 * The class for Patient. A Patient object represents a person in the hospital. 
 * Stores a patient's information (ie. name, date of birth, a unique health card number,
 * arrival time, and if they've been seen by a doctor.
 * Patients are serializable.
 * 
 */
public class Patient implements Serializable {

	/**
	 * Serialization id of Patient. 
	 */
	private static final long serialVersionUID = -4592940497237624312L;
	
	/**
	 * A collection of the patient's visit records, VisitRecord .
	 * 
	 *
	 */
	private List<VisitRecord> visitRecords;
	
	
	/**
	 * The patient's health card number.
	 */
	private int healthCardNumber;

	/**
	 * The patient's first name.
	 */
	private String firstName;

	/**
	 * The patient's last name. 
	 */
	private String lastName;
	
	/**
	 * The patient's date of birth.
	 *
	 */
	
	private String birthDate;
	
	/**
	 * The patient's arrival time.
	 *
	 */
	
	private String arrivalTime;
	
	/**
	 * Whether or not the patient has been seen by the a doctor.
	 *
	 */
	private boolean seenByDoctor;
	// true if assessed by doctor

	/**
	 * Whether or not the patient is in the emergency room.
	 *
	 */
	private String timeSeen;
	// true if visit record created 
	
	/**
	 * Age of Patient
	 */
	private int age;
	
	/**
	 * A collection of the patient's prescription records, PrescriptionRecord .
	 * 
	 *
	 */
	private List<PrescriptionRecord> prescriptionRecords;
	
	/**
	 * returns the List of VisitRecords for the Patient
	 * @return List of VisitRecord
	 */
	public List<VisitRecord> getVisitRecords() {
		return visitRecords;
	}


	/**
	 * sets the collection of visit records in this patient
	 * @param visitRecords new collection of VisitRecords
	 */
	public void setVisitRecords(List<VisitRecord> visitRecords) {
		this.visitRecords = visitRecords;
	}


	/**
	 * add a visit record to the collection of VisitRecords3
	 * @param vr VisitRecord to add
	 */
	public void addVisitRecord(VisitRecord vr) {
		visitRecords.add(vr);
	}



	/**
	 * Returns a string containing the patient's information in the format  
	 * "health card number, firstName lastName, birthDate".
	 *
	 */
	@Override
	public String toString() {
		return healthCardNumber + ", " + firstName + " " + lastName + ", "
				+ birthDate + ", " + seenByDoctor;
	}
	

	
	/**
	 * Creates a Patient.
	 * @param hNumber The patient's health card number.
	 * @param fName The patient's first name.
	 * @param lName The patient's last name.
	 * @param dob The patient's date of birth.
	 * 
	 */
	public Patient(int hNumber, String fName, String lName, String dob) {
		healthCardNumber = hNumber;
		firstName = fName;
		lastName = lName;
		birthDate = dob;
		//arrivalTime = t; // I don't think arrivalTime should be part of the
							// constructor, it should be part of record
		
		// http://stackoverflow.com/questions/5369682/get-current-time-and-date-on-android
		age = calculateAge();
		
		// initially false
		seenByDoctor = false;
		
		
		visitRecords = new ArrayList<VisitRecord>();
		prescriptionRecords = new ArrayList<PrescriptionRecord>();

	}

	/**
	 * Returns the patient's health card number.
	 * @return The patient's health card number.
	 * 
	 */
	public int getHealthCardNumber() {
		return healthCardNumber;
	}

	/**
	 * Changes the patient's health card number.
	 * @param hNumber the patient's health card number
	 * 
	 */
	public void setHealthCardNumber(int hNumber) {
		this.healthCardNumber = hNumber;
	}

	/**
	 * Returns the patient's first name.
	 * @return The patient's first name.
	 * 
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 *Changes the patient's first name
	 *@param fName The Patient's first name.
	 *
	 */
	public void setFirstName(String fName) {
		this.firstName = fName;
	}
	
	/**
	 * Returns the patient's last name.
	 * @return The patient's last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Changes the patient's last name.
	 * @param lName The patient's last name.
	 * 
	 */
	public void setLastName(String lName) {
		this.lastName = lName;
	}

	/**
	 * Returns the patient's date of birth.
	 * @return The patient's date of birth.
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * Changes the patient's date of birth.
	 * @param dob The patient's date of birth.
	 */
	public void setBirthDate(String dob) {
		this.birthDate = dob;
	}

	/**
	 * Returns the patient's arrival time.
	 * @return The patient's arrival time.
	 * 
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Changes the patient's arrival time
	 * @param aTime The patient's arrival time
	 */
	public void setArrivalTime(String aTime) {
		this.arrivalTime = aTime;
	}
	
	/**
	 * Returns True if the patient has been seen by a doctor and False otherwise
	 * @return Returns if the patient has been seen by a doctor.
	 */
	public boolean isSeenByDoctor() {
		return seenByDoctor;
	}

	/**
	 * Changes whether or not the patient has been seen by a doctor.
	 * @param assessed A boolean stating if the patient has been assessed by a doctor.
	 */
	public void setSeenByDoctor(boolean assessed) {
		this.seenByDoctor = assessed;
	}
	
	public void setTimeSeen(String time){
		this.timeSeen = time;
	}
	
	public String getTimeSeen(){
		return timeSeen;
	}
	
	public int getAge(){
		return age;
	}
	
	/**
	 * returns the List of PrescriptionRecords for the Patient.
	 * @return List of PrescriptionRecord.
	 */
	public List<PrescriptionRecord> getPrescriptionRecords() {
		return prescriptionRecords;
	}

	/**
	 * sets the collection of prescription records in this patient.
	 * @param prescriptionRecords new collection of PrescriptionRecords.
	 */
	public void setPrescriptionRecords(List<PrescriptionRecord> prescriptionrecords) {
		this.prescriptionRecords = prescriptionrecords;
	}


	/**
	 * add a prescription record to the collection of PrescriptionRecords.
	 * @param prescriptionrecord PrescriptionRecord to add.
	 */
	public void addPrescriptionRecord(PrescriptionRecord prescriptionrecord) {
		prescriptionRecords.add(prescriptionrecord);
	}
	
	public int calculateAge(){
		Time now = new Time();
		now.setToNow();

		int year = Integer.valueOf(now.year);
		int month = Integer.valueOf(now.month);
		int day = Integer.valueOf(now.monthDay);

		String[] bd = birthDate.split("-");

		int y = year - Integer.parseInt(bd[0]);

		if (Integer.parseInt(bd[1]) > month){
		if (Integer.parseInt(bd[1]) == month){
		if (Integer.parseInt(bd[2]) > day){
		return y;
		}
		}
		}
		return y-1;

		}


	/**
	 * get the most recent urgency score for the patient
	 * 
	 * @param patient
	 * @return urgency score for the patient or -1 if there is no urgency score
	 */
	public int getUrgencyScore() {


		int nVisitRecords = getVisitRecords().size() - 1;

		// the patient does not have an urgency score
		if (nVisitRecords < 0) {
		return -1;
		}
		// the patient has a visit record, so the patient has an urgency score
		else {
		// int indexVitalSign = getVisitRecords().get(indexVisitRecords)
		int nVitalSigns = getVisitRecords().get(nVisitRecords).getVSRSize() - 1;

		if(nVitalSigns < 0) {
		return -1;
		}

		return getVisitRecords().get(nVisitRecords).getVSR(nVitalSigns).getUrgencyScore();

		}

		}

}