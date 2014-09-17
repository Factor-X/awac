package eu.factorx.awac.dto.awac.get;

import java.util.Map;

public class ResultsDTO {

	private Map<String, ReportDTO> reportDTOs;

	private Map<String, String> svgDonuts;

	private String svgWeb;

	private String svgHistogram;

	/**
	 * @param reportDTO
	 * @param svgDonuts
	 * @param svgWeb
	 * @param svgHistogram
	 */
	public ResultsDTO(Map<String, ReportDTO> reportDTOs, Map<String, String> svgDonuts, String svgWeb, String svgHistogram) {
		super();
		this.reportDTOs = reportDTOs;
		this.svgDonuts = svgDonuts;
		this.svgWeb = svgWeb;
		this.svgHistogram = svgHistogram;
	}

	public Map<String, ReportDTO> getReportDTOs() {
		return reportDTOs;
	}

	public void setReportDTOs(Map<String, ReportDTO> reportDTOs) {
		this.reportDTOs = reportDTOs;
	}

	public Map<String, String> getSvgDonuts() {
		return svgDonuts;
	}

	public void setSvgDonuts(Map<String, String> svgDonuts) {
		this.svgDonuts = svgDonuts;
	}

	public String getSvgWeb() {
		return svgWeb;
	}

	public void setSvgWeb(String svgWeb) {
		this.svgWeb = svgWeb;
	}

	public String getSvgHistogram() {
		return svgHistogram;
	}

	public void setSvgHistogram(String svgHistogram) {
		this.svgHistogram = svgHistogram;
	}

}
