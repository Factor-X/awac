package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class CodeLabelDTO extends DTO {

	private String key;

	private String label;

    public CodeLabelDTO() {
    }

    public CodeLabelDTO(String key, String label) {
		super();
		this.key = key;
		this.label = label;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "CodeLabelDTO [key=" + key + ", label=" + label + "]";
	}

}
