package eu.factorx.awac.converter;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.shared.ReducingActionDTO;
import eu.factorx.awac.models.knowledge.ReducingAction;
import eu.factorx.awac.models.knowledge.Unit;

@Component
public class ReducingActionToReducingActionDTOConverter implements Converter<ReducingAction, ReducingActionDTO> {

	@Autowired
	private OrganizationToOrganizationDTOConverter organizationToOrganizationDTOConverter;

	@Autowired
	private SiteToSiteDTOConverter siteToSiteDTOConverter;

	@Override
	public ReducingActionDTO convert(ReducingAction reducingAction) {
		ReducingActionDTO reducingActionDTO = new ReducingActionDTO();

		reducingActionDTO.setId(reducingAction.getId());
		reducingActionDTO.setTitle(reducingAction.getTitle());
		reducingActionDTO.setScopeTypeKey(reducingAction.getScope().getScopeType().getKey());
		reducingActionDTO.setScopeId(reducingAction.getScope().getId());
		reducingActionDTO.setTypeKey(reducingAction.getType().getKey());
		reducingActionDTO.setStatusKey(reducingAction.getStatus().getKey());
		reducingActionDTO.setCompletionDate(fromJodaTime(reducingAction.getCompletionDate()));
		reducingActionDTO.setPhysicalMeasure(reducingAction.getPhysicalMeasure());

		reducingActionDTO.setGhgBenefit(reducingAction.getGhgBenefit());
		Unit unit = reducingAction.getGhgBenefitUnit();
		if (unit != null) {
			reducingActionDTO.setGhgBenefitUnitKey(unit.getUnitCode().getKey());
		}
		reducingActionDTO.setFinancialBenefit(reducingAction.getFinancialBenefit());
		reducingActionDTO.setInvestmentCost(reducingAction.getInvestmentCost());
		reducingActionDTO.setExpectedPaybackTime(reducingAction.getExpectedPaybackTime());
		reducingActionDTO.setDueDate(fromJodaTime(reducingAction.getDueDate()));

		reducingActionDTO.setWebSite(reducingAction.getWebSite());
		reducingActionDTO.setResponsiblePerson(reducingAction.getResponsiblePerson());
		reducingActionDTO.setComment(reducingAction.getComment());

		return reducingActionDTO;
	}

	private static Date fromJodaTime(DateTime dateTime) {
		if (dateTime == null) {
			return null;
		}
		return new Date(dateTime.getMillis());
	}

}
