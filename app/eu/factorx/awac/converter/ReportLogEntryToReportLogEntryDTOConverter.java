package eu.factorx.awac.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.*;
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.service.impl.reporting.*;

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

		if (entry instanceof LowerRankInGroup) {
			LowerRankInGroup lrig = (LowerRankInGroup) entry;

			return new LowerRankInGroupDTO(
				lrig.getBaseActivityData().getActivityCategory().getKey(),
				lrig.getBaseActivityData().getActivitySubCategory().getKey(),
				lrig.getBaseActivityData().getActivityType().getKey(),
				lrig.getBaseActivityData().getActivitySource().getKey(),
				lrig.getBaseActivityData().getValue(),
				lrig.getBaseActivityData().getUnit().getSymbol()
			);
		}

		if (entry instanceof NoSuitableIndicator) {
			NoSuitableIndicator nsi = (NoSuitableIndicator) entry;

			return new NoSuitableIndicatorDTO(
				nsi.getBaseActivityData().getActivityCategory().getKey(),
				nsi.getBaseActivityData().getActivitySubCategory().getKey(),
				nsi.getBaseActivityData().getActivityType().getKey(),
				nsi.getBaseActivityData().getActivitySource().getKey(),
				nsi.getBaseActivityData().getValue(),
				nsi.getBaseActivityData().getUnit().getSymbol()
			);
		}

		return null;
	}

}
