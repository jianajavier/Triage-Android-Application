package com.example.triage;

import java.util.Comparator;

/**
 * This comparator compares the urgency scores of two patients
 * 
 *
 */
public class urgencyComparator implements Comparator<Patient> {

	@Override
	public int compare(Patient p1, Patient p2) {
		// TODO Auto-generated method stub
		
		if (p1.getUrgencyScore() <	p2.getUrgencyScore())
			return 1;
		
		if (p1.getUrgencyScore() >	p2.getUrgencyScore())
			return -1;
		
		return 0;
	}

}
