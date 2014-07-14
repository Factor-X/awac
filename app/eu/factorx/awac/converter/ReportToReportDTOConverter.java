package eu.factorx.awac.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.convert.converter.Converter;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.dto.awac.get.ReportLineDTO;
import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.models.reporting.Report;

public class ReportToReportDTOConverter implements Converter<Report, ReportDTO> {

	@Override
	public ReportDTO convert(Report report) {
		ReportDTO reportDTO = new ReportDTO();

		// group data by indicator name (note: it could exist several indicators with same name but different ISO scope)
		Map<String, List<BaseActivityResult>> dataByIndicator = new HashMap<>();
		for (BaseActivityResult baseActivityResult : report.getActivityResults()) {
			Indicator indicator = baseActivityResult.getIndicator();
			String indicatorName = indicator.getIdentifier();
			if (!dataByIndicator.containsKey(indicatorName)) {
				dataByIndicator.put(indicatorName, new ArrayList<BaseActivityResult>());
			}
			dataByIndicator.get(indicatorName).add(baseActivityResult);
		}

		// build a report line for each indicator name (=> adding values of each activity data linked to the indicator name)
		for (Entry<String, List<BaseActivityResult>> indicatorData : dataByIndicator.entrySet()) {

			ReportLineDTO reportLineDTO = new ReportLineDTO(indicatorData.getKey());

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
