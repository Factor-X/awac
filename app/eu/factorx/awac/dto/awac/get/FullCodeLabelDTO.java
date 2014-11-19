package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class FullCodeLabelDTO extends DTO {

	private String key;
	private String labelEn;
	private String labelFr;
	private String labelNl;
	private String topic;

	public FullCodeLabelDTO() {
	}

	public FullCodeLabelDTO(String key, String labelEn, String labelFr, String labelNl, String topic) {
		this.key = key;
		this.labelEn = labelEn;
		this.labelFr = labelFr;
		this.labelNl = labelNl;
		this.topic = topic;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLabelEn() {
		return labelEn;
	}

	public void setLabelEn(String labelEn) {
		this.labelEn = labelEn;
	}

	public String getLabelFr() {
		return labelFr;
	}

	public void setLabelFr(String labelFr) {
		this.labelFr = labelFr;
	}

	public String getLabelNl() {
		return labelNl;
	}

	public void setLabelNl(String labelNl) {
		this.labelNl = labelNl;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
}
