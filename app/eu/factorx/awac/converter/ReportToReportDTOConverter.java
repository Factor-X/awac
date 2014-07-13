package eu.factorx.awac.converter;

import org.springframework.core.convert.converter.Converter;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.models.reporting.Report;

public class ReportToReportDTOConverter implements Converter<Report, ReportDTO> {

	@Override
	public ReportDTO convert(Report report) {
		ReportDTO reportDTO = new ReportDTO();

		// TODO To Implement

		return reportDTO;
	}

}
