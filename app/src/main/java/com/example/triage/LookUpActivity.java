package com.example.triage;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LookUpActivity extends Activity {

	public static final String FILENAME = "patient_records.txt";
	public static final String TAG = "MainActivity";

	private PatientManager patientManager;
	EditText et_hcn;
	String username;
	String usertype;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_look_up);

		et_hcn = (EditText) findViewById(R.id.uvt_et_age);
		et_hcn.setText("111111");

		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
		// load patients onto the PatientManager object
		//placed in MainActivity instead
/*		try {
			patientManager = new PatientManager(this.getApplicationContext()
					.getFilesDir(), FILENAME);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		patientManager = (PatientManager) intent
				.getSerializableExtra("patientManager");
		
		Log.i(TAG, "username is " + username);
		Log.i(TAG, "usertype is " + usertype);
		// Log.i(TAG, patientManager.getPatientList().get(0).toString());
		// Log.i(TAG, patientManager.getPatientList().get(33).toString());
		// Log.i(TAG, patientManager.getPatientMap().get(987694).toString());
		Log.i(TAG, "END OF ONCREATE");

	}

	/**
	 * responds to the "Look Up" button
	 * 
	 */
	public void lookUp(View view) {
		// inserted try catch to find error that doesn't work
		try {
			String hcn = et_hcn.getText().toString();
			// get hcn as a number
			Integer patienthcn = Integer.parseInt(hcn);
			// if hcn not a key in patientManager raise error
			// works on emulator but not on phone
			if (!(PatientManager.getPatientManager().getPatientMap().containsKey(patienthcn))) {
				throw new NotValidInputException();
			}
			Intent intent = new Intent(this, PatientProfileActivity.class);
			Intent oldIntent = getIntent();
			
			// intent.putExtra("patientManager", patientManager);

			// health card # to look up
			intent.putExtra("hcn", hcn);
			intent.putExtra("username", username);
			intent.putExtra("usertype", usertype);
			// Log.i(TAG, "END OF lookUp BUTTON")
			startActivity(intent);

		} catch (NumberFormatException e) {
			Context context = getApplicationContext();
			CharSequence text = "input a health card number";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

		} catch (NotValidInputException e) {
			// have a toast pop up that says it not valid input
			Context context = getApplicationContext();
			CharSequence text = "Patient not found";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		} finally {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.look_up, menu);
		return true;
	}

}
