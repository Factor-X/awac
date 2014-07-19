package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

public class ReportDTO extends DTO {

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

}
