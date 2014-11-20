package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.util.Keyed;

public class FullCodeLabelDTO extends DTO implements Keyed {

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

	@Override
	public Object uniqueKey() {
		return key;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FullCodeLabelDTO that = (FullCodeLabelDTO) o;

		if (!key.equals(that.key)) return false;
		if (!labelEn.equals(that.labelEn)) return false;
		if (labelFr != null ? !labelFr.equals(that.labelFr) : that.labelFr != null) return false;
		if (labelNl != null ? !labelNl.equals(that.labelNl) : that.labelNl != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = key.hashCode();
		result = 31 * result + labelEn.hashCode();
		result = 31 * result + (labelFr != null ? labelFr.hashCode() : 0);
		result = 31 * result + (labelNl != null ? labelNl.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "FullCodeLabelDTO{" +
				"key='" + key + '\'' +
				", labelEn='" + labelEn + '\'' +
				", labelFr='" + labelFr + '\'' +
				", labelNl='" + labelNl + '\'' +
				'}';
	}
}
