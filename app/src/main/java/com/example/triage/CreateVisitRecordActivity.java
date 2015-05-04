package com.example.triage;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity takes inputs for temperature, age, diastolic blood pressure,
 * systolic blood pressure and heart rate from the user and creates a new visit
 * record for the patient.
 * 
 * 
 */
public class CreateVisitRecordActivity extends Activity {

	private PatientManager patientManager = PatientManager.getPatientManager();
	public static final String TAG = "CreateVisitRecord";

	// The patient's health card number
	String HCN;
	int intHCN;
	String username;
	String usertype;
	// temperature
	EditText et_newTemp;
	// age
	TextView tv_Age;
	// disastolic
	EditText et_dbp;
	// systolic
	EditText et_sbp;
	// heart rate
	EditText et_hr;
	// save button
	Button save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_visit_record);

		et_newTemp = (EditText) findViewById(R.id.et_temp);
		tv_Age = (TextView) findViewById(R.id.cpr_tv_age);
		et_dbp = (EditText) findViewById(R.id.et_dbp);
		et_sbp = (EditText) findViewById(R.id.et_sbp);
		et_hr = (EditText) findViewById(R.id.et_hr);

		// et_newTemp.setText("38");
		
		// et_dbp.setText("100");
		// et_sbp.setText("100");
		// et_hr.setText("101");

		Intent intent = getIntent();
		HCN = intent.getStringExtra("hcn");
		intHCN = Integer.parseInt(HCN);
//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
		
		 tv_Age.setText(Integer.toString(patientManager.getPatient(intHCN).getAge()));
		
		Log.i(TAG, "END OF ONCREATE");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_visit_record, menu);
		return true;
	}

	/**
	 * Responds to click on the "Create a New Visit Record" button and save teh visit
	 * record in the patient stored in the PatientManager object.
	 * 
	 */
	public void CreateVisitRecord(View view) {

		try{
			String stemp = et_newTemp.getText().toString();
			String sdbp = et_dbp.getText().toString();
			String ssbp = et_dbp.getText().toString();
			String shr = et_hr.getText().toString();
			
			if (stemp == null && sdbp == null && ssbp == null && shr == null){
				throw new NotValidInputException();
			}
			
			double temp = Double.parseDouble(stemp);
			double dbp = Double.parseDouble(sdbp);
			double sbp = Double.parseDouble(ssbp); //These were both dbp
			double hr = Double.parseDouble(shr);
			
			Patient p = patientManager.getPatient(intHCN);
			Log.i(TAG, "The Patient HCN is " + p.getHealthCardNumber());
		
			
			/*
			 * a Patient object has a collectino of VisitRecord.
			 * a VisitRecord object has a collection of VitalSigns
			 * a VistalSignsRecord has vital signs
			 */
			VisitRecord visitRecord = new VisitRecord();
			
			
			VitalSignsRecord newVS = new VitalSignsRecord(p.getAge(), temp, sbp, dbp, hr, visitRecord.getTime());
			//vr.setVitalSigns(newVS);
			visitRecord.addVSR(newVS);

			p.addVisitRecord(visitRecord);
			p.setSeenByDoctor(false);

			ArrayList<VisitRecord> test = (ArrayList<VisitRecord>) p
					.getVisitRecords();

			Log.i(TAG, test.get(0).getTime());
			Log.i(TAG, "The newly created VisitRecord is " + String.valueOf(p.getVisitRecords().toString()));
			//Log.i(TAG, test.get(0).getVitalSigns().toString());
			Log.i(TAG, "END OF CreateVisitRecord");

			Intent intent = new Intent(this, PatientProfileActivity.class);
			// intent.putExtra("patientManager", patientManager);
			intent.putExtra("hcn", HCN);
			intent.putExtra("usertype", usertype);
			intent.putExtra("username", username);
			startActivity(intent);

			
		} catch(NumberFormatException e){
			Context context = getApplicationContext();
			CharSequence text = "Input a valid number";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
		}
		catch(NotValidInputException e){
			Context context = getApplicationContext();
			CharSequence text = "Input some values";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		} 
		finally{}

	}

}
