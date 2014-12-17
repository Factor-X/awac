package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsDTO extends DTO {

	private final Map<String, ReportDTO> reportDTOs;
	private final Map<String, String> leftSvgDonuts;
	private final Map<String, String> rightSvgDonuts;
	private final Map<String, String> svgWebs;
	private final Map<String, String> svgHistograms;
	private final List<ReportLogEntryDTO> logEntries;
	private Map<String, ReportDTO> reportCEFDTOs;
	private Map<String, String> svgHistogramsCEF;
	private Map<String, Double> typeMap;
	private Map<String, Double> idealMap;

	public ResultsDTO() {
		reportDTOs = new HashMap<>();
		reportCEFDTOs = new HashMap<>();
		leftSvgDonuts = new HashMap<>();
		rightSvgDonuts = new HashMap<>();
		svgWebs = new HashMap<>();
		svgHistograms = new HashMap<>();
		svgHistogramsCEF = new HashMap<>();
		logEntries = new ArrayList<ReportLogEntryDTO>();
		idealMap = new HashMap<>();
		typeMap = new HashMap<>();
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

	public Map<String, ReportDTO> getReportCEFDTOs() {
		return reportCEFDTOs;
	}

	public void setReportCEFDTOs(Map<String, ReportDTO> reportCEFDTOs) {
		this.reportCEFDTOs = reportCEFDTOs;
	}

	public Map<String, String> getSvgHistogramsCEF() {
		return svgHistogramsCEF;
	}

	public void setSvgHistogramsCEF(Map<String, String> svgHistogramsCEF) {
		this.svgHistogramsCEF = svgHistogramsCEF;
	}

	public Map<String, Double> getTypeMap() {
		return typeMap;
	}

	public void setTypeMap(Map<String, Double> typeMap) {
		this.typeMap = typeMap;
	}

	public Map<String, Double> getIdealMap() {
		return idealMap;
	}

	public void setIdealMap(Map<String, Double> idealMap) {
		this.idealMap = idealMap;
	}
}
