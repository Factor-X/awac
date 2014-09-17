package eu.factorx.awac.models.knowledge;

import javax.persistence.*;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "mm_report_indicator")
public class ReportIndicator extends AbstractEntity {

	private static final long serialVersionUID = -446540676020436619L;

	public static final String PROPERTY_NAME_ORDER_INDEX = "orderIndex";

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Report report;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Indicator indicator;

	@Column(nullable = false)
	private Integer orderIndex;

	public ReportIndicator() {
		super();
	}

	/**
	 * @param report
	 * @param indicator
	 * @param orderIndex
	 */
	public ReportIndicator(Report report, Indicator indicator, Integer orderIndex) {
		super();
		this.report = report;
		this.indicator = indicator;
		this.orderIndex = orderIndex;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

}
