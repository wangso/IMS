package com.neurolab.api.utility;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {

	public static void main(String args[]) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] messageDigest = md.digest("NeuroLab1".getBytes());

		// Convert byte array into signum representation
		BigInteger no = new BigInteger(1, messageDigest);

		// Convert message digest into hex value
		String subDomains = no.toString(32);

		if (subDomains.length() <= 24) {
			while (subDomains.length() < 24) {
				subDomains = "0" + subDomains;
			}
		} else {
			subDomains = subDomains.substring(0, 24);
		}
	}
}
