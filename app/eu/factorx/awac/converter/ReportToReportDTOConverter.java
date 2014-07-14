package eu.factorx.awac.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.dto.awac.get.ReportLineDTO;
import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.models.reporting.Report;
import eu.factorx.awac.service.CodeLabelService;

public class ReportToReportDTOConverter implements Converter<Report, ReportDTO> {

	@Autowired
	private CodeLabelService codeLabelService;

	@Override
	public ReportDTO convert(Report report) {
		ReportDTO reportDTO = new ReportDTO();

		// group data by indicator name
		Map<String, List<BaseActivityResult>> dataByIndicator = new HashMap<>();
		for (BaseActivityResult baseActivityResult : report.getActivityResults()) {
			Indicator indicator = baseActivityResult.getIndicator();
			String indicatorIdentifier = indicator.getIdentifier();
			if (!dataByIndicator.containsKey(indicatorIdentifier)) {
				dataByIndicator.put(indicatorIdentifier, new ArrayList<BaseActivityResult>());
			}
			dataByIndicator.get(indicatorIdentifier).add(baseActivityResult);
		}

		// build a report line for each indicator category (=> adding values of each activity data linked to the indicator cat.)
		for (Entry<String, List<BaseActivityResult>> indicatorData : dataByIndicator.entrySet()) {

			String indicatorIdentifier = indicatorData.getKey();

			ReportLineDTO reportLineDTO = new ReportLineDTO(indicatorIdentifier);

			for (BaseActivityResult baseActivityResult : indicatorData.getValue()) {
				Double numericValue = baseActivityResult.getNumericValue();
				IndicatorIsoScopeCode isoScope = baseActivityResult.getIndicator().getIsoScope();

				if (IndicatorIsoScopeCode.SCOPE1.equals(isoScope)) {
					reportLineDTO.addScope1Value(numericValue);
				} else if (IndicatorIsoScopeCode.SCOPE2.equals(isoScope)) {
					reportLineDTO.addScope2Value(numericValue);
				} else if (IndicatorIsoScopeCode.SCOPE3.equals(isoScope)) {
					reportLineDTO.addScope3Value(numericValue);
				}
			}
			reportDTO.getReportLines().add(reportLineDTO);
		}

		return reportDTO;
	}

}
