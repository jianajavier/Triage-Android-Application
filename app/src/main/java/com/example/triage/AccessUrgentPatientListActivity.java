package com.example.triage;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class AccessUrgentPatientListActivity extends ListActivity {

	String username;
	String usertype;

	private PatientManager patientManager = PatientManager.getPatientManager();
	public static final String TAG = "AccessUrgentPatientListActivity";

	/**
	 * The patientManageer that hold all the patients in the hospital.
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_access_urgent_patient_list);

		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		usertype = intent.getStringExtra("usertype");
//		patientManager = (PatientManager) intent
//				.getSerializableExtra("patientManager");

		Log.i(TAG, patientManager.toString());

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// put name of patients with urgency score in the string array and display on the list
		ArrayList<Patient> urgentPatientsList = (ArrayList<Patient>) createListOfUrgentPatients();
		Object[] urgentPatientArray =  urgentPatientsList.toArray();
		
		String[] recordTimes = new String[urgentPatientsList.size()];
		// Log.i(TAG, urgentPatientsList.toString());

		
		for(int i = 0; i<urgentPatientArray.length; i++) {
			recordTimes[i] = urgentPatientsList.get(i).getFirstName()+" " + urgentPatientsList.get(i).getLastName()+" " + urgentPatientsList.get(i).getUrgencyScore();
			//Log.i(TAG, recordTimes[i]);
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,
				recordTimes);
		setListAdapter(adapter);
		Log.i(TAG, "END OF ONRESUME");
		
	}

	private List<Patient> createListOfUrgentPatients() {

		urgencyComparator comparator = new urgencyComparator();
		PriorityQueue<Patient> pq = new PriorityQueue<Patient>(100, comparator);

		/**
		 * testing
		 */
//		List<Patient> test = setUpForTesting();
//		Log.i(TAG, "test Patients " + test.toString());
//		for (Patient patient : test) {
//			if (!patient.isSeenByDoctor()) {
//				pq.add(patient);
//			}
//			Log.i(TAG, "" + patient.getHealthCardNumber());
//		}

		Log.i(TAG, "pq is " + pq.toString());
		
		// Log.i(TAG, patientManager.getPatientList().toString());
		for (Patient patient : patientManager.getPatientMap().values()) {
			Log.i(TAG, "urgency score " + patient.getUrgencyScore());
			if (patient.getUrgencyScore() != -1) {
				// Log.i(TAG, "Urgency score is " + patient.getUrgencyScore());
				if (!patient.isSeenByDoctor()) {
					pq.add(patient);
				}
			}
			
		}
		Log.i(TAG, "pq is " + pq.toString());
		// patientManager.getPatientMap().values()
		Log.i(TAG, "pq is after filling in fake patients is " + pq.toString());
		List<Patient> listOfUrgentPatient = new ArrayList<Patient>();
		while (!pq.isEmpty()) {
			listOfUrgentPatient.add(pq.poll());
		}

		Log.i(TAG, "The test List is " + listOfUrgentPatient.toString());
		// get the first vital sign in the first visit record
		// Log.i(TAG, "111111's visit record is " + patientManager.getPatient(111111).getVisitRecords().get(0).getVSRs().get(0).toString());
		Log.i(TAG, "111111's urgencyscore is " + patientManager.getPatient(111111).getUrgencyScore());
		// Log.i(TAG, "pq is " + pq.toString());
		return listOfUrgentPatient;
	}

	private List<Patient> setUpForTesting() {
		
		// age < 2
		// temp >= 39.0
		// systolic >= 140
		// diastolic >= 90
		// hr >= 100 or <=50
		
		
		// make fake patients
		Patient a = new Patient(000000, "a", "a", "1994-03-12");
		a.setSeenByDoctor(true);
		
		VitalSignsRecord newVS = new VitalSignsRecord(a.getAge(), 10, 10, 10, 10, "arrivalTime");

		VisitRecord vr = new VisitRecord();
		vr.setVitalSigns(newVS);
		// vr.addVitalSignsRecord(newVS);
		a.addVisitRecord(vr);

		Patient b = new Patient(222222, "b", "b", "1994-03-12");
		// urgency score = 0
		VitalSignsRecord newVSb = new VitalSignsRecord(b.getAge(), 37, 139, 89, 75, "arrivalTime");

		VisitRecord vrb = new VisitRecord();
		vrb.setVitalSigns(newVSb);
		// vr.addVitalSignsRecord(newVS);
		b.addVisitRecord(vrb);

		
		Patient c = new Patient(333333, "c", "c", "2014-03-12");

		// urgengy score = 1
		VitalSignsRecord newVSc = new VitalSignsRecord(c.getAge(), 38.9, 139, 89, 75, "arrivalTime");
		VisitRecord vrc = new VisitRecord();
		vrc.setVitalSigns(newVSc);
		// vr.addVitalSignsRecord(newVS);
		c.addVisitRecord(vrc);

		Patient d = new Patient(444444, "d", "d", "1994-03-12");
		d.setSeenByDoctor(true);
		VitalSignsRecord newVSd = new VitalSignsRecord(d.getAge(), 40, 40, 40, 40, "arrivalTime");
		VisitRecord vrd = new VisitRecord();
		vrd.setVitalSigns(newVSd);
		// vr.addVitalSignsRecord(newVS);
		d.addVisitRecord(vrd);
		
		Patient e = new Patient(555555, "e", "e", "2014-03-12");
		// urgency score = 5
		VitalSignsRecord newVSe = new VitalSignsRecord(e.getAge(), 39, 140, 90, 100, "arrivalTime");
		VisitRecord vre = new VisitRecord();
		vre.setVitalSigns(newVSe);
		// vr.addVitalSignsRecord(newVS);
		e.addVisitRecord(vre);

		List<Patient> test = new ArrayList<Patient>();
		test.add(a);
		test.add(b);
		test.add(c);
		test.add(d);
		test.add(e);

		return test;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.access_urgent_patient_list, menu);
		return true;
	}

}
