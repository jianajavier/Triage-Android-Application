package com.example.triage;

/**
 * The class representing a user that is a nurse.
 * 
 */
public class Nurse extends User{

	/**
	 * Serial Id of nurses.
	 */
	private static final long serialVersionUID = 3458909953723777903L;

	/**
	 * Creates a Nurse given a login ID and a password. Nurse has a usertype of 'nurse'.
	 * @param loginid The nurse's login ID.
	 * @param userpassword The nurse's password.
	 */
	public Nurse(String loginid, String nursepassword){
		super(loginid, nursepassword);
		setNewUserType("nurse");
	}
}
