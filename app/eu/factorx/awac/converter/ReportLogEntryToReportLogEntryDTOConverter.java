package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.ReportLogContributionEntryDTO;
import eu.factorx.awac.dto.awac.get.ReportLogEntryDTO;
import eu.factorx.awac.dto.awac.get.ReportLogNoSuitableFactorEntryDTO;
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.service.impl.reporting.Contribution;
import eu.factorx.awac.service.impl.reporting.NoSuitableFactor;
import eu.factorx.awac.service.impl.reporting.ReportLogEntry;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReportLogEntryToReportLogEntryDTOConverter implements Converter<ReportLogEntry, ReportLogEntryDTO> {
	@Override
	public ReportLogEntryDTO convert(ReportLogEntry entry) {
		if (entry instanceof Contribution) {

			BaseActivityResult bar = ((Contribution) entry).getBar();

			BaseIndicator baseIndicator = bar.getBaseIndicator();
			BaseActivityData activityData = bar.getActivityData();
			Factor factor = bar.getFactor();

			return new ReportLogContributionEntryDTO(
				baseIndicator.getActivityCategory().getKey(),
				baseIndicator.getActivitySubCategory().getKey(),
				baseIndicator.getUnit().getSymbol(),
				activityData.getValue(),
				activityData.getUnit().getSymbol(),
				baseIndicator.getIndicatorCategory().getKey(),
				activityData.getActivityType().getKey(),
				activityData.getActivitySource().getKey(),
				factor.getUnitIn().getSymbol(),
				factor.getUnitOut().getSymbol(),
				factor.getCurrentValue(),
				bar.getNumericValue());

		}

		if (entry instanceof NoSuitableFactor) {

			BaseActivityData activityData = ((NoSuitableFactor) entry).getBad();
			BaseIndicator baseIndicator = ((NoSuitableFactor) entry).getBaseIndicator();

			return new ReportLogNoSuitableFactorEntryDTO(
				baseIndicator.getActivityCategory().getKey(),
				baseIndicator.getActivitySubCategory().getKey(),
				baseIndicator.getUnit().getSymbol(),
				activityData.getValue(),
				activityData.getUnit().getSymbol(),
				baseIndicator.getIndicatorCategory().getKey(),
				activityData.getActivityType().getKey(),
				activityData.getActivitySource().getKey());

		}

		return null;

	}

}
