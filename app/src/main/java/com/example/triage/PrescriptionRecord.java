package com.example.triage;

/**
 * A class representation of prescriptions records of the patient.
 * Prescriptions can only be written by physicians.
 */
public class PrescriptionRecord extends Record{
	
	/**
	 * Serial ID of prescription record.
	 */
	private static final long serialVersionUID = -6215379525465228030L;

	/**
	 * Medication the patient is taking.
	 */
	private String medication = null;
	
	/**
	 * Instructions for the medication the patient is taking.
	 */
	private String medicationinstructions = null;
	
	/**
	 * Creates a prescription record.
	 * @param med Name of the medication prescribed to the patient.
	 * @param medicationinstructs the instructions for the medication prescribed to the patient.
	 */
	public PrescriptionRecord(String med, String medicationinstructs){
		super();
		medication = med;
		medicationinstructions = medicationinstructs;
	}
	
	/**
	 * Returns the name of the medication the patient is taking.
	 * @return Name of the medication for the patient. 
	 */
	public String getMedication(){
		return medication;
	}
	
	/**
	 * Returns the instructions of the medication the patient is taking.
	 * @return the instructions of the medication the patient is taking.
	 */
	public String getMedicationInstructions(){
		return medicationinstructions;
	}
	
	/**
	 * Changes the name of the medication prescribed to the patient.
	 * @param newmedication new name of the medication.
	 */
	public void setMedication(String newmedication){
		medication = newmedication;
	}
	
	/**
	 * Changes the instructions of the medication prescribed to the patient.
	 * @param newmedicationinstructs The new instructions of the medication prescribed to the patient.
	 */
	public void setMedicationInstructions(String newmedicationinstructs){
		medicationinstructions = newmedicationinstructs;
	}
	
	/**
	 * Returns a string representation of the prescription record with medication, 
	 * instructions and time it was created. 
	 */
	@Override
	public String toString() {
		return "PrescriptionRecord [medication=" + getMedication() + ", instructions=" + getMedicationInstructions() + ", recordTime=" + recordTime + "]";
	}
	
	public String item() {
		return "PrescriptionRecord \n" + recordTime + "\n" + "Medication:\n" + getMedication() + "\n" + "Instructions:\n" + getMedicationInstructions();
	}
}