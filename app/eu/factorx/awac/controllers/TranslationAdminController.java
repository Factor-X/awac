package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.CodeListDTO;
import eu.factorx.awac.dto.awac.get.FullCodeLabelDTO;
import eu.factorx.awac.dto.awac.get.UpdateFormsLabelsDTO;
import eu.factorx.awac.dto.awac.shared.*;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.CUD;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.*;

@org.springframework.stereotype.Controller
public class TranslationAdminController extends AbstractController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private CodesEquivalenceService codesEquivalenceService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private CodeLabelService codeLabelService;

	@Autowired
	private FormService formService;

	@Autowired
	private AwacCalculatorService awacCalculatorService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadSublists() {
		List<SubListDTO> subListDTOs = toSubListDTOs(codesEquivalenceService.findAllSublistsData());
		return ok(new UpdateSubListsDTO(subListDTOs, getReferencedCodeListDTOs(subListDTOs)));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result updateSublist() {
		SubListDTO dto = extractDTOFromRequest(SubListDTO.class);
		CodeList codeList = CodeList.valueOf(dto.getCodeList());
		CodeList referencedCodeList = CodeList.valueOf(dto.getReferencedCodeList());

		List<SubListItemDTO> items = dto.getItems();
		for (SubListItemDTO item : items) {
			Long id = item.getId();
			String key = item.getKey();
			Integer orderIndex = item.getOrderIndex();
			if (id == null) {
				CodesEquivalence codesEquivalence = codesEquivalenceService.saveOrUpdate(new CodesEquivalence(codeList, key, orderIndex, referencedCodeList, key));
				item.setId(codesEquivalence.getId());
				Logger.info("Created new sublist item with id = {}: {key: {}, pos: {}}", codesEquivalence.getId(), key, orderIndex);
			} else {
				CodesEquivalence codesEquivalence = codesEquivalenceService.findById(id);
				if (!(key.equals(codesEquivalence.getCodeKey()) && orderIndex.equals(codesEquivalence.getOrderIndex()))) {
					Logger.info("Updating sublist item with id = {}: {key: {}, pos: {}} -> {key: {}, pos: {}}", codesEquivalence.getId(), codesEquivalence.getCodeKey(), codesEquivalence.getOrderIndex(), key, orderIndex);
					codesEquivalence.setCodeKey(key);
					codesEquivalence.setReferencedCodeKey(key);
					codesEquivalence.setOrderIndex(orderIndex);
					codesEquivalenceService.saveOrUpdate(codesEquivalence);
				}
			}
		}
		return ok(dto);
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadBaseLists() {
		List<BaseListDTO> baseListDTOs = getBaseListDTOs();
		return ok(new UpdateBaseListsDTO(baseListDTOs));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result updateBaseLists() {
		UpdateBaseListsDTO dto = extractDTOFromRequest(UpdateBaseListsDTO.class);
		for (BaseListDTO baseListDTO : dto.getBaseLists()) {
			CodeList codeList = CodeList.valueOf(baseListDTO.getCodeList());
			List<FullCodeLabelDTO> codeLabelsDTOs = baseListDTO.getCodeLabels();

			HashMap<String, CodeLabel> savedCodeLabels = codeLabelService.findCodeLabelsByList(codeList);
			List<FullCodeLabelDTO> savedCodeLabelsDTOs = toCodeLabelDTOs(savedCodeLabels.values());

			CUD<FullCodeLabelDTO> cud = CUD.fromLists(savedCodeLabelsDTOs, codeLabelsDTOs);
			updateCodeLabels(savedCodeLabels, cud.getUpdated());
			saveCodeLabels(codeList, cud.getCreated());
		}
		List<BaseListDTO> baseListDTOs = getBaseListDTOs();
		return ok(new UpdateBaseListsDTO(baseListDTOs));
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadFormsLabels() {
//		UpdateFormsLabelsDTO res = new UpdateFormsLabelsDTO();
//		List<AwacCalculator> calculators = awacCalculatorService.findAll();
//		for (AwacCalculator calculator : calculators) {
//			InterfaceTypeCode code = calculator.getInterfaceTypeCode();
//			InterfaceTypeCode interfaceTypeCode = code;
//			CodeLabel codeLabel = codeLabelService.findCodeLabelByCode(interfaceTypeCode);
//			FullCodeLabelDTO fullCodeLabelDTO = conversionService.convert(codeLabel, FullCodeLabelDTO.class);
//
//			UpdateFormsLabelsDTO.CalculatorItem calculatorItem = new
//		}
//		List<Form> forms = formService.findAll();
//		for (Form form : forms) {
//			String identifier = form.getIdentifier();
//			codeLabelService.findCodeLabelByCode()
//		}
//		List<BaseListDTO> baseListDTOs = getBaseListDTOs();
//		return ok(new UpdateBaseListsDTO(baseListDTOs));
		return null;
	}


	private void updateCodeLabels(HashMap<String, CodeLabel> savedCodeLabels, List<FullCodeLabelDTO> updatedCodeLabelDTOs) {
		for (FullCodeLabelDTO codeLabelDTO : updatedCodeLabelDTOs) {
			CodeLabel codeLabel = savedCodeLabels.get(codeLabelDTO.getKey());
			Logger.info("Updating code label: \n\told: {} \n\tnew:{}", codeLabel, codeLabelDTO);
			codeLabel.setLabelEn(codeLabelDTO.getLabelEn());
			codeLabel.setLabelFr(codeLabelDTO.getLabelFr());
			codeLabel.setLabelNl(codeLabelDTO.getLabelNl());
			codeLabel.setOrderIndex(codeLabelDTO.getOrderIndex());
			codeLabel.setTopic(codeLabelDTO.getTopic());
			codeLabelService.saveOrUpdate(codeLabel);
		}
	}

	private void saveCodeLabels(CodeList codeList, List<FullCodeLabelDTO> createdCodeLabelDTOs) {
		for (FullCodeLabelDTO codeLabelDTO : createdCodeLabelDTOs) {
			CodeLabel codeLabel = new CodeLabel(codeList, codeLabelDTO.getKey(), codeLabelDTO.getLabelEn(), codeLabelDTO.getLabelFr(), codeLabelDTO.getLabelNl(), codeLabelDTO.getOrderIndex());
			codeLabelService.saveOrUpdate(codeLabel);
			codeLabelDTO.setId(codeLabel.getId());
		}
	}

	private List<BaseListDTO> getBaseListDTOs() {
		CodeList[] codeListsToExclude = {CodeList.TRANSLATIONS_SURVEY, CodeList.QUESTION,
				CodeList.TRANSLATIONS_INTERFACE, CodeList.TRANSLATIONS_ERROR_MESSAGES, CodeList.TRANSLATIONS_EMAIL_MESSAGE};

		List<BaseListDTO> baseListDTOs = new ArrayList<>();
		for (Map.Entry<CodeList, List<CodeLabel>> entry : codeLabelService.findAllBaseLists().entrySet()) {
			CodeList codeList = entry.getKey();
			if (!ArrayUtils.contains(codeListsToExclude, codeList)) {
				baseListDTOs.add(new BaseListDTO(codeList.name(), toCodeLabelDTOs(entry.getValue())));
			}
		}
		return baseListDTOs;
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadCodeLabels(String codeLists) {
		UpdateCodeLabelsDTO dto = new UpdateCodeLabelsDTO();

		String[] codeListsNames = StringUtils.split(codeLists, ',');
		for (String codeListName : codeListsNames) {
			HashMap<String, CodeLabel> codeLabels = codeLabelService.findCodeLabelsByList(CodeList.valueOf(codeListName));
			dto.putCodeLabels(codeListName, toCodeLabelDTOs(codeLabels.values()));
		}

		return ok(dto);
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result updateCodeLabels() {
		UpdateCodeLabelsDTO dto = extractDTOFromRequest(UpdateCodeLabelsDTO.class);

		for (String codeListName : dto.getCodeLabelsByList().keySet()) {
			List<FullCodeLabelDTO> afterCodeLabelDTOs = dto.getCodeLabelsByList().get(codeListName);
			HashMap<String, CodeLabel> codeLabels = codeLabelService.findCodeLabelsByList(CodeList.valueOf(codeListName));
			List<FullCodeLabelDTO> beforeCodeLabelDTOs = toCodeLabelDTOs(codeLabels.values());

			List<FullCodeLabelDTO> updatedCodeLabelDTOs = CUD.fromLists(beforeCodeLabelDTOs, afterCodeLabelDTOs).getUpdated();
			Logger.info("updatedCodeLabelDTOs = " + updatedCodeLabelDTOs);
			for (FullCodeLabelDTO fullCodeLabelDTO : updatedCodeLabelDTOs) {
				CodeLabel codeLabel = codeLabels.get(fullCodeLabelDTO.getKey());
				codeLabel.setLabelEn(fullCodeLabelDTO.getLabelEn());
				codeLabel.setLabelFr(fullCodeLabelDTO.getLabelFr());
				codeLabel.setLabelNl(fullCodeLabelDTO.getLabelNl());
				Logger.info("Before persisting, label en = '{}'", codeLabel.getLabelEn());
				codeLabelService.saveOrUpdate(codeLabel);
			}
		}
		return ok();
	}

	private List<CodeListDTO> getReferencedCodeListDTOs(List<SubListDTO> subListDTOs) {
		List<CodeList> referencedCodeLists = new ArrayList<>();
		for (SubListDTO subListDTO : subListDTOs) {
			CodeList referencedCodeList = CodeList.valueOf(subListDTO.getReferencedCodeList());
			if (!referencedCodeLists.contains(referencedCodeList)) {
				referencedCodeLists.add(referencedCodeList);
			}
		}
		return getCodeListDTOs(referencedCodeLists.toArray(new CodeList[referencedCodeLists.size()]));
	}

	private static List<SubListDTO> toSubListDTOs(List<CodesEquivalence> sublistsData) {
		Map<CodeList, SubListDTO> sublistsMaps = new LinkedHashMap<>();

		for (CodesEquivalence codesEquivalence : sublistsData) {
			SubListItemDTO subListItemDTO = new SubListItemDTO(codesEquivalence.getId(), codesEquivalence.getCodeKey(), codesEquivalence.getOrderIndex());

			CodeList codeList = codesEquivalence.getCodeList();
			CodeList referencedCodeList = codesEquivalence.getReferencedCodeList();

			SubListDTO subListDTO = sublistsMaps.get(codeList);
			if (subListDTO == null) {
				subListDTO = new SubListDTO(codeList.name(), referencedCodeList.name());
				subListDTO.addItem(subListItemDTO);
				sublistsMaps.put(codeList, subListDTO);
			} else {
				subListDTO.addItem(subListItemDTO);
			}
		}

		return new ArrayList<>(sublistsMaps.values());
	}

	private List<FullCodeLabelDTO> toCodeLabelDTOs(Iterable<CodeLabel> codeLabels) {
		List<FullCodeLabelDTO> codeLabelDTOs = new ArrayList<>();
		for (CodeLabel codeLabel : codeLabels) {
			codeLabelDTOs.add(conversionService.convert(codeLabel, FullCodeLabelDTO.class));
		}
		return codeLabelDTOs;
	}

}
