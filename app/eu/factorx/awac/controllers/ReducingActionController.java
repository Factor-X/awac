package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.awac.get.*;
import eu.factorx.awac.dto.awac.post.CreateReducingActionDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.knowledge.ReducingAction;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.ReducingActionService;
import eu.factorx.awac.service.ScopeService;
import eu.factorx.awac.service.UnitService;

@org.springframework.stereotype.Controller
public class ReducingActionController extends AbstractController {

	@Autowired
	private ReducingActionService reducingActionService;

	@Autowired
	private SecuredController securedController;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private CodeLabelService codeLabelService;

	@Autowired
	private ScopeService scopeService;

	@Autowired
	private UnitService unitService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadActions() {
		List<ReducingActionDTO> reducingActionDTOs = new ArrayList<>();
		List<ReducingAction> reducingActions = reducingActionService.findByOrganization(securedController.getCurrentUser().getOrganization());
		for (ReducingAction reducingAction : reducingActions) {
			reducingActionDTOs.add(conversionService.convert(reducingAction, ReducingActionDTO.class));
		}
		List<CodeListDTO> codeListDTOs = new ArrayList<>();
		codeListDTOs.add(toCodeListDTO(CodeList.REDUCING_ACTION_TYPE, securedController.getDefaultLanguage()));
		codeListDTOs.add(toCodeListDTO(CodeList.REDUCING_ACTION_STATUS, securedController.getDefaultLanguage()));
		List<UnitDTO> gwpUnitDTOs = new ArrayList<>();
		List<Unit> gwpUnits = unitService.findByCategoryCode(UnitCategoryCode.GWP);
		for (Unit gwpUnit : gwpUnits) {
			gwpUnitDTOs.add(new UnitDTO(gwpUnit.getUnitCode().getKey(), gwpUnit.getSymbol()));
		}
		return ok(new ReducingActionDTOList(reducingActionDTOs, codeListDTOs, gwpUnitDTOs));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result saveAction() {
		ReducingAction reducingAction = createReducingAction(extractDTOFromRequest(CreateReducingActionDTO.class));
		reducingActionService.saveOrUpdate(reducingAction);

		return ok();
	}

	private ReducingAction createReducingAction(CreateReducingActionDTO dto) {
		Account currentUser = securedController.getCurrentUser();
		Scope scope = null;
		if (ScopeTypeCode.ORG.getKey().equals(dto.getScopeTypeKey())) {
			scope = currentUser.getOrganization();
		} else {
			scope = scopeService.findById(Long.valueOf(dto.getScopeId()));
			validateUserRightsForScope(currentUser, scope);
		}

		// create new ReducingAction (mandatory fields)
		ReducingAction reducingAction = new ReducingAction(dto.getTitle(), scope, ReducingActionTypeCode.valueOf(dto.getTypeKey()));

		// optional fields
		reducingAction.setComment(dto.getComment());
		reducingAction.setDueDate(dto.getDueDate());
		reducingAction.setExpectedPaybackTime(dto.getExpectedPaybackTime());
		reducingAction.setFinancialBenefit(dto.getFinancialBenefit());
		reducingAction.setGhgBenefit(dto.getGhgBenefit());
		String ghgBenefitUnitKey = dto.getGhgBenefitUnitKey();
		if (StringUtils.isNotBlank(ghgBenefitUnitKey)) {
			Unit ghgBenefitUnit = unitService.findByCode(new UnitCode(ghgBenefitUnitKey));
			reducingAction.setGhgBenefitUnit(ghgBenefitUnit);
		}
		reducingAction.setInvestmentCost(dto.getInvestmentCost());
		reducingAction.setPhysicalMeasure(dto.getPhysicalMeasure());
		reducingAction.setResponsiblePerson(dto.getResponsiblePerson());
		reducingAction.setWebSite(dto.getWebSite());

		return reducingAction;
	}

	private CodeListDTO toCodeListDTO(CodeList codeList, LanguageCode lang) {
		List<CodeLabel> codeLabels = new ArrayList<>(codeLabelService.findCodeLabelsByList(codeList).values());
		List<CodeLabelDTO> codeLabelDTOs = new ArrayList<>();
		for (CodeLabel codeLabel : codeLabels) {
			codeLabelDTOs.add(new CodeLabelDTO(codeLabel.getKey(), codeLabel.getLabel(lang)));
		}
		return new CodeListDTO(codeList.name(), codeLabelDTOs);
	}

	private static void validateUserRightsForScope(Account currentUser, Scope scope) {
		if (!scope.getOrganization().equals(currentUser.getOrganization())) {
			throw new RuntimeException("The user '" + currentUser.getIdentifier() + "' is not allowed to update data of organization '" + scope.getOrganization() + "'");
		}
	}

}
