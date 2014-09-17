package eu.factorx.awac.dto.awac.get;


public class ResultsDTO {

	private ReportDTO reportDTO;
	
	private String donutSvg;
	
	private String webSvg;
	
	private String histogramSvg;

	/**
	 * @param reportDTO
	 * @param donutSvg
	 * @param webSvg
	 * @param histogramSvg
	 */
	public ResultsDTO(ReportDTO reportDTO, String donutSvg, String webSvg, String histogramSvg) {
		super();
		this.reportDTO = reportDTO;
		this.donutSvg = donutSvg;
		this.webSvg = webSvg;
		this.histogramSvg = histogramSvg;
	}
	
	
}
