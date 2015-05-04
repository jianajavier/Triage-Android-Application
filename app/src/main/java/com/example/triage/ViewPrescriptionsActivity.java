package com.example.triage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class ViewPrescriptionsActivity extends ListActivity {
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
	private PatientManager patientManager = PatientManager.getPatientManager();
	/**
	 * Name of the activity page.
	 */
	public static final String TAG = "ViewPrescriptionsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_prescriptions);
		
		File dir = new File(getFilesDir() + "/prescription_records");
		dir.mkdir();
		
		Intent intent = getIntent();
		HCN = intent.getStringExtra("hcn");
		intHCN = Integer.parseInt(HCN);
//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");
		Patient currentPatient = patientManager.getPatient(intHCN);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_prescriptions, menu);
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
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Intent intent = getIntent();

		HCN = intent.getStringExtra("hcn");
		intHCN = Integer.parseInt(HCN);
//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");
		Patient currentPatient = patientManager.getPatient(intHCN);


		///on resume?
		File file = new File(getFilesDir()+"/prescription_records",HCN);
		// use this thing
		try {
			Scanner scanner = new Scanner(new FileInputStream(file));
			String[] record;
			ArrayList<String> allprescriptionrecords = new ArrayList<String>();
			while (scanner.hasNextLine()) {
				record = scanner.nextLine().split("@");
				// time$$$drug$$$once a day with meals\n
				String time = record[0];
				String medication = record[1];
				String medinstructions = record[2];
				String medicationprescription = "Prescription Record:\n" + time + "\nMedication:\n" + medication + "\nInstructions:\n" + medinstructions; 
				allprescriptionrecords.add(medicationprescription);
			}
			scanner.close();
		
			// contains string to populate the list view
			String[] recordTimes = new String[allprescriptionrecords.size()];
			int i = 0;

			for (String eachPR : allprescriptionrecords) {
				recordTimes[i] = eachPR;
				i++;
			}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,
				recordTimes);
		setListAdapter(adapter);
		Log.i(TAG, "END OF ONRESUME");
		} catch(Exception e){
			e.printStackTrace();}
		}
}
