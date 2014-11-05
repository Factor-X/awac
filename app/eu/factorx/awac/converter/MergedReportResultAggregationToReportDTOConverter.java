package eu.factorx.awac.converter;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.dto.awac.get.ReportLineDTO;
import eu.factorx.awac.service.impl.reporting.MergedReportResultAggregation;
import eu.factorx.awac.service.impl.reporting.MergedReportResultIndicatorAggregation;
import eu.factorx.awac.util.Colors;

@Component
public class MergedReportResultAggregationToReportDTOConverter implements Converter<MergedReportResultAggregation, ReportDTO> {

	@Override
	public ReportDTO convert(MergedReportResultAggregation mergedReportResultAggregation) {
		ReportDTO reportDTO = new ReportDTO();

        reportDTO.setLeftScope1Color("#" + Colors.interpolate(Colors.makeGoodColorForSerieElement(0, 2), "ffffff", 0, 4));
        reportDTO.setLeftScope2Color("#" + Colors.interpolate(Colors.makeGoodColorForSerieElement(0, 2), "ffffff", 1, 4));
        reportDTO.setLeftScope3Color("#" + Colors.interpolate(Colors.makeGoodColorForSerieElement(0, 2), "ffffff", 2, 4));

        reportDTO.setRightScope1Color("#" + Colors.interpolate(Colors.makeGoodColorForSerieElement(1, 2), "ffffff", 0, 4));
        reportDTO.setRightScope2Color("#" + Colors.interpolate(Colors.makeGoodColorForSerieElement(1, 2), "ffffff", 1, 4));
        reportDTO.setRightScope3Color("#" + Colors.interpolate(Colors.makeGoodColorForSerieElement(1, 2), "ffffff", 2, 4));


        reportDTO.setLeftPeriod(mergedReportResultAggregation.getLeftPeriod().getLabel());
		reportDTO.setRightPeriod(mergedReportResultAggregation.getRightPeriod().getLabel());

		reportDTO.setLeftColor("#" + Colors.makeGoodColorForSerieElement(0, 2));
		reportDTO.setRightColor("#" + Colors.makeGoodColorForSerieElement(1, 2));

		List<MergedReportResultIndicatorAggregation> list = mergedReportResultAggregation.getMergedReportResultIndicatorAggregationList();

		double leftTotal = 0;
		for (MergedReportResultIndicatorAggregation mergedReportResultIndicatorAggregation : list) {
			leftTotal += mergedReportResultIndicatorAggregation.getLeftTotalValue();
		}
		double rightTotal = 0;
		for (MergedReportResultIndicatorAggregation mergedReportResultIndicatorAggregation : list) {
			rightTotal += mergedReportResultIndicatorAggregation.getRightTotalValue();
		}

		int nNotNull = 0;
		for (MergedReportResultIndicatorAggregation reportResultIndicatorAggregation : list) {
			if (reportResultIndicatorAggregation.getLeftTotalValue() + reportResultIndicatorAggregation.getRightTotalValue() > 0) {
				nNotNull++;
			}
		}

		int currentNotNull = 0;
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
				currentNotNull++;
				reportLineDTO.setColor("#" + Colors.makeGoodColorForSerieElement(currentNotNull , nNotNull));
			}

			// percentage
			reportLineDTO.setLeftPercentage(100 * leftTotalValue / leftTotal);
			reportLineDTO.setRightPercentage(100 * rightTotalValue / rightTotal);

			reportDTO.getReportLines().add(reportLineDTO);
		}

		return reportDTO;
	}

}
