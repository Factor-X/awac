package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.dto.awac.get.ReportLineDTO;
import eu.factorx.awac.models.reporting.Report;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Map;

public class ReportToReportDTOConverter implements Converter<Report, ReportDTO> {

	@Override
	public ReportDTO convert(Report report) {
		ReportDTO reportDTO = new ReportDTO();

		Map<String, List<Double>> scopeValuesByIndicator = report.getScopeValuesByIndicator();

		for (Map.Entry<String, List<Double>> entry : scopeValuesByIndicator.entrySet()) {
			ReportLineDTO reportLineDTO = new ReportLineDTO(entry.getKey());
			reportLineDTO.setScope1Value(entry.getValue().get(1));
			reportLineDTO.setScope2Value(entry.getValue().get(2));
			reportLineDTO.setScope3Value(entry.getValue().get(3));
			reportLineDTO.setOutOfScopeValue(entry.getValue().get(4));
			reportDTO.getReportLines().add(reportLineDTO);
		}

		return reportDTO;
	}

}
