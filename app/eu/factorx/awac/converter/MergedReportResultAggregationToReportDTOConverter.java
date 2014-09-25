package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.dto.awac.get.ReportLineDTO;
import eu.factorx.awac.service.impl.reporting.MergedReportResultAggregation;
import eu.factorx.awac.service.impl.reporting.MergedReportResultIndicatorAggregation;
import eu.factorx.awac.util.Colors;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class MergedReportResultAggregationToReportDTOConverter implements Converter<MergedReportResultAggregation, ReportDTO> {

	@Override
	public ReportDTO convert(MergedReportResultAggregation mergedReportResultAggregation) {
		ReportDTO reportDTO = new ReportDTO();
		List<MergedReportResultIndicatorAggregation> list = mergedReportResultAggregation.getMergedReportResultIndicatorAggregationList();

		double leftTotal = 0;
		for (MergedReportResultIndicatorAggregation mergedReportResultIndicatorAggregation : list) {
			leftTotal += mergedReportResultIndicatorAggregation.getLeftTotalValue();
		}
		double rightTotal = 0;
		for (MergedReportResultIndicatorAggregation mergedReportResultIndicatorAggregation : list) {
			rightTotal += mergedReportResultIndicatorAggregation.getRightTotalValue();
		}

		int index = 0;
		for (MergedReportResultIndicatorAggregation reportResultIndicatorAggregation : list) {
			ReportLineDTO reportLineDTO = new ReportLineDTO(reportResultIndicatorAggregation.getIndicator());

			// left
			reportLineDTO.setLeftScope1Value(reportResultIndicatorAggregation.getLeftScope1Value());
			reportLineDTO.setLeftScope2Value(reportResultIndicatorAggregation.getLeftScope2Value());
			reportLineDTO.setLeftScope3Value(reportResultIndicatorAggregation.getLeftScope3Value());
			reportLineDTO.setLeftOutOfScopeValue(reportResultIndicatorAggregation.getLeftOutOfScopeValue());

			// right
			reportLineDTO.setRightScope1Value(reportResultIndicatorAggregation.getRightScope1Value());
			reportLineDTO.setRightScope2Value(reportResultIndicatorAggregation.getRightScope2Value());
			reportLineDTO.setRightScope3Value(reportResultIndicatorAggregation.getRightScope3Value());
			reportLineDTO.setRightOutOfScopeValue(reportResultIndicatorAggregation.getRightOutOfScopeValue());

			// total values
			Double leftTotalValue = reportResultIndicatorAggregation.getLeftTotalValue();
			Double rightTotalValue = reportResultIndicatorAggregation.getRightTotalValue();

			// color
			if (leftTotalValue + rightTotalValue > 0) {
				reportLineDTO.setColor("#" + Colors.makeGoodColorForSerieElement(index + 1, list.size()));
				index++;
			}

			// percentage
			reportLineDTO.setLeftPercentage(100 * leftTotalValue / leftTotal);
			reportLineDTO.setRightPercentage(100 * rightTotalValue / rightTotal);

			reportDTO.getReportLines().add(reportLineDTO);
		}

		return reportDTO;
	}

}
