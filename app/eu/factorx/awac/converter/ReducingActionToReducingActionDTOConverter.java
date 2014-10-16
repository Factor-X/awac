package eu.factorx.awac.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.ReducingActionDTO;
import eu.factorx.awac.dto.awac.get.ScopeDTO;
import eu.factorx.awac.dto.awac.get.UnitDTO;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.ScopeTypeCode;
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

		reducingActionDTO.setTitle(reducingAction.getTitle());
		ScopeDTO scopeDTO = null;
		Scope scope = reducingAction.getScope();
		ScopeTypeCode scopeType = scope.getScopeType();
		if (ScopeTypeCode.ORG.equals(scopeType)) {
			scopeDTO = organizationToOrganizationDTOConverter.convert((Organization) scope);
		} else if (ScopeTypeCode.SITE.equals(scopeType)) {
			scopeDTO = siteToSiteDTOConverter.convert((Site) scope);
		}
		reducingActionDTO.setScope(scopeDTO);
		reducingActionDTO.setTypeKey(reducingAction.getType().getKey());
		reducingActionDTO.setStatusKey(reducingAction.getStatus().getKey());
		reducingActionDTO.setCompletionDate(reducingAction.getCompletionDate());
		reducingActionDTO.setPhysicalMeasure(reducingAction.getPhysicalMeasure());

		reducingActionDTO.setGhgBenefit(reducingAction.getGhgBenefit());
		Unit unit = reducingAction.getGhgBenefitUnit();
		if (unit != null) {			
			reducingActionDTO.setGhgBenefitUnit(new UnitDTO(unit.getUnitCode().getKey(), unit.getSymbol()));
		}
		reducingActionDTO.setFinancialBenefit(reducingAction.getFinancialBenefit());
		reducingActionDTO.setInvestmentCost(reducingAction.getInvestmentCost());
		reducingActionDTO.setExpectedPaybackTime(reducingAction.getExpectedPaybackTime());
		reducingActionDTO.setDueDate(reducingAction.getDueDate());

		reducingActionDTO.setWebSite(reducingAction.getWebSite());
		reducingActionDTO.setResponsiblePerson(reducingAction.getResponsiblePerson());
		reducingActionDTO.setComment(reducingAction.getComment());
		
		return reducingActionDTO;
	}

}
