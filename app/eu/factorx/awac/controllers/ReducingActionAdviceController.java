package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.ReducingActionAdviceDTOList;
import eu.factorx.awac.dto.awac.post.FilesUploadedDTO;
import eu.factorx.awac.dto.awac.shared.ReducingActionAdviceDTO;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.BaseIndicatorCode;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.ReducingActionTypeCode;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.models.knowledge.ReducingActionAdvice;
import eu.factorx.awac.models.knowledge.ReducingActionAdviceBaseIndicatorAssociation;
import eu.factorx.awac.service.AwacCalculatorService;
import eu.factorx.awac.service.BaseIndicatorService;
import eu.factorx.awac.service.ReducingActionAdviceService;
import eu.factorx.awac.service.StoredFileService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexFatalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

}
