package eu.factorx.awac.dto.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@java.lang.annotation.Target({ElementType.FIELD})
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)

public @interface NotNull {
	String message() default "The field  cannot be null";
}