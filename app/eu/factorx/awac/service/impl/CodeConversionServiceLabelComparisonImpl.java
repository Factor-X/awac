package eu.factorx.awac.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import play.Logger;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.service.CodeConversionService;
import eu.factorx.awac.service.CodeLabelService;

/**
 * Temporary implementation of {@link CodeConversionService} based on labels comparison.
 * 
 * <br>
 * <br>
 * <b>TODO</b> This impl is a performance lack, and result is not consistent! Delete this, and use instead {@link CodeConversionServiceImpl} when {@link CodesEquivalence} data will be
 * present in DB.
 * 
 */
@Component
@Qualifier("codeConversionServiceLabelComparisonImpl")
public class CodeConversionServiceLabelComparisonImpl implements CodeConversionService {

	@Autowired
	CodeLabelService codeLabelService;

	@Override
	public ActivitySourceCode toActivitySourceCode(Code code) {
		ActivitySourceCode res = null;
		String labelToFind = codeLabelService.findCodeLabelByCode(code).getLabelFr();
		List<CodeLabel> activitySources = codeLabelService.findCodeLabelsByType(CodeList.ActivitySource);

		String key = findExactEquivalence(labelToFind, activitySources);

		if (key == null) {
			Logger.warn("Cannot find an ActivitySourceCode having the same label than input code '" + code
					+ "' - Trying to compare after removing non-ascci characters...");
			key = findByAsciiComparison(labelToFind, activitySources);
			if (key == null) {
				Logger.error("Cannot find an ActivitySourceCode for input code " + code + "!");
			}
		} else {
			res = new ActivitySourceCode(key);
		}

		return res;
	}

	@Override
	public ActivityTypeCode toActivityTypeCode(Code code) {
		ActivityTypeCode res = null;
		String labelToFind = codeLabelService.findCodeLabelByCode(code).getLabelFr();
		List<CodeLabel> activityTypes = codeLabelService.findCodeLabelsByType(CodeList.ActivityType);

		String key = findExactEquivalence(labelToFind, activityTypes);

		if (key == null) {
			Logger.warn("Cannot find an ActivityTypeCode having the same label than input code '" + code
					+ "' - Trying to compare after removing non-ascci characters...");
			key = findByAsciiComparison(labelToFind, activityTypes);
			if (key == null) {
				Logger.error("Cannot find an ActivityTypeCode for input code " + code + "!");
			}
		} else {
			res = new ActivityTypeCode(key);
		}

		return res;
	}

	private String findExactEquivalence(String labelToFind, List<CodeLabel> codeLabels) {
		labelToFind = normalizeSpace(labelToFind);

		for (CodeLabel codeLabel : codeLabels) {
			if (labelToFind.equalsIgnoreCase(normalizeSpace(codeLabel.getLabelFr()))) {
				return codeLabel.getKey();
			}
		}
		return null;
	}

	private String findByAsciiComparison(String labelToFind, List<CodeLabel> codeLabels) {
		labelToFind = removeAllNonAsciiCharacters(labelToFind);

		List<String> resultList = new ArrayList<>();
		for (CodeLabel codeLabel : codeLabels) {
			if (labelToFind.equalsIgnoreCase(removeAllNonAsciiCharacters(codeLabel.getLabelFr()))) {
				resultList.add(codeLabel.getKey());
			}
		}
		if (resultList.size() == 1) {
			return resultList.get(0);
		}

		int nbResults = resultList.size();
		if (nbResults == 0) {
			Logger.info("No code label matching " + labelToFind);
		} else if (nbResults > 1) {
			Logger.info("More than one (" + nbResults + ") code labels matching " + labelToFind);
		}
		return null;
	}

	private String normalizeSpace(String input) {
		return StringUtils.normalizeSpace(input);
	}

	private String removeAllNonAsciiCharacters(String input) {
		return StringUtils.normalizeSpace(input.replaceAll("[^a-zA-Z0-9]+", ""));
	}

}
