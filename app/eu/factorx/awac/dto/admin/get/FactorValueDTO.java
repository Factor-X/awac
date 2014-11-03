package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;

public class FactorValueDTO extends DTO {


    private String key;
    private String description;
    private String type;
    private String origin;
    private List<FactorValueDTO> factorValues;

    public FactorValueDTO() {
        factorValues = new ArrayList<FactorValueDTO>();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public List<FactorValueDTO> getFactorValues() {
        return factorValues;
    }
}
