package eu.factorx.awac.dto.validation.annotations;

public @interface Value {
	String message() default "The expected number is wrong";

	double min() default 0;

	double max() default Double.MAX_VALUE;
}
