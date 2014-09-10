package eu.factorx.awac.dto.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@java.lang.annotation.Target({ElementType.FIELD})
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
public @interface Range {
	String message() default "The expected number is wrong";

	double min() default 0;

	double max() default Double.MAX_VALUE;
}
