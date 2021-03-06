package eu.factorx.awac.dto.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@java.lang.annotation.Target({ElementType.FIELD})
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)

public @interface Pattern {
	/**
	 * RFC 5322 EMAIL VALIDATION
	 */
	public static final String EMAIL = "/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])/";

	public static final String PASSWORD = "/[A-Za-z0-9#?!@$%^&*-]{5,20}/";

	public static final String PASSWORD_OPTIONAL = "/([A-Za-z0-9#?!@$%^&*-]{5,20}|.{0})/";

	public static final String IDENTIFIER = "/[a-zA-Z0-9_-]{5,20}/";

    public static final String PHONE_NUMBER = "/.{5-12}/";

	public String regexp() default "/.*/";

	String message() default "The field doesn't have the expected structure";
}
