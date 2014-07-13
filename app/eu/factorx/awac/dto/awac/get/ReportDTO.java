package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;

public class ReportDTO extends DTO {

	private List<ReportLineDTO> reportLines;

	public ReportDTO() {
		super();
	}

	public List<ReportLineDTO> getReportLines() {
		return reportLines;
	}

	public void setReportLines(List<ReportLineDTO> reportLines) {
		this.reportLines = reportLines;
	}

}
