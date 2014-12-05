package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.CodeListDTO;
import eu.factorx.awac.dto.awac.get.FormLabelsDTO;
import eu.factorx.awac.dto.awac.get.UpdateCodeLabelDTO;
import eu.factorx.awac.dto.awac.get.UpdateSurveysLabelsDTO;
import eu.factorx.awac.dto.awac.shared.*;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
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

	public static final CodeList[] BASE_LISTS = {CodeList.ActivityType, CodeList.ActivitySource, CodeList.ActivityCategory, CodeList.ActivitySubCategory, CodeList.INDICATOR_TYPE};
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
		Map<CodeList, List<InterfaceTypeCode>> interfaceTypesByCodeList = formService.getInterfaceTypesByCodeList();
		List<SubListDTO> subListDTOs = toSubListDTOs(codesEquivalenceService.findAllSublistsData(), interfaceTypesByCodeList);
		return ok(new UpdateSubListsDTO(subListDTOs, getReferencedCodeListDTOs(subListDTOs)));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result updateSublist() {
		UpdateSubListsDTO dto = extractDTOFromRequest(UpdateSubListsDTO.class);
		Logger.info("dto.getSublists() = " + dto.getSublists());
		Map<CodeList, List<SubListItemDTO>> newSubListDTOs = getSubListDTOMap(dto.getSublists());
		Map<CodeList, List<InterfaceTypeCode>> interfaceTypesByCodeList = formService.getInterfaceTypesByCodeList();

		for (SubListDTO oldSubListDTO : toSubListDTOs(codesEquivalenceService.findAllSublistsData(), interfaceTypesByCodeList)) {
			CodeList codeList = CodeList.valueOf(oldSubListDTO.getCodeList());
			CodeList referencedCodeList = CodeList.valueOf(oldSubListDTO.getReferencedCodeList());

			List<SubListItemDTO> oldListItems = oldSubListDTO.getItems();
			List<SubListItemDTO> newListItems = newSubListDTOs.get(codeList);

			CUD<SubListItemDTO> cud = CUD.fromLists(oldListItems, newListItems);
			updateSubListItems(cud.getUpdated());
			saveSubListItems(codeList, referencedCodeList, cud.getCreated());
		}
		return loadSublists();
	}

	private void saveSubListItems(CodeList codeList, CodeList referencedCodeList, List<SubListItemDTO> created) {
		for (SubListItemDTO subListItemDTO : created) {
			String key = subListItemDTO.getKey();
			codesEquivalenceService.saveOrUpdate(new CodesEquivalence(codeList, key, subListItemDTO.getOrderIndex(), referencedCodeList, key));
		}
	}

	private void updateSubListItems(List<SubListItemDTO> updated) {
		for (SubListItemDTO subListItemDTO : updated) {
			CodesEquivalence codesEquivalence = codesEquivalenceService.findById(subListItemDTO.getId());
			codesEquivalence.setOrderIndex(subListItemDTO.getOrderIndex());
			codesEquivalenceService.saveOrUpdate(codesEquivalence);
		}
	}

	private static Map<CodeList,List<SubListItemDTO>> getSubListDTOMap(List<SubListDTO> sublists) {
		Map<CodeList, List<SubListItemDTO>> res = new HashMap<>();
		for (SubListDTO subListDTO: sublists) {
			res.put(CodeList.valueOf(subListDTO.getCodeList()), subListDTO.getItems());
		}
		return res;
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
			List<UpdateCodeLabelDTO> codeLabelsDTOs = baseListDTO.getCodeLabels();

			HashMap<String, CodeLabel> savedCodeLabels = codeLabelService.findCodeLabelsByList(codeList);
			List<UpdateCodeLabelDTO> savedCodeLabelsDTOs = toCodeLabelDTOs(savedCodeLabels.values());

			CUD<UpdateCodeLabelDTO> cud = CUD.fromLists(savedCodeLabelsDTOs, codeLabelsDTOs);
			updateCodeLabels(savedCodeLabels, cud.getUpdated());
			saveCodeLabels(codeList, cud.getCreated());
		}
		List<BaseListDTO> baseListDTOs = getBaseListDTOs();
		return ok(new UpdateBaseListsDTO(baseListDTOs));
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadLinkedLists() {
		return ok(new UpdateLinkedListsDTO(getLinkedListDTOs(), getCodeListDTOs(CodeList.ActivitySource, CodeList.ActivityType, CodeList.ActivitySubCategory)));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result updateLinkedLists() {
		UpdateLinkedListsDTO dto = extractDTOFromRequest(UpdateLinkedListsDTO.class);
		Map<CodeList, List<LinkedListItemDTO>> newlinkedListDTOs = byCodeList(dto.getLinkedLists());

		for (LinkedListDTO oldLinkedListDTO : getLinkedListDTOs()) {
			CodeList codeList = CodeList.valueOf(oldLinkedListDTO.getCodeList());

			List<LinkedListItemDTO> oldListItems = oldLinkedListDTO.getItems();
			List<LinkedListItemDTO> newListItems = newlinkedListDTOs.get(codeList);

			CUD<LinkedListItemDTO> cud = CUD.fromLists(oldListItems, newListItems);
			updateLinkedListItems(cud.getUpdated());
			saveLinkedListItems(codeList, cud.getCreated());
		}
		return loadLinkedLists();
	}

	private void saveLinkedListItems(CodeList codeList, List<LinkedListItemDTO> createdItems) {
		for (LinkedListItemDTO listItemDTO : createdItems) {
			String codeKey = listItemDTO.getKey();
			codeLabelService.saveOrUpdate(new CodeLabel(codeList, codeKey, listItemDTO.getLabelEn(), listItemDTO.getLabelFr(), listItemDTO.getLabelNl(), listItemDTO.getOrderIndex()));
			codesEquivalenceService.saveOrUpdate(new CodesEquivalence(codeList, codeKey, CodeList.ActivitySource, listItemDTO.getActivitySourceKey()));
			codesEquivalenceService.saveOrUpdate(new CodesEquivalence(codeList, codeKey, CodeList.ActivityType, listItemDTO.getActivityTypeKey()));
		}
	}

	private void updateLinkedListItems(List<LinkedListItemDTO> updatedItems) {
		for (LinkedListItemDTO listItemDTO : updatedItems) {
			CodeLabel codeLabel = codeLabelService.findById(listItemDTO.getId());
			codeLabel.setLabelEn(listItemDTO.getLabelEn());
			codeLabel.setLabelFr(listItemDTO.getLabelFr());
			codeLabel.setLabelNl(listItemDTO.getLabelNl());
			codeLabel.setOrderIndex(listItemDTO.getOrderIndex());
			codeLabelService.saveOrUpdate(codeLabel);
		}
	}

	private static Map<CodeList, List<LinkedListItemDTO>> byCodeList(List<LinkedListDTO> linkedLists) {
		Map<CodeList, List<LinkedListItemDTO>> res = new HashMap<>();
		for (LinkedListDTO linkedListDTO : linkedLists) {
			res.put(CodeList.valueOf(linkedListDTO.getCodeList()), linkedListDTO.getItems());
		}
		return res;
	}

	private List<LinkedListDTO> getLinkedListDTOs() {
		List<LinkedListDTO> res = new ArrayList<>();

		Map<CodeList, List<InterfaceTypeCode>> interfaceTypesByCodeList = formService.getInterfaceTypesByCodeList();
		Map<CodeList, Map<String, List<CodesEquivalence>>> codesEquivalences = getCodesEquivalencesMap(codesEquivalenceService.findAllLinkedListsData());
		Map<CodeList, List<CodeLabel>> codeLabels = codeLabelService.findAllBaseLists();

		for (CodeList codeList : codeLabels.keySet()) {
			if (codesEquivalences.containsKey(codeList)) {
				res.add(toLinkedListDTO(codeList, codeLabels.get(codeList), codesEquivalences.get(codeList), toString(interfaceTypesByCodeList.get(codeList))));
			}
		}
		return res;
	}

	private LinkedListDTO toLinkedListDTO(CodeList codeList, List<CodeLabel> allCodeLabels, Map<String, List<CodesEquivalence>> allCodesEquivalences, String calculators) {
		List<LinkedListItemDTO> linkedListItemDTOs = new ArrayList<>();

		for (CodeLabel codeLabel : allCodeLabels) {
			String codeKey = codeLabel.getKey();
			Map<CodeList, String> links = new HashMap<>();
			for (CodesEquivalence codesEquivalence : allCodesEquivalences.get(codeKey)) {
				CodeList referencedCodeList = codesEquivalence.getReferencedCodeList();
				links.put(referencedCodeList, codesEquivalence.getReferencedCodeKey());
			}
			String activitySourceKey = links.get(CodeList.ActivitySource);
			String activityTypeKey = links.get(CodeList.ActivityType);
			linkedListItemDTOs.add(new LinkedListItemDTO(codeLabel.getId(), codeLabel.getKey(), codeLabel.getLabelEn(), codeLabel.getLabelFr(), codeLabel.getLabelNl(), codeLabel.getOrderIndex(), activitySourceKey, activityTypeKey));
		}
		return new LinkedListDTO(codeList.name(), linkedListItemDTOs, calculators);
	}

	private Map<CodeList, Map<String, List<CodesEquivalence>>> getCodesEquivalencesMap(List<CodesEquivalence> codesEquivalences) {
		Map<CodeList, Map<String, List<CodesEquivalence>>> res = new HashMap<>();
		for (CodesEquivalence codesEquivalence : codesEquivalences) {
			CodeList codeList = codesEquivalence.getCodeList();
			String codeKey = codesEquivalence.getCodeKey();
			if (!res.containsKey(codeList)) {
				res.put(codeList, new HashMap<String, List<CodesEquivalence>>());
			}
			Map<String, List<CodesEquivalence>> codeListData = res.get(codeList);
			if (!codeListData.containsKey(codeKey)) {
				codeListData.put(codeKey, new ArrayList<CodesEquivalence>());
			}
			codeListData.get(codeKey).add(codesEquivalence);
		}
		Logger.info("Found {} linked lists: {}", res.size(), StringUtils.join(res.keySet(), ','));
		return res;
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadSurveysLabels() {
		UpdateSurveysLabelsDTO res = new UpdateSurveysLabelsDTO();
		HashMap<String, CodeLabel> questionCodeLabels = codeLabelService.findCodeLabelsByList(CodeList.QUESTION);

		for (Form form : formService.findAll()) {
			FormLabelsDTO formItem = new FormLabelsDTO(form.getIdentifier(), form.getAwacCalculator().getInterfaceTypeCode().getKey());
			for (QuestionSet questionSet : form.getQuestionSets()) {
				formItem.addQuestionSet(toQuestionSetItem(questionSet, questionCodeLabels));
			}
			res.addForm(formItem);
		}
		return ok(res);
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result updateSurveysLabels() {
		UpdateCodeLabelsDTO updatedCodeLabelsDTO = extractDTOFromRequest(UpdateCodeLabelsDTO.class);
		List<UpdateCodeLabelDTO> codeLabelDTOs = updatedCodeLabelsDTO.getCodeLabelsByList().get(CodeList.QUESTION.name());
		for (UpdateCodeLabelDTO codeLabelDTO : codeLabelDTOs) {
			CodeLabel codeLabel = codeLabelService.findById(codeLabelDTO.getId());
			codeLabel.setLabelEn(codeLabelDTO.getLabelEn());
			codeLabel.setLabelFr(codeLabelDTO.getLabelFr());
			codeLabel.setLabelNl(codeLabelDTO.getLabelNl());
			codeLabelService.saveOrUpdate(codeLabel);
		}
		return ok();
	}

	private FormLabelsDTO.QuestionSetItem toQuestionSetItem(QuestionSet questionSet, Map<String, CodeLabel> questionCodeLabels) {
		FormLabelsDTO.QuestionSetItem questionSetItem = new FormLabelsDTO.QuestionSetItem(getQuestionCodeLabelDTO(questionSet.getCode(), questionCodeLabels));
		for (Question question : questionSet.getQuestions()) {
			FormLabelsDTO.QuestionItem questionItem = new FormLabelsDTO.QuestionItem(getQuestionCodeLabelDTO(question.getCode(), questionCodeLabels));
			questionSetItem.addQuestion(questionItem);
		}
		for (QuestionSet childQuestionSet : questionSet.getChildren()) {
			questionSetItem.addChild(toQuestionSetItem(childQuestionSet, questionCodeLabels));
		}
		return questionSetItem;
	}

	private UpdateCodeLabelDTO getQuestionCodeLabelDTO(QuestionCode code, Map<String, CodeLabel> questionCodeLabels) {
		UpdateCodeLabelDTO res;
		String codeKey = code.getKey();
		if (questionCodeLabels.containsKey(codeKey)) {
			res = conversionService.convert(questionCodeLabels.get(codeKey), UpdateCodeLabelDTO.class);
		} else {
			res = new UpdateCodeLabelDTO();
			res.setKey(codeKey);
		}
		return res;
	}


	private void updateCodeLabels(HashMap<String, CodeLabel> savedCodeLabels, List<UpdateCodeLabelDTO> updatedCodeLabelDTOs) {
		for (UpdateCodeLabelDTO codeLabelDTO : updatedCodeLabelDTOs) {
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

	private void saveCodeLabels(CodeList codeList, List<UpdateCodeLabelDTO> createdCodeLabelDTOs) {
		for (UpdateCodeLabelDTO codeLabelDTO : createdCodeLabelDTOs) {
			CodeLabel codeLabel = new CodeLabel(codeList, codeLabelDTO.getKey(), codeLabelDTO.getLabelEn(), codeLabelDTO.getLabelFr(), codeLabelDTO.getLabelNl(), codeLabelDTO.getOrderIndex());
			codeLabelService.saveOrUpdate(codeLabel);
			codeLabelDTO.setId(codeLabel.getId());
		}
	}

	//	private List<BaseListDTO> getBaseListDTOs() {
//		List<BaseListDTO> baseListDTOs = new ArrayList<>();
//		for (CodeList codeList : BASE_LISTS) {
//			HashMap<String, CodeLabel> codeLabelsByList = codeLabelService.findCodeLabelsByList(codeList);
//			baseListDTOs.add(new BaseListDTO(codeList.name(), toCodeLabelDTOs(codeLabelsByList.values())));
//		}
//		return baseListDTOs;
//	}
//
	private List<BaseListDTO> getBaseListDTOs() {
		CodeList[] codeListsToExclude = {CodeList.TRANSLATIONS_SURVEY, CodeList.QUESTION,
				CodeList.TRANSLATIONS_INTERFACE, CodeList.TRANSLATIONS_ERROR_MESSAGES, CodeList.TRANSLATIONS_EMAIL_MESSAGE};
		Map<CodeList, List<InterfaceTypeCode>> interfaceTypesByCodeList = formService.getInterfaceTypesByCodeList();

		List<BaseListDTO> baseListDTOs = new ArrayList<>();
		for (Map.Entry<CodeList, List<CodeLabel>> entry : codeLabelService.findAllBaseLists().entrySet()) {
			CodeList codeList = entry.getKey();
			if (!ArrayUtils.contains(codeListsToExclude, codeList) && (!codesEquivalenceService.isLinkedList(codeList))) {
				baseListDTOs.add(new BaseListDTO(codeList.name(), toCodeLabelDTOs(entry.getValue()), toString(interfaceTypesByCodeList.get(codeList))));
			}
		}
		return baseListDTOs;
	}

	private String toString(List<InterfaceTypeCode> interfaceTypeCodes) {
		if (interfaceTypeCodes == null) {
			return "";
		}
		List<String> res = new ArrayList<>();
		for (InterfaceTypeCode interfaceTypeCode : interfaceTypeCodes) {
			res.add(interfaceTypeCode.getKey());
		}
		return StringUtils.join(res, ", ");
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
			List<UpdateCodeLabelDTO> afterCodeLabelDTOs = dto.getCodeLabelsByList().get(codeListName);
			HashMap<String, CodeLabel> codeLabels = codeLabelService.findCodeLabelsByList(CodeList.valueOf(codeListName));
			List<UpdateCodeLabelDTO> beforeCodeLabelDTOs = toCodeLabelDTOs(codeLabels.values());

			List<UpdateCodeLabelDTO> updatedCodeLabelDTOs = CUD.fromLists(beforeCodeLabelDTOs, afterCodeLabelDTOs).getUpdated();
			Logger.info("updatedCodeLabelDTOs = " + updatedCodeLabelDTOs);
			for (UpdateCodeLabelDTO fullCodeLabelDTO : updatedCodeLabelDTOs) {
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

	private List<SubListDTO> toSubListDTOs(List<CodesEquivalence> sublistsData, Map<CodeList, List<InterfaceTypeCode>> interfaceTypesByCodeList) {
		Map<CodeList, SubListDTO> sublistsMaps = new LinkedHashMap<>();

		for (CodesEquivalence codesEquivalence : sublistsData) {
			SubListItemDTO subListItemDTO = new SubListItemDTO(codesEquivalence.getId(), codesEquivalence.getCodeKey(), codesEquivalence.getOrderIndex());

			CodeList codeList = codesEquivalence.getCodeList();
			CodeList referencedCodeList = codesEquivalence.getReferencedCodeList();

			SubListDTO subListDTO = sublistsMaps.get(codeList);
			if (subListDTO == null) {
				subListDTO = new SubListDTO(codeList.name(), referencedCodeList.name(), toString(interfaceTypesByCodeList.get(codeList)));
				subListDTO.addItem(subListItemDTO);
				sublistsMaps.put(codeList, subListDTO);
			} else {
				subListDTO.addItem(subListItemDTO);
			}
		}

		return new ArrayList<>(sublistsMaps.values());
	}

	private List<UpdateCodeLabelDTO> toCodeLabelDTOs(Iterable<CodeLabel> codeLabels) {
		List<UpdateCodeLabelDTO> codeLabelDTOs = new ArrayList<>();
		for (CodeLabel codeLabel : codeLabels) {
			codeLabelDTOs.add(conversionService.convert(codeLabel, UpdateCodeLabelDTO.class));
		}
		return codeLabelDTOs;
	}

}
