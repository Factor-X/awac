package eu.factorx.awac.dto.awac.post;


import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class AssignPeriodToProductDTO extends DTO {

	@NotNull
	private String periodKeyCode;

	@NotNull
	private Long productId;

	@NotNull
	private Boolean assign;

	public AssignPeriodToProductDTO() {
	}

	public String getPeriodKeyCode() {
		return periodKeyCode;
	}

	public void setPeriodKeyCode(String periodKeyCode) {
		this.periodKeyCode = periodKeyCode;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Boolean getAssign() {
		return assign;
	}

	public void setAssign(Boolean assign) {
		this.assign = assign;
	}

	@Override
	public String toString() {
		return "AssignPeriodToProductDTO{" +
				"periodKeyCode='" + periodKeyCode + '\'' +
				", productId=" + productId +
				", assign=" + assign +
				'}';
	}
}
