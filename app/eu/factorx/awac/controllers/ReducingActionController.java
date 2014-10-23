package eu.factorx.awac.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Number;

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
import eu.factorx.awac.dto.awac.post.FilesUploadedDTO;
import eu.factorx.awac.dto.awac.shared.ReducingActionDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.knowledge.ReducingAction;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexRuntimeException;

@org.springframework.stereotype.Controller
public class ReducingActionController extends AbstractController {

	private static final int TITLE_COLUMN = 0;
	private static final int SCOPE_COLUMN = 1;
	private static final int TYPE_COLUMN = 2;
	private static final int STATUS_COLUMN = 3;
	private static final int COMPLETION_DATE_COLUMN = 4;
	private static final int PHYSICAL_MEASURE_COLUMN = 5;
	private static final int GHG_BENEFIT_COLUMN = 6;
	private static final int GHG_BENEFIT_UNIT_COLUMN = 7;
	private static final int FINANCIAL_BENEFIT_COLUMN = 8;
	private static final int INVESTMENT_COLUMN = 9;
	private static final int EXPECTED_PAYBACK_TIME_COLUMN = 10;
	private static final int DUE_DATE_COLUMN = 11;
	private static final int WEBSITE_COLUMN = 12;
	private static final int RESPONSIBLE_PERSON_COLUMN = 13;
	private static final int COMMENT_COLUMN = 14;

	private static final String CURRENCY_FORMAT = "#,##0.00 €#164;";
	private static final String REAL_NUMBER_FORMAT = "#,##0.00";
	private static final String DATE_FORMAT = "dd/MM/yyyy";

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

	@Autowired
	private StoredFileService storedFileService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadActions() {
		Account currentUser = securedController.getCurrentUser();
		List<Scope> authorizedScopes = securedController.getAuthorizedScopes(currentUser);

		ReducingActionDTOList result = new ReducingActionDTOList(getReducingActionDTOs(authorizedScopes),
				getCodeListDTOs(CodeList.REDUCING_ACTION_TYPE, CodeList.REDUCING_ACTION_STATUS),
				getUnitDTOsByCategory(UnitCategoryCode.GWP));

		return ok(result);
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result getActionsAsXls() throws WriteException, IOException {
		Account currentUser = securedController.getCurrentUser();
		List<Scope> authorizedScopes = securedController.getAuthorizedScopes(currentUser);
		List<ReducingAction> reducingActions = reducingActionService.findByScopes(authorizedScopes);

		byte[] content = getExcelExport(reducingActions);

		response().setContentType("application/octet-stream");
		String fileName = "export_actions_" + new DateTime().toString("yyyyMMdd'-'HH'h'mm") + ".xls";
		response().setHeader("Content-Disposition", "attachment; filename=" + fileName);
		return ok(content);
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
		ReducingActionTypeCode actionType = ReducingActionTypeCode.valueOf(dto.getTypeKey());
		reducingAction.setType(actionType);

		ReducingActionStatusCode status = ReducingActionStatusCode.valueOf(dto.getStatusKey());
		reducingAction.setStatus(status);
		Date completionDate = dto.getCompletionDate();
		if (ReducingActionStatusCode.DONE.equals(status) && (completionDate == null)) {
			reducingAction.setCompletionDate(new DateTime());
		} else if (!ReducingActionStatusCode.DONE.equals(status)) {
			reducingAction.setCompletionDate(null);
		} else {
			reducingAction.setCompletionDate(toJodaTime(completionDate));
		}

		reducingAction.setPhysicalMeasure(dto.getPhysicalMeasure());
		if (ReducingActionTypeCode.BETTER_METHOD.equals(actionType)) {
			reducingAction.setGhgBenefit(null);
			reducingAction.setGhgBenefitUnit(null);
		} else {
			reducingAction.setGhgBenefit(dto.getGhgBenefit());
			String ghgBenefitUnitKey = dto.getGhgBenefitUnitKey();
			if (StringUtils.isNotBlank(ghgBenefitUnitKey)) {
				reducingAction.setGhgBenefitUnit(unitService.findByCode(new UnitCode(ghgBenefitUnitKey)));
			}
		}
		reducingAction.setFinancialBenefit(dto.getFinancialBenefit());
		reducingAction.setInvestmentCost(dto.getInvestmentCost());
		reducingAction.setExpectedPaybackTime(dto.getExpectedPaybackTime());
		reducingAction.setDueDate(toJodaTime(dto.getDueDate()));

		reducingAction.setWebSite(dto.getWebSite());
		reducingAction.setResponsiblePerson(dto.getResponsiblePerson());
		reducingAction.setComment(dto.getComment());

		List<StoredFile> documents = new ArrayList<>();
		for (FilesUploadedDTO file : dto.getFiles()) {
			documents.add(storedFileService.findById(file.getId()));
		}
		if (dto.getId() == null) {
			reducingAction.setDocuments(documents);
		} else {			
			reducingAction.getDocuments().clear();
			reducingAction.getDocuments().addAll(documents);
		}

		reducingActionService.saveOrUpdate(reducingAction);
		return ok(conversionService.convert(reducingAction, ReducingActionDTO.class));
	}

	private byte[] getExcelExport(List<ReducingAction> reducingActions) throws WriteException, IOException {
		LanguageCode userLanguage = securedController.getDefaultLanguage();

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		// Headers cell font and format
		WritableFont headersFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		WritableCellFormat headersFormat = new WritableCellFormat(headersFont);
		headersFormat.setAlignment(Alignment.LEFT);
		headersFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

		// Default cell font and format
		WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

		// Date cell format
		WritableCellFormat dateCellFormat = new WritableCellFormat(cellFont, new DateFormat(DATE_FORMAT));

		// Real Number cell format
		WritableCellFormat realNumberCellFormat = new WritableCellFormat(new NumberFormat(REAL_NUMBER_FORMAT));

		// Currency cell format
		WritableCellFormat currencyCellFormat = new WritableCellFormat(new NumberFormat(CURRENCY_FORMAT));

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale(userLanguage.getKey()));
		WritableWorkbook wb = Workbook.createWorkbook(byteArrayOutputStream, wbSettings);
		WritableSheet sheet = wb.createSheet("export", 0);

		HashMap<String, CodeLabel> interfaceCodeLabels = codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_INTERFACE);

