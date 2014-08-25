package eu.factorx.awac.common.actions;

import eu.factorx.awac.controllers.SecuredController;
import play.mvc.With;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


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
	boolean isSystemAdmin () default false;
}
