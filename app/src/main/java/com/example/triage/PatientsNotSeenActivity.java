package com.example.triage;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class PatientsNotSeenActivity extends ListActivity {
	

	/**
	 * A string representation of the patient's health card number.
	 */
	String HCN;
	/**
	 * A patient's health card number.
	 */
	int intHCN;
	/** 
	 * The patientManageer that hold all the patients in the hospital.
	 */
	private PatientManager patientManager;
	
	/**
	 * List of the patients in PatientManager
	 */
	List<Patient> patients;
	
	/**
	 * Name of the activity page.
	 */
	public static final String TAG = "PatientsNotSeenActivity";

	//String username;
	//String usertype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patients_not_seen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patients_not_seen, menu);
		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Intent intent = getIntent();
		HCN = intent.getStringExtra("hcn");
		intHCN = Integer.parseInt(HCN);
		patientManager = (PatientManager) intent
				.getSerializableExtra("patientManager");
		patients = patientManager.getPatientList();
		List<Patient> patientsNotSeen = new ArrayList<Patient>();
		
		String[] orderedList = null;
		int number = 0;
		for (int i = 0; i < patientManager.getSize(); i++){
			if (patients.get(i).isSeenByDoctor()==false){
				number += 1;
				patientsNotSeen.add(patients.get(i));
			}
		}
		orderedList = new String[number];

		for (int i = 0; i < orderedList.length; i++){
			orderedList[i] = patientsNotSeen.get(i).getFirstName() + " " + patientsNotSeen.get(i).getLastName();
		}
		
//
//		Log.i(TAG, "Current Patient is " + currentPatient);
//		Log.i(TAG, "Current Patient VisitRecord is "
//				+ currentPatient.getVisitRecords().toString());


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,
				orderedList);
		setListAdapter(adapter);
		Log.i(TAG, "END OF ONRESUME");
	}

}
