package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.dto.awac.get.ReportLineDTO;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.impl.reporting.ReportResultAggregation;
import eu.factorx.awac.service.impl.reporting.ReportResultIndicatorAggregation;
import eu.factorx.awac.util.Colors;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Map;

public class ReportResultAggregationToReportDTOConverter implements Converter<ReportResultAggregation, ReportDTO> {

	@Override
	public ReportDTO convert(ReportResultAggregation reportResultAggregation) {
		ReportDTO reportDTO = new ReportDTO();

		reportDTO.setLeftPeriod(reportResultAggregation.getPeriod().getLabel());
		reportDTO.setLeftColor("#" + Colors.makeGoodColorForSerieElement(1,1));

		int notNullValues = 0;
		double total = 0;
		for (ReportResultIndicatorAggregation reportResultIndicatorAggregation : reportResultAggregation.getReportResultIndicatorAggregationList()) {
			Double totalValue = reportResultIndicatorAggregation.getTotalValue();
			if (totalValue > 0) notNullValues++;
			total += totalValue;
		}

		int currentNotNullValue = 0;

		for (ReportResultIndicatorAggregation reportResultIndicatorAggregation : reportResultAggregation.getReportResultIndicatorAggregationList()) {
			ReportLineDTO reportLineDTO = new ReportLineDTO(reportResultIndicatorAggregation.getIndicator());

			reportLineDTO.setLeftScope1Value(reportResultIndicatorAggregation.getScope1Value());
			reportLineDTO.setLeftScope2Value(reportResultIndicatorAggregation.getScope2Value());
			reportLineDTO.setLeftScope3Value(reportResultIndicatorAggregation.getScope3Value());
			reportLineDTO.setLeftOutOfScopeValue(reportResultIndicatorAggregation.getOutOfScopeValue());

			Double totalValue = reportResultIndicatorAggregation.getTotalValue();
			if (totalValue > 0) {
				reportLineDTO.setColor("#" + Colors.makeGoodColorForSerieElement(currentNotNullValue + 1, notNullValues));
				currentNotNullValue++;
			}
			reportLineDTO.setLeftPercentage(100 * totalValue / total);
			reportDTO.getReportLines().add(reportLineDTO);
		}

		return reportDTO;
	}

}
