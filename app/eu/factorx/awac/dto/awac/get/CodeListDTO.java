package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import java.util.List;

public class CodeListDTO extends DTO {

    private String code;

    private List<CodeLabelDTO> codeLabels;

    public CodeListDTO() {
    }

    public CodeListDTO(String code) {
        this.code = code;
    }

    public List<CodeLabelDTO> getCodeLabels() {
        return codeLabels;
    }

    public void setCodeLabels(List<CodeLabelDTO> codeLabels) {
        this.codeLabels = codeLabels;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
