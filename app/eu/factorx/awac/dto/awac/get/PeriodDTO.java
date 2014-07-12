package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class PeriodDTO extends DTO {
    private Long id;
    private String label;

    public PeriodDTO() {
    }

    public PeriodDTO(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
