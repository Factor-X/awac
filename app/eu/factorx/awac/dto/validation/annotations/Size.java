package eu.factorx.awac.dto.validation.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@java.lang.annotation.Target({ElementType.FIELD})
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
public @interface Size {
	String message() default "The expected number of letter is wrong";

	int min() default 0;

	int max() default Integer.MAX_VALUE;
}
