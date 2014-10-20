package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class ExceptionsDTO extends DTO {

    private String message=null;
    private  String messageToTranslate=null;
    private final List<String> params = new ArrayList<>();

    // add default constructor for Json Parser
    public ExceptionsDTO() {
        this.message = null;
    }

    public ExceptionsDTO(String message) {
        this.message = message;
    }

    public ExceptionsDTO(BusinessErrorType businessError, String... params) {
        this.messageToTranslate = businessError.toString();
        for (String param : params) {
            this.params.add(param);
        }
    }

    public ExceptionsDTO(MyrmexRuntimeException t) {
        this.messageToTranslate = t.getBusinessErrorType().toString();
        for (String param : t.getParamList()) {
            this.params.add(param);
        }
    }

    public String getMessage() {
        return message;
    }

    public String getMessageToTranslate() {
        return messageToTranslate;
    }

    public List<String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "ExceptionsDTO{" +
                "message='" + message + '\'' +
                ", params=" + params +
                '}';
    }
}
