package eu.factorx.awac.controllers;

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
import eu.factorx.awac.service.impl.reporting.ReportLogEntry;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexFatalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.*;

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
		return ok(new ReducingActionAdviceDTOList(getReducingActionAdviceDTOs(organization, period), null));
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

		Set<ReducingActionAdviceBaseIndicatorAssociation> baseIndicatorAssociations = new HashSet<>();
		if (ReducingActionTypeCode.REDUCING_GES.equals(actionType)) {
			for (ReducingActionAdviceDTO.BaseIndicatorAssociationDTO baseIndicatorAssociationDTO : dto.getBaseIndicatorAssociations()) {
				BaseIndicatorCode baseIndicatorCode = new BaseIndicatorCode(baseIndicatorAssociationDTO.getBaseIndicatorKey());
				BaseIndicator baseIndicator = baseIndicatorService.getByCode(baseIndicatorCode);
				Double percent = baseIndicatorAssociationDTO.getPercent();
				baseIndicatorAssociations.add(new ReducingActionAdviceBaseIndicatorAssociation(advice, baseIndicator, percent));
			}
		}
		advice.setBaseIndicatorAssociations(baseIndicatorAssociations);

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

	private List<ReducingActionAdviceDTO> getReducingActionAdviceDTOs(Organization organization, Period period) {
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

			res.add(adviceDTO);
		}
		return res;
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
			Logger.info("Found one activity result matching a reduction advice (BaseIndicator = '{}'; GHG Emission = {}tCO2; Advice reduction objective = {}%", baseIndicatorCode, ghgEmission, ghgReductionPercent);
			res += (ghgEmission * ghgReductionPercent / 100.0);
		}
		return res;
	}
}
