package eu.factorx.awac.dto.validation.annotations;

/**
 * Created by root on 8/07/14.
 */
public @interface Size {
    String message() default "";
    int min() default 0;
    int max() default Integer.MAX_VALUE;
}
