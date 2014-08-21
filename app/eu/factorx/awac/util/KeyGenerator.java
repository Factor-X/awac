package eu.factorx.awac.util;

import java.util.Random;

public class KeyGenerator {

	private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	private static final String LETTERS_LITTLE = "abcdefghijklmnopqrstuvwxyz1234567890";

	public static String generateRandomKey(final int nbLetter) {

		String result = "";

		final Random rand = new Random();
		for (int i = 0; i < nbLetter; i++) {
			result += LETTERS.charAt(rand.nextInt(LETTERS.length()));
		}

		return result;
	}


	public static String generateRandomPassword(final int nbLetter) {

		String result = "";

		final Random rand = new Random();
		for (int i = 0; i < nbLetter; i++) {
			result += LETTERS_LITTLE.charAt(rand.nextInt(LETTERS_LITTLE.length()));
		}

		return result;
	}

}
