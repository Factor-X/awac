package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.util.Keyed;

import java.util.Map;

public class LinkedListItemDTO extends DTO implements Keyed {

	private Long id;
	private String key;
	private String labelEn;
	private String labelFr;
	private String labelNl;
	private Integer orderIndex;
	private String activitySourceKey;
	private String activityTypeKey;

	public LinkedListItemDTO() {
	}

	public LinkedListItemDTO(Long id, String key, String labelEn, String labelFr, String labelNl, Integer orderIndex, String activitySourceKey, String activityTypeKey) {
		this.id = id;
		this.key = key;
		this.labelEn = labelEn;
		this.labelFr = labelFr;
		this.labelNl = labelNl;
		this.orderIndex = orderIndex;
		this.activitySourceKey = activitySourceKey;
		this.activityTypeKey = activityTypeKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getActivitySourceKey() {
		return activitySourceKey;
	}

	public void setActivitySourceKey(String activitySourceKey) {
		this.activitySourceKey = activitySourceKey;
	}

	public String getActivityTypeKey() {
		return activityTypeKey;
	}

	public void setActivityTypeKey(String activityTypeKey) {
		this.activityTypeKey = activityTypeKey;
	}

	@Override
	public Object uniqueKey() {
		return this.id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LinkedListItemDTO that = (LinkedListItemDTO) o;

		if (!activitySourceKey.equals(that.activitySourceKey)) return false;
		if (!activityTypeKey.equals(that.activityTypeKey)) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (!key.equals(that.key)) return false;
		if (!labelEn.equals(that.labelEn)) return false;
		if (labelFr != null ? !labelFr.equals(that.labelFr) : that.labelFr != null) return false;
		if (labelNl != null ? !labelNl.equals(that.labelNl) : that.labelNl != null) return false;
		if (!orderIndex.equals(that.orderIndex)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + key.hashCode();
		result = 31 * result + labelEn.hashCode();
		result = 31 * result + (labelFr != null ? labelFr.hashCode() : 0);
		result = 31 * result + (labelNl != null ? labelNl.hashCode() : 0);
		result = 31 * result + orderIndex.hashCode();
		result = 31 * result + activitySourceKey.hashCode();
		result = 31 * result + activityTypeKey.hashCode();
		return result;
	}
}
