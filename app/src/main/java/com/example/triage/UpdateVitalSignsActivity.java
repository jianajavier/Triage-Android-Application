package com.example.triage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * this activity updates vital sign of a particular visit record for a
 * particular patient.
 * 
 * 
 */
public class UpdateVitalSignsActivity extends Activity {

	private PatientManager patientManager = PatientManager.getPatientManager();
	public static final String TAG = "ViewVitalSignsRecordActivity";

	String HCN;
	int intHCN;
	String username;
	String usertype;
	
	Patient currentPatient;
	int visitRecordIndex;
	int vitalIndex;

	EditText et_sbp;
	EditText et_dbp;
	EditText et_hr;
	EditText et_temp;
	TextView tv_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updating_vital_signs);

		et_sbp = (EditText) findViewById(R.id.uvs_et_systolicBloodPressure);
		et_dbp = (EditText) findViewById(R.id.uvs_et_diastolicBloodPressure);
		et_hr = (EditText) findViewById(R.id.uvs_et_heartRate);
		et_temp = (EditText) findViewById(R.id.uvs_et_temperature);
		tv_name = (TextView) findViewById(R.id.uvs_tv_patientName);

		Intent intent = getIntent();
		HCN = intent.getStringExtra("hcn");
		intHCN = Integer.parseInt(HCN);
//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");
		// index of VisitRecords in the patient's collection of VisitRecord
		visitRecordIndex = intent.getIntExtra("visitIndex", 99);
		vitalIndex = intent.getIntExtra("index", 99);
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
		currentPatient = patientManager.getPatient(intHCN);
		Log.i(TAG,
				"The currentPatient HCN is "
						+ currentPatient.getHealthCardNumber());

		et_sbp.setText(String.valueOf(currentPatient.getVisitRecords()
				.get(visitRecordIndex).getVSR(vitalIndex)
				.getSystolicBloodPressure()));
		et_dbp.setText(String.valueOf(currentPatient.getVisitRecords()
				.get(visitRecordIndex).getVSR(vitalIndex)
				.getDiastolicBloodPressure()));
		et_hr.setText(String.valueOf(currentPatient.getVisitRecords()
				.get(visitRecordIndex).getVSR(vitalIndex).getHeartRate()));
		et_temp.setText(String.valueOf(currentPatient.getVisitRecords()
				.get(visitRecordIndex).getVSR(vitalIndex).getTemperature()));
		tv_name.setText(currentPatient.getFirstName() + " "
				+ currentPatient.getLastName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.updating_vital_signs, menu);
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
	 * this method responds to the click on the "Update" button and saves the
	 * input into the Patient object in the Patient object
	 * 
	 */
	public void updateVitalSigns(View view) {
		// vital signs recorded in a vital signs record
		// on visit record, "current" vital signs moved to log of old vital
		// signs
		// on visit record, new vital signs become current vital signs
		// say vital signs have been updated
		
		double sbp;
		double dbp;
		double hr;
		double temperature;
		
		String time;
		
		Time now = new Time();
		now.setToNow();

		String year = String.valueOf(now.year);
		String month = String.valueOf(now.month);
		String day = String.valueOf(now.monthDay);
		String hour = String.valueOf(now.hour);
		String minute = String.valueOf(now.minute);
		
		time = year + "-" + month + "-" + day + "-" + hour + ":" + minute;
		
		try {
			sbp = Double.parseDouble(et_sbp.getText().toString());
			dbp = Double.parseDouble(et_dbp.getText().toString());
			hr = Double.parseDouble(et_hr.getText().toString());
			temperature = Double.parseDouble(et_temp.getText().toString());
			
			//Need to deal with case if the field is empty.

			VitalSignsRecord VS = new VitalSignsRecord(currentPatient.getAge(), temperature, sbp, dbp, hr, time);
			currentPatient.getVisitRecords().get(visitRecordIndex).addVSR(VS);
			currentPatient.getVisitRecords().get(visitRecordIndex);
			
/*			if (et_hr.getText().toString() != ""){
				currentPatient.getVisitRecords().get(visitRecordIndex).getVitalSigns()
						.setHeartRate(hr);
			}
			if (et_sbp.getText().toString() != ""){
				currentPatient.getVisitRecords().get(visitRecordIndex).getVitalSigns()
						.setSystolicBloodPressure(sbp);
			}
			if (et_dbp.getText().toString() != ""){
				currentPatient.getVisitRecords().get(visitRecordIndex).getVitalSigns()
						.setDiastolicBloodPressure(dbp);
			}
			if (et_temp.getText().toString() != ""){
				currentPatient.getVisitRecords().get(visitRecordIndex).getVitalSigns()
						.setTemperature(temperature);
			}*/
			Context context = getApplicationContext();
	        String textToSave;
	        BufferedWriter bs=null;
	        File dir = new File(getFilesDir() + "/visit_records" + "/" + HCN,
					"VisitRecords");
	        File file = new File(dir,currentPatient.getVisitRecords().get(visitRecordIndex).recordTime);
           
	        try{
	        bs = new BufferedWriter(new FileWriter(file,true));

            textToSave = time +","+ currentPatient.getAge() +","+ temperature
                    +","+ sbp +"," +dbp +","
                    + hr; //format: time,age,temp,sbp,dbp,hr
            bs.write(textToSave);
            bs.write("\n");
            bs.close();
	        }catch(Exception e){
            	e.printStackTrace();
            }
	       
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Maybe print a Toast
		}

		

		Intent intent = new Intent(this, PatientProfileActivity.class);
		//intent.putExtra("patientManager", patientManager);
		intent.putExtra("hcn", HCN);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);

	}
}