		sheet.addCell(new Label(TITLE_COLUMN, 0, getLabel("REDUCTION_ACTION_TITLE_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(SCOPE_COLUMN, 0, getLabel("REDUCTION_ACTION_SCOPE_TYPE_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(TYPE_COLUMN, 0, getLabel("REDUCTION_ACTION_TYPE_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(STATUS_COLUMN, 0, getLabel("REDUCTION_ACTION_STATUS_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(COMPLETION_DATE_COLUMN, 0, getLabel("REDUCTION_ACTION_COMPLETION_DATE_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(PHYSICAL_MEASURE_COLUMN, 0, getLabel("REDUCTION_ACTION_PHYSICAL_MEASURE_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(GHG_BENEFIT_COLUMN, 0, getLabel("REDUCTION_ACTION_GHG_BENEFIT_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(GHG_BENEFIT_UNIT_COLUMN, 0, getLabel("REDUCTION_ACTION_GHG_BENEFIT_UNIT_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(FINANCIAL_BENEFIT_COLUMN, 0, getLabel("REDUCTION_ACTION_FINANCIAL_BENEFIT_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(INVESTMENT_COLUMN, 0, getLabel("REDUCTION_ACTION_INVESTMENT_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(EXPECTED_PAYBACK_TIME_COLUMN, 0, getLabel("REDUCTION_ACTION_EXPECTED_PAYBACK_TIME_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(DUE_DATE_COLUMN, 0, getLabel("REDUCTION_ACTION_DUE_DATE_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(WEBSITE_COLUMN, 0, getLabel("REDUCTION_ACTION_WEBSITE_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(RESPONSIBLE_PERSON_COLUMN, 0, getLabel("REDUCTION_ACTION_RESPONSIBLE_PERSON_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));
		sheet.addCell(new Label(COMMENT_COLUMN, 0, getLabel("REDUCTION_ACTION_COMMENT_FIELD_TITLE", interfaceCodeLabels, userLanguage), headersFormat));

		HashMap<String, CodeLabel> typeCodeLabels = codeLabelService.findCodeLabelsByList(CodeList.REDUCING_ACTION_TYPE);
		HashMap<String, CodeLabel> statusCodeLabels = codeLabelService.findCodeLabelsByList(CodeList.REDUCING_ACTION_STATUS);

		int row = 1;
		for (ReducingAction reducingAction : reducingActions) {
			// title
			sheet.addCell(new Label(TITLE_COLUMN, row, StringUtils.trimToEmpty(reducingAction.getTitle()), cellFormat));
			// scope
			sheet.addCell(new Label(SCOPE_COLUMN, row, reducingAction.getScope().getName(), cellFormat));
			// type
			sheet.addCell(new Label(TYPE_COLUMN, row, getLabel(reducingAction.getType().getKey(), typeCodeLabels, userLanguage), cellFormat));
			// status
			sheet.addCell(new Label(STATUS_COLUMN, row, getLabel(reducingAction.getStatus().getKey(), statusCodeLabels, userLanguage), cellFormat));
			// completion date
			DateTime completionDate = reducingAction.getCompletionDate();
			if (completionDate != null) {
				sheet.addCell(new jxl.write.DateTime(COMPLETION_DATE_COLUMN, row, completionDate.toDate(), dateCellFormat));
			}
			// Physical measure
			sheet.addCell(new Label(PHYSICAL_MEASURE_COLUMN, row, StringUtils.trimToEmpty(reducingAction.getPhysicalMeasure()), cellFormat));
			// GHG Benefit
			Double ghgBenefit = reducingAction.getGhgBenefit();
			if (ghgBenefit != null) {
				sheet.addCell(new Number(GHG_BENEFIT_COLUMN, row, ghgBenefit, realNumberCellFormat));
			}
			// GHG Benefit Unit
			Unit ghgBenefitUnit = reducingAction.getGhgBenefitUnit();
			if (ghgBenefitUnit != null) {
				sheet.addCell(new Label(GHG_BENEFIT_UNIT_COLUMN, row, ghgBenefitUnit.getSymbol(), cellFormat));
			}
			// Financial Benefit
			Double financialBenefit = reducingAction.getFinancialBenefit();
			if (financialBenefit != null) {
				sheet.addCell(new Number(FINANCIAL_BENEFIT_COLUMN, row, financialBenefit, currencyCellFormat));
			}
			// Investment
			Double investmentCost = reducingAction.getInvestmentCost();
			if (investmentCost != null) {
				sheet.addCell(new Number(INVESTMENT_COLUMN, row, investmentCost, currencyCellFormat));
			}
			// Expected Payback Time
			sheet.addCell(new Label(EXPECTED_PAYBACK_TIME_COLUMN, row, StringUtils.trimToEmpty(reducingAction.getExpectedPaybackTime()), cellFormat));
			// Due Date
			DateTime dueDate = reducingAction.getDueDate();
			if (dueDate != null) {
				sheet.addCell(new jxl.write.DateTime(DUE_DATE_COLUMN, row, dueDate.toDate(), dateCellFormat));
			}
			// Website
			sheet.addCell(new Label(WEBSITE_COLUMN, row, StringUtils.trimToEmpty(reducingAction.getWebSite()), cellFormat));
			// Comment
			sheet.addCell(new Label(COMMENT_COLUMN, row, StringUtils.trimToEmpty(reducingAction.getComment()), cellFormat));

			row++;
		}

		wb.write();
		wb.close();

		return byteArrayOutputStream.toByteArray();
	}

	private String getLabel(String key, HashMap<String, CodeLabel> codeLabelsMap, LanguageCode userLanguage) {
		CodeLabel codeLabel = codeLabelsMap.get(key);
		if (codeLabel == null) {
			return key;
		}
		return codeLabel.getLabel(userLanguage);
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
		List<Scope> authorizedScopes = securedController.getAuthorizedScopes(currentUser);
		if (!authorizedScopes.contains(scope)) {
			throw new MyrmexRuntimeException(BusinessErrorType.NOT_YOUR_SCOPE, scope.getName());
		}
	}

	private static DateTime toJodaTime(Date date) {
		if (date == null) {
			return null;
		}
		return new DateTime(date.getTime());
	}

}
