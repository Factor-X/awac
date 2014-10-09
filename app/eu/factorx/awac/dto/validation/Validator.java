package eu.factorx.awac.dto.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.factorx.awac.dto.validation.annotations.*;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import play.Logger;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {
	public static void validate(Object object) throws Exception {
		if (object == null) return;

		boolean validationFail = false;
		String failureMessage = "";

		Field[] declaredFields = object.getClass().getDeclaredFields();
		for (Field field : declaredFields) {

			Annotation[] annotations = field.getDeclaredAnnotations();
			boolean good = false;
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().equals(Optional.class)) {
					Object v = object.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)).invoke(object);
					if (v == null) good = true;
					break;
				}
			}
			if (good) {
				continue;
			}

			for (Annotation annotation : annotations) {
				if (annotation.annotationType().getPackage().equals(Validate.class.getPackage())) {

					Object value = object.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)).invoke(object);

					if (annotation.annotationType().equals(Validate.class)) {
						if (value instanceof List) {
							List list = (List) value;
							for (Object element : list) {
								Validator.validate(element);
							}
						} else {
							Validator.validate(value);
						}
					} else if (annotation.annotationType().equals(Optional.class)) {
					} else {
						// create a script engine manager

						Context cx = Context.enter();


						try {
							// Initialize the standard objects (Object, Function, etc.). This must be done before scripts can be
							// executed. The null parameter tells initStandardObjects
							// to create and return a scope object that we use
							// in later calls.
							Scriptable scope = cx.initStandardObjects();

							// Build the script
							// evaluate JavaScript code from String
							String name = annotation.annotationType().getSimpleName();
							InputStream is = play.Play.application().resourceAsStream("public/javascripts/scripts/" + name + ".js");
							String javascript = IOUtils.toString(is, "UTF-8");

							// Execute the script
							cx.evaluateString(scope, javascript, name + ".js", 1, null);

							Map<String, Object> parameters = new HashMap<>();
							for (Method method : annotation.annotationType().getDeclaredMethods()) {
								parameters.put(method.getName(), method.invoke(annotation));
							}

							ObjectMapper mapper = new ObjectMapper();
							cx.evaluateString(scope, "VALUE = " + mapper.writeValueAsString(value) + ";", name + ".js", 1, null);
							cx.evaluateString(scope, "ARGS = " + mapper.writeValueAsString(parameters) + ";", name + ".js", 1, null);

							Boolean result = (Boolean) cx.evaluateString(scope, "validate(VALUE, ARGS)", name + ".js", 1, null);
							if (!result) {
								validationFail = true;

								//create the error message
								failureMessage += "\n- ";

								//recover the error message
								if (annotation instanceof NotNull) {
									failureMessage += ((NotNull) annotation).message();
								} else if (annotation instanceof Null) {
									failureMessage += ((Null) annotation).message();
								} else if (annotation instanceof NotNull) {
									failureMessage += ((Size) annotation).message();
								} else if (annotation instanceof Pattern) {
									failureMessage += ((Pattern) annotation).message();
								} else if (annotation instanceof Range) {
									failureMessage += ((Range) annotation).message();
								} else {
									failureMessage += field.getName() + " is not valid";
								}

							}

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							Context.exit();
						}
					}
				}
			}

			// System.out.println("-- " + object.getClass().getName() + " :: " + field.getName());
		}
		if (validationFail) {

			Logger.error(object.toString());

			throw new Exception(failureMessage);
		}
	}
}
