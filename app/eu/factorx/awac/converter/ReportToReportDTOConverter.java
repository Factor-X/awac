package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.dto.awac.get.ReportLineDTO;
import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.models.reporting.Report;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ReportToReportDTOConverter implements Converter<Report, ReportDTO> {

	@Autowired
	private IndicatorService indicatorService;

	@Autowired
	private CodeLabelService codeLabelService;

	@Override
	public ReportDTO convert(Report report) {
		ReportDTO reportDTO = new ReportDTO();

		// group data by indicator name
		Map<String, List<BaseActivityResult>> dataByIndicator = new HashMap<>();
		for (String indicatorName : indicatorService.findAllIndicatorNames()) {
			dataByIndicator.put(indicatorName, new ArrayList<BaseActivityResult>());
		}
		for (BaseActivityResult baseActivityResult : report.getActivityResults()) {
			String indicatorName = baseActivityResult.getIndicator().getName();
			dataByIndicator.get(indicatorName).add(baseActivityResult);
		}

		// build a report line for each entry (=> adding values of each activity data linked to the indicator name.)
		for (Entry<String, List<BaseActivityResult>> indicatorData : dataByIndicator.entrySet()) {
			String indicatorName = indicatorData.getKey();
			ReportLineDTO reportLineDTO = new ReportLineDTO(indicatorName);

			for (BaseActivityResult baseActivityResult : indicatorData.getValue()) {
				Double numericValue = baseActivityResult.getNumericValue();
				IndicatorIsoScopeCode isoScope = baseActivityResult.getIndicator().getIsoScope();

				if (IndicatorIsoScopeCode.SCOPE1.equals(isoScope)) {
					reportLineDTO.addScope1Value(numericValue);
				} else if (IndicatorIsoScopeCode.SCOPE2.equals(isoScope)) {
					reportLineDTO.addScope2Value(numericValue);
				} else if (IndicatorIsoScopeCode.SCOPE3.equals(isoScope)) {
					reportLineDTO.addScope3Value(numericValue);
				} else if (IndicatorIsoScopeCode.OUT_OF_SCOPE.equals(isoScope)) {
					reportLineDTO.addOutOfScopeValue(numericValue);
				}
			}
			reportDTO.getReportLines().add(reportLineDTO);
		}

		return reportDTO;
	}

}
