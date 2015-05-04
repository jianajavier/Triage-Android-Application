package com.example.triage;

import java.io.IOException;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String TAG = "MainActivity";
	// going to take the approach of look up but with passwords.txt and pass the
	// user.
	public static final String FILENAME = "passwords.txt";
	public static final String FILENAME2 = "patient_records.txt";

	private UserManager userManager;
	public PatientManager patientManager;
	EditText et_passcodeinput;
	EditText et_username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTheme(android.R.style.Theme_Holo_Dialog);
		setContentView(R.layout.activity_main);

		et_username = (EditText) findViewById(R.id.username);
		et_passcodeinput = (EditText) findViewById(R.id.passcodeinput);

		// load users onto a patientManager to make something that handles the
		// users
		try {
			userManager = new UserManager(this.getApplicationContext()
					.getFilesDir(), FILENAME);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Placed here because nurseActivity needs it
		try {
			patientManager = new PatientManager(this.getApplicationContext()
					.getFilesDir(), FILENAME2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Log.i(TAG, "END OF ONCREATE");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	 * responds to the "Look Up" button
	 * 
	 */
	public void login(View view) {
		try {
			String passcode = et_passcodeinput.getText().toString();
			String username = et_username.getText().toString();
			// if hcn not a key in patientManager raise error
			// works on emulator but not on phone
			if (!(userManager.getUserMap().containsKey(username))) {
				throw new NotValidInputException();
			}
			;
			Map<String, User> usermap = userManager.getUserMap();
			if (!(userManager.getUser(username).getPassword().equals(passcode))) {
				Log.i(TAG, userManager.getUser(username).getPassword());
				Log.i(TAG, passcode);
				throw new DoesNotMatchException();
			}
			Log.i(TAG, "person was correct");

			// go to nurse activity
			if ((userManager.getUser(username).getUserType().equals("nurse"))) {
				Intent intent = new Intent(this, NurseActivity.class);
				intent.putExtra("userManager", userManager);
				Log.i(TAG, "userManager tossed into Extra");
				// usertype to allow for certain activities to be performed
				String usertype = userManager.getUser(username).getUserType();
				intent.putExtra("usertype", usertype);
				intent.putExtra("username", username);
				intent.putExtra("patientManager", patientManager);
				Log.i(TAG, "usertype tossed into Extra");
				startActivity(intent);
			}

			// goto physician activity
			else if ((userManager.getUser(username).getUserType().equals("physician"))) {
				Intent intent = new Intent(this, LookUpActivity.class);
				intent.putExtra("userManager", userManager);
				Log.i(TAG, "userManager tossed into Extra");
				// usertype to allow for certain activities to be performed
				String usertype = userManager.getUser(username).getUserType();
				intent.putExtra("usertype", usertype);
				intent.putExtra("username", username);
				intent.putExtra("patientManager", patientManager);
				Log.i(TAG, "usertype tossed into Extra");
				startActivity(intent);
			}

			Log.i(TAG, "END OF login BUTTON");


		} catch (NotValidInputException e) {
			Context context = getApplicationContext();
			CharSequence text = "user not found";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

		} catch (DoesNotMatchException e) {
			// have a toast pop up that says it not valid input
			Context context = getApplicationContext();
			CharSequence text = "username and password do not match";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		} finally {
		}
	}

	// add new patients, other data so that we don't have to enter it every time
	private void setUpForTesting() {

	}
}
