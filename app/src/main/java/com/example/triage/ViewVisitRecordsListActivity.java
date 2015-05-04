package com.example.triage;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * This activity shows a view of all the visit records for a
 * patient.
 * 
 * 
 */
public class ViewVisitRecordsListActivity extends ListActivity {

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
	public static final String TAG = "ViewVisitRecordsActivity";

	String username;
	String usertype;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_visit_records);

		Log.i(TAG, "END OF ONCREATE");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
		HCN = intent.getStringExtra("hcn");
		intHCN = Integer.parseInt(HCN);
		
		//timeSeen = intent.getStringExtra("time");

//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");
		Patient currentPatient = patientManager.getPatient(intHCN);

//		VitalSignsRecord newVS = new VitalSignsRecord(20, 38, 100, 100, 101);
//		VisitRecord vr = new VisitRecord();

//		vr.addVitalSignsRecord(newVS);
//		currentPatient.addVisitRecord(vr);
//		

//		vr.addVitalSignsRecord(newVS);
//		currentPatient.addVisitRecord(vr);

		String[] recordTimes = null;

		Log.i(TAG, "Current Patient is " + currentPatient);
		Log.i(TAG, "Current Patient VisitRecord is "
				+ currentPatient.getVisitRecords().toString());

		if (currentPatient.getVisitRecords() != null) {

			// contains string to populate the list view
			recordTimes = new String[currentPatient.getVisitRecords().size()];
			int i = 0;

			for (VisitRecord eachVR : currentPatient.getVisitRecords()) {
				recordTimes[i] = eachVR.getTime();
				i++;
			}

		} 
		
		// current patient does not have a VisitRecord
		else {
			Log.i(TAG,
					"No visit record for"
							+ currentPatient.getHealthCardNumber());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,
				recordTimes);
		setListAdapter(adapter);
		Log.i(TAG, "END OF ONRESUME");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_visit_records, menu);
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String recordIndex= "";
		int index;
		
		
		Intent intent = new Intent(this, ViewAVisitRecordActivity.class);
//		intent.putExtra("patientManager", patientManager);
		intent.putExtra("hcn", HCN);
		// intent.putExtra("index", String.valueOf(position));
		intent.putExtra("index", position);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);
	}
	

	
	
}
