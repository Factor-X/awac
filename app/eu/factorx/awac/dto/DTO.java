package eu.factorx.awac.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.factorx.awac.util.MyrmexRunTimeException;
import play.Logger;
import eu.factorx.awac.dto.validation.NotNull;
import eu.factorx.awac.util.FileUtil;
import play.mvc.Content;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;

import javax.script.*;

public class DTO implements Content {

    private String __type;

    public String get__type() {
        return this.getClass().getCanonicalName();
    }

    public void set__type(String __type) {
        if (!get__type().equals(__type)) {
            throw new MyrmexRunTimeException("Wrong type of DTO received");
        }
    }

    @Override
    public String body() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new MyrmexRunTimeException(e,e.getMessage());
        }
    }

    @Override
    public String contentType() {
        return "application/json; charset=utf-8";
    }

    public static <T extends DTO> T getDTO(JsonNode data, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        JsonParser jp = data.traverse();
        try {
            T dto = mapper.readValue(jp, type);
            if (dto == null) {
                throw new MyrmexRunTimeException("Validation of DTO creation");
            }
            dto.validate();
            return dto;

        } catch (IOException e) {
            throw new MyrmexRunTimeException("Validation of DTO creation");
        }
    }

    public void validate() {
        /*
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<DTO>> violations = validator.validate(this);

        if (violations.size() > 0) {

            String errors = "";

            for(ConstraintViolation<DTO> constraintViolation : violations){
                errors=errors.concat("\n");
                errors=errors.concat(constraintViolation.getMessage());
            }

            throw new RuntimeException("Validation of DTO failed : "+errors);
*/
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                for (Annotation annotation : field.getAnnotations()) {
                    if (annotation.annotationType().getPackage().equals(NotNull.class.getPackage())) {
                        // create a script engine manager
                        ScriptEngineManager factory = new ScriptEngineManager();
                        // create a JavaScript engine
                        ScriptEngine engine = factory.getEngineByName("JavaScript");
                        // evaluate JavaScript code from String
                        String name = annotation.annotationType().getSimpleName();
                        String javascript = FileUtil.getContents("app/" + annotation.annotationType().getPackage().getName().replaceAll("\\.", "/") + "/scripts/" + name + ".js");
                        engine.eval(javascript);

                        Object o = this.getClass().getMethod("get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1)).invoke(this);

                        engine.put("VALUE", o);

                        Boolean result = (Boolean) engine.eval("validate(VALUE)");
                        if (!result) {
                            throw new Exception("Validation failed for property" + field.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Validation failed on DTO " + get__type());
        }
    }


}
