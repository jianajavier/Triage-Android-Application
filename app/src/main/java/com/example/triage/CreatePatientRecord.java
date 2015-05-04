package com.example.triage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePatientRecord extends Activity {

	private PatientManager patientManager = PatientManager.getPatientManager();
	public static final String TAG = "CreatePatientRecord";

	// The patient's health card number
	String HCN;
	int intHCN;
	String username;
	String usertype;
	
	// firstname
	EditText et_firstName;
	// lastname
	EditText et_lastName;
	// birthdate
	EditText et_year;
	EditText et_month;
	EditText et_day;
	// hcn
	EditText et_hcn;

	// save buttons
	Button saveView;
	Button saveExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_patient_record);
		

		et_firstName = (EditText) findViewById(R.id.cpr_et_name);
		et_lastName = (EditText) findViewById(R.id.cpr_et_lname);
		et_year = (EditText) findViewById(R.id.cpr_et_year);
		et_month = (EditText) findViewById(R.id.cpr_et_month);
		et_day = (EditText) findViewById(R.id.cpr_et_day);
		et_hcn = (EditText) findViewById(R.id.cpr_et_hcn);
		
		Intent intent = getIntent();

//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");
		
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
		Log.i(TAG, "END OF ONCREATE");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_patient_record, menu);
		return true;
	}

	
	public void saveExit(View view) throws IOException{
		addPatient();
		save();
		Intent intent = new Intent(this, NurseActivity.class);
		// intent.putExtra("patientManager", patientManager);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);
	}
	
	public void saveView(View view) throws IOException{
		addPatient();
		save();
		Intent intent = new Intent(this, PatientProfileActivity.class);
		// intent.putExtra("patientManager", patientManager);
		intent.putExtra("hcn", HCN);
		intent.putExtra("usertype", usertype);
		intent.putExtra("username", username);
		startActivity(intent);
	}
	
	public void addPatient(){
		try{
			String fn = et_firstName.getText().toString();
			String ln = et_lastName.getText().toString();
			String bd = et_year.getText().toString() + "-"+et_month.getText().toString()+ "-"+et_day.getText().toString();
			HCN = et_hcn.getText().toString();
			
			if (fn == null || ln == null || bd == null || HCN == null){
				throw new NotValidInputException();
			}
			
			int intHCN = Integer.parseInt(HCN);
				
			patientManager.add(new Patient(intHCN, fn, ln, bd));
			
			} catch(NotValidInputException e){
				Context context = getApplicationContext();
				CharSequence text = "Must fill all fields.";
				int duration = Toast.LENGTH_SHORT;
		
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();

	} 		
		finally{}

			
			//String.valueOf(myDate.) + "-" + myDate.getMonth() + "-" + myDate.getDate();

			Log.i(TAG, "END OF addPatient");
	}
	
	/**
	 * Saves new patient to patient_records.txt
	 * @throws IOException
	 */
	public void save() throws IOException{
		Context context = getApplicationContext();
        String textToSave;
        BufferedWriter bs;
        File file = new File(getFilesDir(),"patient_records.txt");

        String fn = et_firstName.getText().toString();
		String ln = et_lastName.getText().toString();
		String bd = et_year.getText().toString() + "-"+et_month.getText().toString()+ "-"+et_day.getText().toString();
		HCN = et_hcn.getText().toString();

		try {
            if (!file.exists()){
                file.createNewFile();
            }


	        bs = new BufferedWriter(new FileWriter(file,true));
	        if (!emptyLine()){
	        	bs.write("\n");
	        }
		        textToSave = HCN +"," + fn +" "+ ln + "," + bd ; //format:
		        bs.write(textToSave);
				bs.close();


		}catch(Exception e){
	        e.printStackTrace();
	    }

        CharSequence text = "File Saved.";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
	}

	public boolean emptyLine() throws IOException{
		 File file = new File(getFilesDir(),"patient_records.txt");
		 BufferedReader br =new BufferedReader(new FileReader(file));
		 String line, last="";

		 while ((line = br.readLine()) != null){
			 last = line;
		 }
		 br.close();
		 return (last.trim().isEmpty());
	}
}

