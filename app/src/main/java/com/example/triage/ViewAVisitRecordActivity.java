package com.example.triage;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewAVisitRecordActivity extends ListActivity {
	
	/**
	 * The PatientManager that holds all the patients.
	 */
	private PatientManager patientManager = PatientManager.getPatientManager();
	/**
	 * Name of the page.
	 */
	public static final String TAG = "ViewAVisitRecordActivity";
	/**
	 * A string representation of the patient's health card number.
	 */
	String HCN;
	/**
	 * A patient's health card number.
	 */
	int intHCN;
	/**
	 *  The index to Visit record for the patient whose health card number is HCN.
	 */
	int visitRecordIndex;
	String username;
	String usertype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_avisit_record);
		
		Intent intent = getIntent();
		HCN = intent.getStringExtra("hcn");
		intHCN = Integer.parseInt(HCN);
//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");
		// index of VisitRecords in the patient's collection of VisitRecord		
		visitRecordIndex = intent.getIntExtra("index", 99);
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
		visitRecordIndex = intent.getIntExtra("index", 99);
		HCN = intent.getStringExtra("hcn");
		intHCN = Integer.parseInt(HCN);
		

//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");
		Patient currentPatient = patientManager.getPatient(intHCN);


		String[] VSRecords = null;

		Log.i(TAG, "Current Patient is " + currentPatient);
		Log.i(TAG, "Current Patient VisitRecord is "
				+ currentPatient.getVisitRecords().toString());

		if (currentPatient.getVisitRecords().get(visitRecordIndex).getVSRs() != null) {

			// contains string to populate the list view
			VSRecords = new String[currentPatient.getVisitRecords().get(visitRecordIndex).getVSRSize()];
			int i = 0;
			
			VisitRecord currentVisitRecord = currentPatient.getVisitRecords().get(visitRecordIndex);

			for (VitalSignsRecord VSR : currentVisitRecord.getVSRs()) {
				VSRecords[i] = VSR.getTime();
				i++;
			}

		} 
		
		// current patient does not have a VisitRecord
		else {
			Log.i(TAG,
					"No vital signs record for"
							+ currentPatient.getHealthCardNumber());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,
				VSRecords);
		setListAdapter(adapter);
		Log.i(TAG, "END OF ONRESUME");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_avisit_record, menu);
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
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String recordIndex= "";
		int index;
		
		
		Intent intent = new Intent(this, ViewVitalSignsRecordActivity.class);
		// intent.putExtra("patientManager", patientManager);
		intent.putExtra("hcn", HCN);
		// intent.putExtra("index", String.valueOf(position));
		intent.putExtra("index", position);
		intent.putExtra("visitIndex", visitRecordIndex);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);
	}
}
