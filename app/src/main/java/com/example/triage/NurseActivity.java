package com.example.triage;

import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NurseActivity extends Activity {
	
	public static final String FILENAME = "patient_records.txt";
	
	String username;
	String usertype;
	String HCN;
	int intHCN;

	private PatientManager patientManager = PatientManager.getPatientManager();
	public static final String TAG = "NurseActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse);
		


		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");

//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");

		// Log.i(TAG, patientManager.getPatientList().get(0).toString());
		// Log.i(TAG, patientManager.getPatientList().get(33).toString());
		// Log.i(TAG, patientManager.getPatientMap().get(987694).toString());
		Log.i(TAG, "END OF ONCREATE");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nurse, menu);
		return true;
	}
	public void navigateToLookUp(View view) {
		Intent intent = new Intent(this, LookUpActivity.class);
		// intent.putExtra("patientManager", patientManager);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);
	}

	public void createPatientRecord(View view){
		Intent intent = new Intent(this, CreatePatientRecord.class);
		//intent.putExtra("patientManager", patientManager);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);
		
	}
	
	public void goToPatientList(View view){
		Intent intent = new Intent(this, AccessUrgentPatientListActivity.class);
		// intent.putExtra("patientManager", patientManager);
		intent.putExtra("hcn", HCN);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);
		
	}
	
}
