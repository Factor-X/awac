package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.CodeListDTO;
import eu.factorx.awac.dto.awac.shared.SubListDTO;
import eu.factorx.awac.dto.awac.shared.SubListItemDTO;
import eu.factorx.awac.dto.awac.shared.SublistDTOList;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.service.CodesEquivalenceService;
import eu.factorx.awac.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
public class TranslationAdminController extends AbstractController {


	@Autowired
	private QuestionService questionService;

	@Autowired
	private CodesEquivalenceService codesEquivalenceService;

	@Autowired
	private ConversionService conversionService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadSublists() {
		List<SubListDTO> subListDTOs = toSubListDTOs(codesEquivalenceService.findAllSublistsData());
		return ok(new SublistDTOList(subListDTOs, getReferencedCodeListDTOs(subListDTOs)));
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadInterfaceCodeLabels() {
		getCodeListDTOs(CodeList.TRANSLATIONS_INTERFACE).get(0);
		return ok(getCodeListDTOs(CodeList.TRANSLATIONS_INTERFACE).get(0));
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

}
