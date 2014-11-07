package eu.factorx.awac.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.dto.awac.get.ReducingActionAdviceDTOList;
import eu.factorx.awac.dto.awac.post.FilesUploadedDTO;
import eu.factorx.awac.dto.awac.shared.ReducingActionAdviceDTO;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.ReducingActionAdvice;
import eu.factorx.awac.models.knowledge.ReducingActionAdviceBaseIndicatorAssociation;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.service.*;
import eu.factorx.awac.service.impl.reporting.LowRankMeasureWarning;
import eu.factorx.awac.service.impl.reporting.ReportLogEntry;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexFatalException;

@org.springframework.stereotype.Controller
public class ReducingActionAdviceController extends AbstractController {

	@Autowired
	private ReducingActionAdviceService reducingActionAdviceService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private BaseIndicatorService baseIndicatorService;

	@Autowired
	private AwacCalculatorService awacCalculatorService;

	@Autowired
	private StoredFileService storedFileService;

	@Autowired
	private ReportResultService reportResultService;

	@Autowired
	private PeriodService periodService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result computeAdvices(String periodKey) {
		Organization organization = securedController.getCurrentUser().getOrganization();
		Period period = periodService.findByCode(new PeriodCode(periodKey));
		return ok(new ReducingActionAdviceDTOList(computeAdviceDTOs(organization, period)));
	}

