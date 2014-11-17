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
		return ok(toSublistDTOList(codesEquivalenceService.findAllSublistsData()));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result saveSublists() {
		SublistDTOList dtoList = extractDTOFromRequest(SublistDTOList.class);
		return ok();
	}

	private SublistDTOList toSublistDTOList(List<CodesEquivalence> sublistsData) {
		Map<CodeList, SubListDTO> sublistsMaps = new LinkedHashMap<>();
		List<CodeList> referencedCodeLists = new ArrayList<>();

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

			if (!referencedCodeLists.contains(referencedCodeList)) {
				referencedCodeLists.add(referencedCodeList);
			}
		}
		List<SubListDTO> subListDTOs = new ArrayList<>(sublistsMaps.values());
		List<CodeListDTO> codeListDTOs = getCodeListDTOs(referencedCodeLists.toArray(new CodeList[referencedCodeLists.size()]));
		return new SublistDTOList(subListDTOs, codeListDTOs);
	}

}
