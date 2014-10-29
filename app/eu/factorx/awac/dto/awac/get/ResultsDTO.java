package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.dto.DTO;

public class ResultsDTO extends DTO {

	private final Map<String, ReportDTO>  reportDTOs;
	private final Map<String, String>     leftSvgDonuts;
	private final Map<String, String>     rightSvgDonuts;
	private final Map<String, String>     svgWebs;
	private final Map<String, String>     svgHistograms;
	private final List<ReportLogEntryDTO> logEntries;

	public ResultsDTO() {
		reportDTOs = new HashMap<>();
		leftSvgDonuts = new HashMap<>();
		rightSvgDonuts = new HashMap<>();
		svgWebs = new HashMap<>();
		svgHistograms = new HashMap<>();
		logEntries = new ArrayList<ReportLogEntryDTO>();
	}

	public Map<String, ReportDTO> getReportDTOs() {
		return reportDTOs;
	}

	public Map<String, String> getLeftSvgDonuts() {
		return leftSvgDonuts;
	}

	public Map<String, String> getRightSvgDonuts() {
		return rightSvgDonuts;
	}

	public Map<String, String> getSvgWebs() {
		return svgWebs;
	}

	public Map<String, String> getSvgHistograms() {
		return svgHistograms;
	}

	public List<ReportLogEntryDTO> getLogEntries() {
		return logEntries;
	}

}
