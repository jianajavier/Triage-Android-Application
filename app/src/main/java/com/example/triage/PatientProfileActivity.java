package com.example.triage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity is responsible for displaying patient's personal information
 * This activity offers a button for creating a new visit record This activity
 * offers a button for viewing visit records after which you can edit the vital
 * signs in that visit record
 */
public class PatientProfileActivity extends Activity {


	Button viewVisitRecords;
	Button makeNewVisitRecord;
	Button save;
	Button recordSeenByDoctor;
	Button recordPrescription;
	TextView patientName;
	TextView patientHCN;
	TextView patientBOD;
	String username;
	String usertype;
	FileOutputStream outputStream;


	String HCN;
	int intHCN;

	private boolean load = false;

	private PatientManager patientManager = PatientManager.getPatientManager();

	public static final String TAG = "PatientProfileActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patient_profile);

		File dir = new File(getFilesDir() + "/visit_records");
		dir.mkdir();
		viewVisitRecords = (Button) findViewById(R.id.pp_b_viewVisitRecords);
		makeNewVisitRecord = (Button) findViewById(R.id.pp_b_createVisitRecord);
		patientName = (TextView) findViewById(R.id.tv_patientName);
		patientHCN = (TextView) findViewById(R.id.tv_patientHCN);
		patientBOD = (TextView) findViewById(R.id.tv_patientBod);
		save = (Button) findViewById(R.id.pp_b_save);
		// recordSeenByDoctor = (Button) findViewById(R.id.pp_b_recordDateTime);
		recordPrescription = (Button) findViewById(R.id.pp_b_recordPrescription);

		

		
		Intent intent = getIntent();

		HCN = intent.getStringExtra("hcn");
		intHCN = Integer.parseInt(HCN);


		patientName.setText(patientManager.getPatient(intHCN).getFirstName()
				+ " " + patientManager.getPatient(intHCN).getLastName());
		patientHCN.setText(String.valueOf(patientManager.getPatient(intHCN)
				.getHealthCardNumber()));
		patientBOD.setText(patientManager.getPatient(intHCN).getBirthDate());

		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");

		// hide the "Make New Visit Record" button if the user is a doctor
		if (usertype.equals("physician")) {
			makeNewVisitRecord.setVisibility(View.INVISIBLE);
			save.setVisibility(View.INVISIBLE);

		} else if (usertype.equals("nurse")) {

			recordPrescription.setVisibility(View.INVISIBLE);
		}

		if (patientManager == null) {
			Log.i(TAG, "patientManager object is null");
		}
		Log.i(TAG, "username is " + username);
		Log.i(TAG, "usertype is " + usertype);
		// Log.i(TAG, patientManager.getPatientMap().toString());
		Log.i(TAG, patientManager.getPatient(111111).toString());

		if (load == false) {
			try {
				loadVisitRecords();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				load = true;
			}
		}

		Log.i(TAG, "END OF ONCREATE");

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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_profile, menu);
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

	// implement what happens when you go to screen of all visit records
	/**
	 * this method responds to the button "View Visit Records" button and takes
	 * the user to the activity that displays the list of VisitRecords with the
	 * PatientManager object and the health card number for the patient being
	 * examined.
	 * 
	 * @throws FileNotFoundException
	 * 
	 */
	public void navigateToviewVisitRecords(View view) {
		Intent intent = new Intent(this, ViewVisitRecordsListActivity.class);
		//intent.putExtra("patientManager", patientManager);
		intent.putExtra("hcn", HCN);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);
	}

	/**
	 * this method responds to the button "Make New Visit Record" and takes the
	 * user to the activity that creates a new visit record with the
	 * PatientManager object and the health card number for the patient being
	 * examined.
	 * 
	 */
	public void navigateToCreateNewVisitRecord(View view) {
		Intent intent = new Intent(this, CreateVisitRecordActivity.class);
		// intent.putExtra("patientManager", patientManager);
		intent.putExtra("hcn", HCN);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);

	}

	public void navigateToRecordPrescription(View view) {
		Intent intent = new Intent(this, RecordPrescription.class);
		// intent.putExtra("patientManager", patientManager);
		intent.putExtra("hcn", HCN);
		intent.putExtra("username", username);
		intent.putExtra("usertype", usertype);
		startActivity(intent);
	}

	public void navigateToViewPrescriptions(View view) {
		Intent intent = new Intent(this, ViewPrescriptionsActivity.class);
		// intent.putExtra("patientManager", patientManager);
		intent.putExtra("hcn", HCN);
		intent.putExtra("username", username);
		intent.putExtra("usertype", usertype);
		startActivity(intent);
	}

	/**
	 * Saves VisitRecord
	 * 
	 * @param view
	 */
	public void saveToFiles(View view) throws IOException {
		Context context = getApplicationContext();
		String textToSave;
		List<VitalSignsRecord> VS;
		Patient p = patientManager.getPatient(intHCN);
		List<VisitRecord> VR_list = p.getVisitRecords();
		BufferedWriter bs;
		File dir = new File(getFilesDir() + "/visit_records" + "/" + HCN, "VisitRecords");
		File file;

		try {
			if (!dir.exists()) {
				dir.mkdir();
			}

			for (int i = 0; i < VR_list.size(); i++) {
				file = new File(dir, VR_list.get(i).recordTime);

				try {
					if (!file.exists()) {
						file.createNewFile();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				bs = new BufferedWriter(new FileWriter(file,true));
				VS = VR_list.get(i).getVSRs();
				
				for (int j = 0; j < VS.size(); j++){
					textToSave = VS.get(j).getTime()+"," + p.getAge() +"," + VS.get(j).getTemperature() + ","
						+ VS.get(j).getSystolicBloodPressure() + ","
						+ VS.get(j).getDiastolicBloodPressure() + ","
						+ VS.get(j).getHeartRate(); // format: time,age,temp,sbp,dbp,hr
					bs.write(textToSave);
					bs.write("\n");
					bs.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		CharSequence text = "File Saved.";
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	public void loadVisitRecords() throws FileNotFoundException {
		Patient p = patientManager.getPatient(intHCN);
		VitalSignsRecord VSR;
		VisitRecord VR;

		File vrDir = new File(getFilesDir() + "/visit_records" + "/" + HCN,"VisitRecords");
		vrDir.mkdirs();

		File[] Files = vrDir.listFiles();

		// Check if HCN file exists
		if (Files.length!= 0) {
			for (File vrFile : Files) {
				// Load File
				Scanner scanner = new Scanner(new FileInputStream(vrFile.getPath()));
				String[] record;
				VR = new VisitRecord();
				while (scanner.hasNextLine()) {
					record = scanner.nextLine().split(",");
					// 2014-10-23-2:35,1,1.0,1.0,1.0,1.0
					// time, age, sbp, dbp, hr
					String time = record[0];
					int age = p.getAge();
					double temp = Double.parseDouble(record[2]);
					double sbp = Double.parseDouble(record[3]);
					double dbp = Double.parseDouble(record[4]);
					double hr = Double.parseDouble(record[5]);
					
					VSR = new VitalSignsRecord(age,temp,sbp,dbp,hr,time);
					VR.addVSR(VSR);
				}
				p.addVisitRecord(VR);
				scanner.close();
				Log.i(TAG, "END OF LOAD");
			}
		}
}

}
