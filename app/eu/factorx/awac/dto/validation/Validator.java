package eu.factorx.awac.dto.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Validate;
import eu.factorx.awac.util.FileUtil;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {
    public static void validate(Object object) throws Exception {
        if (object == null) return;

        for (Field field : object.getClass().getDeclaredFields()) {

            System.out.println("++ " + object.getClass().getName() + " :: " + field.getName());
            Class<?> fieldType = field.getType();


            for (Annotation annotation : field.getAnnotations()) {
                if (annotation.annotationType().getPackage().equals(NotNull.class.getPackage())) {

                    Object o = object.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)).invoke(object);

                    if (annotation.annotationType().equals(Validate.class)) {
                        if ( o instanceof List) {
                            List list = (List) o;
                            for (Object element : list) {
                                Validator.validate(element);
                            }
                        } else {
                            Validator.validate(o);
                        }
                    } else {
                        // create a script engine manager
                        ScriptEngineManager factory = new ScriptEngineManager();
                        // create a JavaScript engine
                        ScriptEngine engine = factory.getEngineByName("JavaScript");
                        // evaluate JavaScript code from String
                        String name = annotation.annotationType().getSimpleName();
                        String javascript = FileUtil.getContents("app/" + annotation.annotationType().getPackage().getName().replaceAll("\\.", "/") + "/../scripts/" + name + ".js");
                        engine.eval(javascript);

                        Map<String, Object> parameters = new HashMap<>();

                        for (Method method : annotation.annotationType().getDeclaredMethods()) {
                            parameters.put(method.getName(), method.invoke(annotation));
                        }

                        ObjectMapper mapper = new ObjectMapper();

                        engine.put("VALUE", o);
                        engine.eval("ARGS = " + mapper.writeValueAsString(parameters) + ";");

                        Boolean result = (Boolean) engine.eval("validate(VALUE, ARGS)");
                        if (!result) {
                            throw new Exception("Validation failed for property" + field.getName());
                        }
                    }
                }
            }

            System.out.println("-- " + object.getClass().getName() + " :: " + field.getName());
        }

    }
}
