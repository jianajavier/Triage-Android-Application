package com.example.triage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// MAKE IT SERIALIAZABLE TO SO IT CAN BE PASSED FROM ONE ACTIVITY TO ANOTHER
/**
 * The class PatientManager holds all the Patient object made for the hospital.
 * 
 */
public class PatientManager implements Serializable {

	private static final PatientManager patientManager = new PatientManager();

	public static final String FILENAME = "patient_records.txt";
	
	/**
	 * The serialization ID for the PatientManager so it can be passed around.
	 */
	private static final long serialVersionUID = 2715448915207696794L;
	// from the main activity

	/**
	 * A list of all the patients in the hospital.
	 */
	private List<Patient> patientsList = new ArrayList<Patient>();;

	/**
	 * Size of patientManager
	 */
	private int size;

	/**
	 * A map that maps a patient's health card number to the Patient.
	 * 
	 * @param <Integer>
	 *            An number(integer), hopefully a patient's health card number.
	 * @param <Patient>
	 *            A patient.
	 */
	private Map<Integer, Patient> patientsMap = new HashMap<Integer, Patient>();

	/**
	 * Creates a PatientManager using a file.
	 * 
	 * @param dir
	 *            The directory PatientManager will be stored in.
	 * @param fileName
	 *            The name the file will be called.
	 * 
	 */
	public PatientManager(File dir, String fileName) throws IOException {

		File file = new File(dir, fileName);
		size = 0;
		
		if (file.exists()) {
			populate(file.getPath());
		} else {
			file.createNewFile();
		}
		getPatientManager().patientsList = patientsList;
		getPatientManager().patientsMap = patientsMap;
		
	}

	private PatientManager() {


	}

	public static PatientManager getPatientManager() {
		return patientManager;
	}
	
	/**
	 * Populates the PatientManager with patients based on the file.
	 * 
	 * @param filePath
	 *            The filePath
	 * 
	 */
	private void populate(String filePath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(filePath));
		String[] record;
		while (scanner.hasNextLine()) {
			record = scanner.nextLine().split(",");
			// 452133,Vijay Vazirani,1994-03-12
			String HCN = record[0];
			String[] name = record[1].split(" ");
			String dob = record[2];
			patientsList.add(new Patient(Integer.parseInt(HCN), name[0], name[1],
					dob));
			Patient newPatient = new Patient(Integer.parseInt(HCN), name[0],
					name[1], dob);
			patientsMap.put(newPatient.getHealthCardNumber(), newPatient);
			size += 1;
		}
		scanner.close();

	}

	/**
	 * Returns a string of the patients in the PatientManager.
	 * 
	 * @return A string of the patients in the PatientManager.
	 */
	@Override
	public String toString() {
		return "PatientManager [patients=" + patientsList + "]";
	}

	/**
	 * Adds a Patient to the PatientManager.
	 * 
	 * @param patient
	 *            A Patient.
	 * 
	 */
	public void add(Patient patient) {
		patientsList.add(patient);

		patientsMap.put(patient.getHealthCardNumber(), patient);
		size += 1;
	}

	/**
	 * Returns a list of the patients in the PatientManager.
	 * 
	 * @return A list of the patients in the PatientManager.
	 * 
	 */
	public List<Patient> getPatientList() {
		return patientsList;
	}

	/**
	 * Returns A map mapping patient health card numbers to the patients in the
	 * PatientManager.
	 * 
	 * @return A map mapping patient health card numbers to the patients in the
	 *         PatientManager.
	 * 
	 */
	public Map<Integer, Patient> getPatientMap() {
		return patientsMap;
	}

	/**
	 * Returns the patient with the health card number intHCN.
	 * 
	 * @param intHCN
	 *            a patient's health card number.
	 * @return The patient with the health card number asked for.
	 * 
	 */
	public Patient getPatient(int intHCN) {

		return getPatientMap().get(intHCN);
	}

	/**
	 * Returns the size of the patient list.
	 * 
	 * @return Number of patients in patientManager
	 * 
	 */
	public int getSize() {

		return size;
	}

}
