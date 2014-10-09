package eu.factorx.awac.dto.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.factorx.awac.dto.validation.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import play.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
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
						ScriptEngineManager factory = new ScriptEngineManager();
						// create a JavaScript engine

						Logger.info("EngineFactories : " );
						for (ScriptEngineFactory engineFactory : factory.getEngineFactories()) {
							String name = engineFactory.getEngineName();
							String version = engineFactory.getEngineVersion();
							String extensions = StringUtils.join(engineFactory.getExtensions(), ", ");
							Logger.info("EngineFactory[ name = " + name + ", version = " + version + ", extensions = " + extensions + " ]");
						}


						ScriptEngine engine = factory.getEngineByExtension("js");
						// evaluate JavaScript code from String
						String name = annotation.annotationType().getSimpleName();
						//String javascript = FileUtil.getContents("app/" + annotation.annotationType().getPackage().getName().replaceAll("\\.", "/") + "/../scripts/" + name + ".js");
						//String javascript = FileUtil.getContents("public/javascripts/scripts/"+ name + ".js");

						InputStream is = play.Play.application().resourceAsStream("public/javascripts/scripts/" + name + ".js");
						String javascript = IOUtils.toString(is, "UTF-8");

						engine.eval(javascript);

						Map<String, Object> parameters = new HashMap<>();

						for (Method method : annotation.annotationType().getDeclaredMethods()) {
							parameters.put(method.getName(), method.invoke(annotation));
						}

						ObjectMapper mapper = new ObjectMapper();

						engine.eval("VALUE = " + mapper.writeValueAsString(value) + ";");
						engine.eval("ARGS = " + mapper.writeValueAsString(parameters) + ";");

						Boolean result = (Boolean) engine.eval("validate(VALUE, ARGS)");
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
