package eu.factorx.awac.dto.awac.post;


import eu.factorx.awac.dto.DTO;

public class FormProgressDTO extends DTO{

	private String period;

	private Long scope;

	private String form;

	private Integer percentage;

	public FormProgressDTO() {
	}

	public FormProgressDTO(String period, Long scope, String form, Integer percentage) {
		this.period = period;
		this.scope = scope;
		this.form = form;
		this.percentage = percentage;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Long getScope() {
		return scope;
	}

	public void setScope(Long scope) {
		this.scope = scope;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "FormProgressDTO{" +
				"period='" + period +
				"', scope=" + scope +
				", form='" + form + '\'' +
				", percentage=" + percentage +
				'}';
	}
}