	/**
	 * Admin only!
	 */
	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadAdvices() {
		if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
			throw new MyrmexFatalException(BusinessErrorType.WRONG_RIGHT);
		}
		return ok(new ReducingActionAdviceDTOList(getReducingActionAdviceDTOs(), getCodeListDTOs(CodeList.REDUCING_ACTION_TYPE, CodeList.BASE_INDICATOR, CodeList.INTERFACE_TYPE)));
	}

	/**
	 * Admin only!
	 */
	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result saveOrUpdateAdvice() {
		if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
			throw new MyrmexFatalException(BusinessErrorType.WRONG_RIGHT);
		}

		ReducingActionAdviceDTO dto = extractDTOFromRequest(ReducingActionAdviceDTO.class);

		ReducingActionAdvice advice;
		if (dto.getId() == null) {
			advice = new ReducingActionAdvice();
		} else {
			advice = reducingActionAdviceService.findById(dto.getId());
		}

		advice.setTitle(dto.getTitle());
		advice.setAwacCalculator(awacCalculatorService.findByCode(new InterfaceTypeCode(dto.getInterfaceTypeKey())));
		ReducingActionTypeCode actionType = ReducingActionTypeCode.valueOf(dto.getTypeKey());
		advice.setType(actionType);

		advice.setPhysicalMeasure(dto.getPhysicalMeasure());

		Set<ReducingActionAdviceBaseIndicatorAssociation> biAssociations = new HashSet<>();
		if (ReducingActionTypeCode.REDUCING_GES.equals(actionType)) {
			for (ReducingActionAdviceDTO.BaseIndicatorAssociationDTO biAssociationDTO : dto.getBaseIndicatorAssociations()) {
				BaseIndicator baseIndicator = baseIndicatorService.getByCode(new BaseIndicatorCode(biAssociationDTO.getBaseIndicatorKey()));
				Double percent = biAssociationDTO.getPercent();
				biAssociations.add(new ReducingActionAdviceBaseIndicatorAssociation(advice, baseIndicator, percent));
			}
		}
		advice.setBaseIndicatorAssociations(biAssociations);

		advice.setFinancialBenefit(dto.getFinancialBenefit());
		advice.setInvestmentCost(dto.getInvestmentCost());
		advice.setExpectedPaybackTime(dto.getExpectedPaybackTime());

		advice.setWebSite(dto.getWebSite());
		advice.setResponsiblePerson(dto.getResponsiblePerson());
		advice.setComment(dto.getComment());

		List<StoredFile> documents = new ArrayList<>();
		for (FilesUploadedDTO file : dto.getFiles()) {
			documents.add(storedFileService.findById(file.getId()));
		}
		advice.setDocuments(documents);

		reducingActionAdviceService.saveOrUpdate(advice);
		return ok(conversionService.convert(advice, ReducingActionAdviceDTO.class));
	}

	/**
	 * Admin only!
	 */
	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result deleteAdvice() {
		if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
			throw new MyrmexFatalException(BusinessErrorType.WRONG_RIGHT);
		}

		ReducingActionAdviceDTO dto = extractDTOFromRequest(ReducingActionAdviceDTO.class);
		ReducingActionAdvice advice = reducingActionAdviceService.findById(dto.getId());
		reducingActionAdviceService.remove(advice);
		return ok();
	}

	private List<ReducingActionAdviceDTO> getReducingActionAdviceDTOs() {
		List<ReducingActionAdviceDTO> res = new ArrayList<>();
		for (ReducingActionAdvice reducingActionAdvice : reducingActionAdviceService.findAll()) {
			res.add(conversionService.convert(reducingActionAdvice, ReducingActionAdviceDTO.class));
		}
		return res;
	}

	private List<ReducingActionAdviceDTO> computeAdviceDTOs(Organization organization, Period period) {
		InterfaceTypeCode interfaceTypeCode = organization.getInterfaceCode();
		AwacCalculator awacCalculator = awacCalculatorService.findByCode(interfaceTypeCode);

		List<Scope> scopes = new ArrayList<>();
		if (InterfaceTypeCode.ENTERPRISE.equals(interfaceTypeCode)) {
			scopes.addAll(organization.getSites());
		} else {
			scopes.add(organization);
		}
		List<ReportLogEntry> logEntries = new ArrayList<>();
		List<BaseActivityResult> baseActivityResults = reportResultService.getBaseActivityResults(awacCalculator, scopes, period, logEntries);

		List<ReducingActionAdviceDTO> res = new ArrayList<>();
		res.addAll(getReductionActionAdvices(awacCalculator, baseActivityResults));
		res.addAll(getBetterMeasureAdvices(logEntries));
		return res;
	}

	private List<ReducingActionAdviceDTO> getBetterMeasureAdvices(List<ReportLogEntry> logEntries) {
		List<ReducingActionAdviceDTO> res = new ArrayList<>();
		for (ReportLogEntry logEntry : new HashSet<>(logEntries)) {
			if (logEntry instanceof LowRankMeasureWarning) {
				LowRankMeasureWarning lowRankMeasureWarning = (LowRankMeasureWarning) logEntry;
				ReducingActionAdviceDTO.LowRankMeasureWarningDTO lowRankMeasureWarningDTO = new ReducingActionAdviceDTO.LowRankMeasureWarningDTO(lowRankMeasureWarning.getAlternativeGroupKey(), lowRankMeasureWarning.getMinRank());

				ReducingActionAdviceDTO reducingActionAdviceDTO = new ReducingActionAdviceDTO();
				reducingActionAdviceDTO.setTypeKey(ReducingActionTypeCode.BETTER_METHOD.getKey());
				reducingActionAdviceDTO.setLowRankMeasureWarning(lowRankMeasureWarningDTO);
				reducingActionAdviceDTO.setTitle(lowRankMeasureWarning.toString());
				res.add(reducingActionAdviceDTO);
			}
		}
		return res;
	}


	private List<ReducingActionAdviceDTO> getReductionActionAdvices(AwacCalculator awacCalculator, List<BaseActivityResult> baseActivityResults) {
		List<ReducingActionAdviceDTO> reducingActionDTOs = new ArrayList<>();
		List<ReducingActionAdvice> advices = reducingActionAdviceService.findByCalculator(awacCalculator);
		Logger.info("Found {} advice(s). Computing reduction objectives...", advices.size());
		for (ReducingActionAdvice advice : advices) {
			ReducingActionAdviceDTO adviceDTO = conversionService.convert(advice, ReducingActionAdviceDTO.class);

			Double computedGhgBenefit = computeGhgBenefit(advice, baseActivityResults);
			UnitCode computedGhgBenefitUnitCode = UnitCode.U5331;
			if (computedGhgBenefit < 1.0) {
				computedGhgBenefit = (computedGhgBenefit * 1000);
				computedGhgBenefitUnitCode = UnitCode.U5335;
			}
			adviceDTO.setComputedGhgBenefit(computedGhgBenefit);
			adviceDTO.setComputedGhgBenefitUnitKey(computedGhgBenefitUnitCode.getKey());

			reducingActionDTOs.add(adviceDTO);
		}
		return reducingActionDTOs;
	}

	private static Double computeGhgBenefit(ReducingActionAdvice advice, List<BaseActivityResult> baseActivityResults) {
		Map<BaseIndicatorCode, Double> ghgReductionObjectiveByBaseIndicator = new HashMap<>();
		for (ReducingActionAdviceBaseIndicatorAssociation baseIndicatorAssociation : advice.getBaseIndicatorAssociations()) {
			ghgReductionObjectiveByBaseIndicator.put(baseIndicatorAssociation.getBaseIndicator().getCode(), baseIndicatorAssociation.getPercent());
		}

		Double res = 0.0;
		for (BaseActivityResult baseActivityResult : baseActivityResults) {
			BaseIndicatorCode baseIndicatorCode = baseActivityResult.getBaseIndicator().getCode();
			Double ghgReductionPercent = ghgReductionObjectiveByBaseIndicator.get(baseIndicatorCode);
			if (ghgReductionPercent == null) {
				continue;
			}
			Double ghgEmission = baseActivityResult.getNumericValue();
			res += (ghgEmission * ghgReductionPercent / 100.0);
		}
		return res;
	}
}
