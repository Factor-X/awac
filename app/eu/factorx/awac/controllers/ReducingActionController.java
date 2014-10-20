package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.awac.get.CodeLabelDTO;
import eu.factorx.awac.dto.awac.get.CodeListDTO;
import eu.factorx.awac.dto.awac.get.ReducingActionDTOList;
import eu.factorx.awac.dto.awac.get.UnitDTO;
import eu.factorx.awac.dto.awac.shared.ReducingActionDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.knowledge.ReducingAction;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.service.*;

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
	private UnitService unitService;
	
	@Autowired
	private ScopeService scopeService;

	@Autowired
	private AccountSiteAssociationService accountSiteAssociationService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadActions() {
		Account currentUser = securedController.getCurrentUser();
		List<Scope> authorizedScopes = getAuthorizedScopes(currentUser);

		ReducingActionDTOList result = new ReducingActionDTOList(getReducingActionDTOs(authorizedScopes),
				getCodeListDTOs(CodeList.REDUCING_ACTION_TYPE, CodeList.REDUCING_ACTION_STATUS),
				getUnitDTOsByCategory(UnitCategoryCode.GWP));

		return ok(result);
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result saveAction() {
		ReducingActionDTO dto = extractDTOFromRequest(ReducingActionDTO.class);

		Account currentUser = securedController.getCurrentUser();

		Scope scope;
		if (ScopeTypeCode.ORG.getKey().equals(dto.getScopeTypeKey())) {
			scope = currentUser.getOrganization();
		} else {
			scope = scopeService.findById(dto.getScopeId());
			validateUserRightsForScope(currentUser, scope);
		}

		ReducingAction reducingAction;
		if (dto.getId() == null) {
			reducingAction = new ReducingAction();
		} else {
			reducingAction = reducingActionService.findById(dto.getId());
		}

		reducingAction.setTitle(dto.getTitle());
		reducingAction.setScope(scope);
		reducingAction.setType(ReducingActionTypeCode.valueOf(dto.getTypeKey()));
		reducingAction.setStatus(ReducingActionStatusCode.valueOf(dto.getStatusKey()));
		reducingAction.setCompletionDate(toJodaTime(dto.getCompletionDate()));
		reducingAction.setPhysicalMeasure(dto.getPhysicalMeasure());

		reducingAction.setGhgBenefit(dto.getGhgBenefit());
		String ghgBenefitUnitKey = dto.getGhgBenefitUnitKey();
		if (StringUtils.isNotBlank(ghgBenefitUnitKey)) {
			reducingAction.setGhgBenefitUnit(unitService.findByCode(new UnitCode(ghgBenefitUnitKey)));
		}

		reducingAction.setFinancialBenefit(dto.getFinancialBenefit());
		reducingAction.setInvestmentCost(dto.getInvestmentCost());
		reducingAction.setExpectedPaybackTime(dto.getExpectedPaybackTime());
		reducingAction.setDueDate(toJodaTime(dto.getDueDate()));

		reducingAction.setWebSite(dto.getWebSite());
		reducingAction.setResponsiblePerson(dto.getResponsiblePerson());
		reducingAction.setComment(dto.getComment());

		reducingActionService.saveOrUpdate(reducingAction);
		return ok();
	}

	private void markAsDone(ReducingAction reducingAction) {
		reducingAction.setStatus(ReducingActionStatusCode.DONE);
		reducingAction.setCompletionDate(new DateTime());
	}

	private List<Scope> getAuthorizedScopes(Account account) {
		List<Scope> res = new ArrayList<>();
		// add organization
		res.add(account.getOrganization());
		// add authorized sites
		for (AccountSiteAssociation accountSiteAssociation : accountSiteAssociationService.findByAccount(account)) {
			res.add(accountSiteAssociation.getSite());
		}
		return res;
	}

	private List<ReducingActionDTO> getReducingActionDTOs(List<Scope> authorizedScopes) {
		List<ReducingActionDTO> reducingActionDTOs = new ArrayList<>();
		List<ReducingAction> reducingActions = reducingActionService.findByScopes(authorizedScopes);
		for (ReducingAction reducingAction : reducingActions) {
			reducingActionDTOs.add(conversionService.convert(reducingAction, ReducingActionDTO.class));
		}
		return reducingActionDTOs;
	}

	private List<UnitDTO> getUnitDTOsByCategory(UnitCategoryCode category) {
		List<UnitDTO> res = new ArrayList<>();
		List<Unit> units = unitService.findByCategoryCode(category);
		for (Unit unit : units) {
			res.add(new UnitDTO(unit.getUnitCode().getKey(), unit.getSymbol()));
		}
		return res;
	}

	private List<CodeListDTO> getCodeListDTOs(CodeList... codeLists) {
		LanguageCode userLanguage = securedController.getDefaultLanguage();
		List<CodeListDTO> res = new ArrayList<>();
		for (CodeList codeList : codeLists) {
			res.add(toCodeListDTO(codeList, userLanguage));
		}
		return res;
	}

	private CodeListDTO toCodeListDTO(CodeList codeList, LanguageCode lang) {
		List<CodeLabel> codeLabels = new ArrayList<>(codeLabelService.findCodeLabelsByList(codeList).values());
		List<CodeLabelDTO> codeLabelDTOs = new ArrayList<>();
		for (CodeLabel codeLabel : codeLabels) {
			codeLabelDTOs.add(new CodeLabelDTO(codeLabel.getKey(), codeLabel.getLabel(lang)));
		}
		return new CodeListDTO(codeList.name(), codeLabelDTOs);
	}

	private void validateUserRightsForScope(Account currentUser, Scope scope) {
		List<Scope> authorizedScopes = getAuthorizedScopes(currentUser);
		if (!authorizedScopes.contains(scope)) {
			throw new RuntimeException("The user '" + currentUser.getIdentifier() + "' is not allowed to update data for scope '" + scope + "'");
		}
	}

	private static DateTime toJodaTime(Date date) {
		if (date == null) {
			return null;
		}
		return new DateTime(date.getTime());
	}

}
