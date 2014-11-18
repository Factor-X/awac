package eu.factorx.awac.common.actions;

import java.lang.annotation.*;

import play.mvc.With;


/**
 * Created by gaston on 8/25/14.
 */

@With(SecurityAnnotationAction.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@Documented

public @interface SecurityAnnotation {
	boolean isAdmin () default false;
    boolean isMainVerifier () default false;
	boolean isSystemAdmin () default false;
}
