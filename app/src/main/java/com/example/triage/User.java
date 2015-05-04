package com.example.triage;

import java.io.Serializable;

/**
 * An abstract class representing users of the Triage app.
 * There are different types of User such as Nurse and Physician.
 * A User has a login name and password. Certain types of Users are able to perform certain activities.
 *
 */
public abstract class User implements Serializable{

	/**
	 * Serial Id for Users
	 */
	private static final long serialVersionUID = 1306090249140089380L;

	/**
	 * The User's login ID.
	 */
	private String login;
	
	/**
	 * The User's password.
	 */
	private String password;
	
	/**
	 * The User's type (nurse, physician, etc.).
	 */
	private String usertype;
	
	/**
	 * Creates a User given a login ID and a password. 
	 * @param loginid The user's login ID.
	 * @param userpassword The user's password.
	 */
	public User(String loginid, String userpassword){
		login = loginid;
		password = userpassword;
		usertype = null;
		
	}
	
	/**
	 * Changes the user's login ID to the new login ID.
	 * @param newlogin The user's new login ID.
	 */
	public void setLoginId(String newlogin){
		login = newlogin;
	}
	
	/**
	 * Changes the password to the new password.
	 * @param newpassword The new password of the user.
	 */
	public void setNewPassword(String newpassword){
		password = newpassword;
	}
	
	/**
	 * Changes the user type to the new user type.
	 * @param newusertype The new type of the user.
	 */
	public void setNewUserType(String newusertype){
		usertype = newusertype;
	}
	
	/**
	 * Return's the user's login ID.
	 * @return The user's login ID.
	 */
	public String getLoginId(){
		return login;
	}
	
	/**
	 * Return's the user's password.
	 * @return The user's password.
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Return's the user's type.
	 * @return The user's type.
	 */
	public String getUserType(){
		return usertype;
	}
	
	/**
	 * Returns a string containing all the user's information  in the format
	 * "username,password,usertype".
	 *
	 */
	@Override
	public String toString() {
		return login + "," + password + ","
				+ usertype;
	}
	

}
