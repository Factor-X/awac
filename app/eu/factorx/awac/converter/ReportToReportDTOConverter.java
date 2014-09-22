package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.dto.awac.get.ReportLineDTO;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.util.Colors;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Map;

public class ReportToReportDTOConverter implements Converter<ReportResult, ReportDTO> {

	@Override
	public ReportDTO convert(ReportResult report) {
		ReportDTO reportDTO = new ReportDTO();

		Map<String, List<Double>> scopeValuesByIndicator = report.getScopeValuesByIndicator();

		int notNullValues = 0;
		double total = 0;
		for (Map.Entry<String, List<Double>> entry : scopeValuesByIndicator.entrySet()) {
			if (entry.getValue().get(0) > 0) notNullValues++;
			total += entry.getValue().get(0);
		}

		int currentNotNullValue = 0;
		for (Map.Entry<String, List<Double>> entry : scopeValuesByIndicator.entrySet()) {
			ReportLineDTO reportLineDTO = new ReportLineDTO(entry.getKey());
			reportLineDTO.setScope1Value(entry.getValue().get(1));
			reportLineDTO.setScope2Value(entry.getValue().get(2));
			reportLineDTO.setScope3Value(entry.getValue().get(3));
			reportLineDTO.setOutOfScopeValue(entry.getValue().get(4));
			if (entry.getValue().get(0) > 0) {
				reportLineDTO.setColor("#" + Colors.makeGoodColorForSerieElement(currentNotNullValue + 1, notNullValues));
				currentNotNullValue++;
			}
			reportLineDTO.setPercentage(100 * entry.getValue().get(0) / total);
			reportDTO.getReportLines().add(reportLineDTO);
		}

		return reportDTO;
	}

}
