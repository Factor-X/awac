package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class PeriodDTO extends DTO {
	private String key;
	private String label;

	public PeriodDTO() {
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

	public String toString() {
		String content = "[";
		content += "key:" + key;
		content += ",label:" + label;
		content += "]";
		return content;


	}
}
