package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

public class ReportDTO extends DTO {

	private String              leftPeriod;
	private String              rightPeriod;

	private String              leftColor;
	private String              rightColor;

	private List<ReportLineDTO> reportLines;

	public ReportDTO() {
		super();
		reportLines = new ArrayList<>();
	}

	public List<ReportLineDTO> getReportLines() {
		return reportLines;
	}

	public void setReportLines(List<ReportLineDTO> reportLines) {
		this.reportLines = reportLines;
	}

	public String getLeftPeriod() {
		return leftPeriod;
	}

	public void setLeftPeriod(String leftPeriod) {
		this.leftPeriod = leftPeriod;
	}

	public String getRightPeriod() {
		return rightPeriod;
	}

	public void setRightPeriod(String rightPeriod) {
		this.rightPeriod = rightPeriod;
	}

	public String getLeftColor() {
		return leftColor;
	}

	public void setLeftColor(String leftColor) {
		this.leftColor = leftColor;
	}

	public String getRightColor() {
		return rightColor;
	}

	public void setRightColor(String rightColor) {
		this.rightColor = rightColor;
	}
}
