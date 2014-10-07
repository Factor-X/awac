package eu.factorx.awac.common;

/**
 * Created by gaston on 10/7/14.
 */
public enum TranslatedExceptionType {

		LOGIN_PASSWORD_PAIR_NOT_FOUND ("LOGIN_PASSWORD_PAIR_NOT_FOUND"),
		WRONG_INTERFACE_FOR_USER("WRONG_INTERFACE_FOR_USER"),
		SUSPENDED_ACCOUNT("SUSPENDED_ACCOUNT");

		private final String stringValue;
		private TranslatedExceptionType(final String s) { stringValue = s; }
		public String toString() { return stringValue; }
}
