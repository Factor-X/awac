package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;

public class SubListItemDTO extends DTO {

	private Long id;

	private String key;

	private Integer orderIndex;

	public SubListItemDTO() {
	}

	public SubListItemDTO(Long id, String key, Integer orderIndex) {
		this.id = id;
		this.key = key;
		this.orderIndex = orderIndex;
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

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
}
