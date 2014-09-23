package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsDTO extends DTO {

	private Map<String, ReportDTO> reportDTOs;

	private Map<String, String>     svgDonuts;
	private Map<String, String>     svgWebs;
	private Map<String, String>     svgHistograms;
	private List<ReportLogEntryDTO> logEntries;

	public ResultsDTO() {
		reportDTOs = new HashMap<>();
		svgDonuts = new HashMap<>();
		svgWebs = new HashMap<>();
		svgHistograms = new HashMap<>();
		logEntries = new ArrayList<ReportLogEntryDTO>();
	}

	public Map<String, ReportDTO> getReportDTOs() {
		return reportDTOs;
	}

	public Map<String, String> getSvgDonuts() {
		return svgDonuts;
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
