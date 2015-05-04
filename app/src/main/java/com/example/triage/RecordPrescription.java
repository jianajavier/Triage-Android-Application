package com.example.triage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecordPrescription extends Activity {
	
	/**
	 * The PatientManager that holds all the patients.
	 */
	private PatientManager patientManager = PatientManager.getPatientManager();
	/**
	 * Name of the page.
	 */
	public static final String TAG = "RecordPrescription";
	/**
	 * A string representation of the patient's health card number.
	 */
	String HCN;
	/**
	 * A patient's health card number.
	 */
	int intHCN;
	/**
	 *  The index to Prescription record for the patient whose health card number is HCN.
	 */
	EditText et_medication;
	EditText et_medicationinstructions;
	Button save;

	String username;
	String usertype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_prescription);
		
		File dir = new File(getFilesDir() + "/prescription_records");
		dir.mkdir();

		et_medication = (EditText) findViewById(R.id.et_medication);
		et_medicationinstructions = (EditText) findViewById(R.id.et_med_instructions);
		
		Intent intent = getIntent();
		HCN = intent.getStringExtra("hcn");
		intHCN = Integer.parseInt(HCN);
//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");
		
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");

		Log.i(TAG, "END OF ONCREATE");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record_prescription, menu);
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
	/**
	 * Responds to click on the "Create a New Visit Record" button and save teh visit
	 * record in the patient stored in the PatientManager object.
	 * 
	 */
	public void CreatePrescriptionRecord(View view) {
		
		try{
			String smedication = et_medication.getText().toString();
			String smedicationinstructions = et_medicationinstructions.getText().toString();
			
			if (smedication == null && smedicationinstructions == null){
				throw new NotValidInputException();
			}
			
			String medication = smedication;
			String medicationinstructions = smedicationinstructions;
			
			Patient p = patientManager.getPatient(intHCN);
			Log.i(TAG, "The Patient HCN is " + p.getHealthCardNumber());
		
			PrescriptionRecord pr = new PrescriptionRecord(medication, medicationinstructions);

			p.addPrescriptionRecord(pr);

			ArrayList<PrescriptionRecord> test = (ArrayList<PrescriptionRecord>) p
					.getPrescriptionRecords();

			Log.i(TAG, test.get(0).getTime());
			Log.i(TAG, test.get(0).toString());
			Log.i(TAG, "made pr");
			
			//need to save to file
			savePRToFiles(view);
			
			Log.i(TAG, "END OF RecordPrescription");
			

			Intent intent = new Intent(this, PatientProfileActivity.class);
			// intent.putExtra("patientManager", patientManager);
			intent.putExtra("hcn", HCN);
			intent.putExtra("username", username);
			intent.putExtra("usertype", usertype);
			startActivity(intent);

						
		}
		catch(NotValidInputException e){
			Context context = getApplicationContext();
			CharSequence text = "Please fill out the page";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		 catch (IOException e) {
		  e.printStackTrace();
		  }
		
		finally{}

	}

	/**
	 * Saves the PrescriptionRecords to a file according to each patient's HCN.
	 * 
	 * @param view
	 */
	
	public void savePRToFiles(View view) throws IOException {
		Context context = getApplicationContext();
		String textToSave;
		PrescriptionRecord prescription;
		Patient p = patientManager.getPatient(intHCN);
		List<PrescriptionRecord> PR_list = p.getPrescriptionRecords();
		BufferedWriter bs;
		File file = new File(getFilesDir()+"/prescription_records",HCN);
		
		try {
			if (!file.exists()){
				file.createNewFile();
			}
			
			bs = new BufferedWriter(new FileWriter(file));
			
		  for (int i=0; i<PR_list.size();i++){
				prescription = PR_list.get(i);
				textToSave = PR_list.get(i).recordTime + "@" + prescription.getMedication()+ "@"+ prescription.getMedicationInstructions(); 
				//format: time$$$medication$$$medicationinstructions
				bs.write(textToSave);
				bs.write("\n");
		  }
		  bs.close();
		} catch (Exception e) {
			  e.printStackTrace();
			}
		
		CharSequence text = "File Saved.";
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
	}
}




