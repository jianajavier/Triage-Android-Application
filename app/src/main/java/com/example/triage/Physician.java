package com.example.triage;

/**
*
* The class representing a user that is a physician.
* 
*/
public class Physician extends User{

	/**
	 * Serial ID of physician.
	 */
	private static final long serialVersionUID = -8542630506880968706L;

	/**
	* Creates a Physician given a login ID and a password. Nurse has a usertype of 'nurse'.
	* @param loginid The physician's login ID.
	* @param userpassword The physician's password.
	*/
	public Physician(String loginid, String physicianpassword){
		super(loginid, physicianpassword);
		setNewUserType("physician");
		}
}
