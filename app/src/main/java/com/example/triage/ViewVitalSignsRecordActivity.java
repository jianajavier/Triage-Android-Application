package com.example.triage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * This activity is a view of a visit record of a patient.
 *
 */
public class ViewVitalSignsRecordActivity extends Activity {

	/**
	 * The PatientManager that holds all the patients.
	 */
	private PatientManager patientManager = PatientManager.getPatientManager();
	/**
	 * Name of the page.
	 */
	public static final String TAG = "ViewVitalSignsRecordActivity";
	/**
	 * A string representation of the patient's health card number.
	 */
	String HCN;
	/**
	 * A patient's health card number.
	 */
	int intHCN;
	/**
	 *  The index to VitalSignsRecord for the patient whose health card number is HCN.
	 */
	int vitalRecordIndex;
	int visitIndex;
	String username;
	String usertype;
	TextView tv_temp;
	TextView tv_sbp;
	TextView tv_dbp;
	TextView tv_hr;
	TextView timeSeen;

	Button update;
	Button seen;
	Button saveExit;

	String time;
	boolean seenDoctor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_visit_record);

		tv_temp = (TextView) findViewById(R.id.vvr_temperature);
		tv_sbp = (TextView) findViewById(R.id.vvr_sbp);
		tv_dbp = (TextView) findViewById(R.id.vvr_dbp);
		tv_hr = (TextView) findViewById(R.id.vvr_hr);
		update = (Button) findViewById(R.id.vvr_b_update);
		seen = (Button) findViewById(R.id.seenButton);
		timeSeen = (TextView) findViewById(R.id.timeSeen);
		saveExit = (Button) findViewById(R.id.saveAndExit);
		
		Intent intent = getIntent();
		HCN = intent.getStringExtra("hcn");
		intHCN = Integer.parseInt(HCN);
//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");
		// index of VisitRecords in the patient's collection of VisitRecord		
		vitalRecordIndex = intent.getIntExtra("index", 99);
		visitIndex = intent.getIntExtra("visitIndex", 99);
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
		
		
		if (usertype.equals("physician")) {
			update.setVisibility(View.INVISIBLE);
			seen.setVisibility(View.INVISIBLE);
			timeSeen.setVisibility(View.VISIBLE);
			saveExit.setVisibility(View.INVISIBLE);
		}
		Patient currentPatient = patientManager.getPatient(intHCN);

		VisitRecord particularVR = currentPatient.getVisitRecords().get(visitIndex);

		VitalSignsRecord particularVS =  particularVR.getVSR(vitalRecordIndex);
		
		tv_temp.setText(String.valueOf(particularVS.getTemperature()));
		tv_sbp.setText(String.valueOf(particularVS.getSystolicBloodPressure()));
		tv_dbp.setText(String.valueOf(particularVS.getDiastolicBloodPressure()));
		tv_hr.setText(String.valueOf(particularVS.getHeartRate()));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_visit_record, menu);
		return true;
	}
	
	public void updateVitalSigns(View view) {
		
		// the patient on whom to create visit record
		Patient p = patientManager.getPatient(intHCN);
		Log.i(TAG, "The currentPatient HCN is " + p.getHealthCardNumber());
		
		Log.i(TAG, "END OF updateVitalSigns()");
		

		// TODO UPDATE THE PATIENT P
		
		
		Intent intent = new Intent(this, UpdateVitalSignsActivity.class);
		// intent.putExtra("patientManager", patientManager);
		intent.putExtra("hcn", HCN);
		intent.putExtra("visitIndex", visitIndex);
		intent.putExtra("index", vitalRecordIndex);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);
		
	}

	public void setSeenTrue(View view) {
		Patient currentPatient = patientManager.getPatient(intHCN);

		currentPatient.setSeenByDoctor(true);
		Time now = new Time();
		now.setToNow();
		
		String year = String.valueOf(now.year);
		String month = String.valueOf(now.month);
		String day = String.valueOf(now.monthDay);
		String hour = String.valueOf(now.hour);
		String minute = String.valueOf(now.minute);
		
		time = year + "-" + month + "-" + day + "-" + hour + ":" + minute;
		
		currentPatient.setTimeSeen(time);
		
		Context context = getApplicationContext();
		CharSequence text = "Saved Time Seen";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        
        seen.setVisibility(View.INVISIBLE);
        seen.setEnabled(false);
        
        CharSequence ts = time;
        timeSeen.setText(ts);
        timeSeen.setVisibility(View.VISIBLE);
        
        
        VisitRecord particularVR = currentPatient.getVisitRecords().get(visitIndex);
        particularVR.setTimeSeen(time);
        
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		Intent intent = getIntent();

		HCN = intent.getStringExtra("hcn");
		intHCN = Integer.parseInt(HCN);
//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");

		
		Patient p = patientManager.getPatient(intHCN);
		//p.setSeenByDoctor(seenDoctor);
		//p.setTimeSeen(time);
        //p.getVisitRecords().get(visitRecordIndex).setTimeSeen(time);
		
		if (p.isSeenByDoctor()){
			seen.setVisibility(View.INVISIBLE);
	        seen.setEnabled(false);
	        
	        String ts = p.getTimeSeen();
	        
	        timeSeen.setText(ts);
	        timeSeen.setVisibility(View.VISIBLE);
		}
	}
	
	public void saveExit(View view){
		
		try {
			Intent intent = new Intent(this, NurseActivity.class);
			
			if (!(patientManager.getPatientMap().containsKey(intHCN))) {
				throw new NotValidInputException();
			}
			
			// intent.putExtra("patientManager", patientManager);
			intent.putExtra("hcn", HCN);
			intent.putExtra("username", username);
			intent.putExtra("usertype", usertype);


			// Log.i(TAG, "END OF saveAndExit BUTTON")
			startActivity(intent);

		} catch (NotValidInputException e) {


		}  finally {
		}

	}
	@Override
	public void onBackPressed(){
		
		if (usertype != "nurse"){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Are you sure?");
		builder.setMessage("Your changes will not be saved.");

		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int which) {
		        // Do do my action here

		        ViewVitalSignsRecordActivity.super.onBackPressed();
		    }

		});

		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.dismiss();
		    }
		});

		AlertDialog alert = builder.create();
		alert.show();
			
		
	}
	}
}
