package com.example.triage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
//MAKE IT SERIALIAZABLE TO SO IT CAN BE PASSED FROM ONE ACTIVITY TO ANOTHER
/**
* The class UserManager holds all the User object made for the hospital.
* 
*/

public class UserManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4326155359967169130L;

	/**
	 * A list of all the users in the hospital.
	 */
	private List<User> users;

	/**
	 * A map that maps a user's login ID to the User.
	 * @param <String> The User's login Id.
	 * @param <User> A User.
	 */
	private Map<String,User> userMap = new HashMap<String, User>();

	/**
	 * Creates a UserManager using a file.
	 * @param dir The directory UserManager will be stored in.
	 * @param fileName The name the file will be called.
	 *
	 */
	public UserManager(File dir, String fileName) throws IOException {
		users = new ArrayList<User>();
		File file = new File(dir, fileName);

		if (file.exists()) {
			populate(file.getPath());
		} else {
			file.createNewFile();
		}
	}

	/**
	 * Populates the UserManager with users based on the file.
	 * @param filePath The filePath
	 * 
	 */
	private void populate(String filePath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(filePath));
		String[] record;
		while (scanner.hasNextLine()) {
			record = scanner.nextLine().split(",");
			// yorgos,34234dd,nurse
			String loginid = record[0];
			String password = record[1];
			String usertype = record[2];
			// if usertype is physician, make a physician  
			if (usertype.equals("physician")){
				users.add(new Physician(loginid, password));
				Physician newphysician = new Physician(loginid, password);
				userMap.put(newphysician.getLoginId(), newphysician);
			}
			else if (usertype.equals("nurse")){
				users.add(new Nurse(loginid, password));
				Nurse newnurse = new Nurse(loginid, password);
				userMap.put(newnurse.getLoginId(), newnurse);
				}
		}
		scanner.close();
	}

	/**
	 * Returns a string of the users in the UserManager.
	 * @return A string of the users in the UserManager.
	 */
	@Override
	public String toString() {
		return "UserManager [users=" + users + "]";
	}

	/**
	 * Adds a User to the UserManager.
	 * @param user A User.
	 * 
	 */
	public void add(User user) {
		users.add(user);
	}

	/**
	 * Returns a list of the users in the UserManager.
	 * @return A list of the users in the UserManager.
	 * 
	 */
	public List<User> getUserList() {
		return users;
	}

	/**
	 * Returns A map mapping user login ID to the users in the UserManager.
	 * @return A map mapping user login ID to the users in the UserManager.
	 * 
	 */
	public Map<String, User> getUserMap() {
		return userMap;
	}

	/**
	 * Returns the user with the username username.
	 * @param username a user's username.
	 * @return The User with the username asked for.
	 * 
	 */
	public User getUser(String username) {
		
		return getUserMap().get(username);
	}

}
