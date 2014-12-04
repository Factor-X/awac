package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.service.FormService;
import eu.factorx.awac.service.QuestionService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import play.Logger;
import play.db.jpa.JPA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FormServiceImpl extends AbstractJPAPersistenceServiceImpl<Form> implements FormService {

	@Autowired
	private QuestionService questionService;

	@Override
	public Form findByIdentifier(String identifier) {
		List<Form> resultList = JPA.em().createNamedQuery(Form.FIND_BY_IDENTIFIER, Form.class)
				.setParameter("identifier", identifier).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one form with identifier = '" + identifier + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		Form form = resultList.get(0);
		Hibernate.initialize(form.getQuestionSets());
		return form;
	}

	@Override
	public List<Form> findByCalculator(AwacCalculator awacCalculator) {
		return JPA.em().createNamedQuery(Form.FIND_BY_CALCULATOR, Form.class)
				.setParameter("awacCalculator", awacCalculator).getResultList();
	}

	@Override
	public Map<CodeList, List<InterfaceTypeCode>> getInterfaceTypesByCodeList() {
		Map<CodeList, List<InterfaceTypeCode>> res = new HashMap<>();
		for (Form form : findAll()) {
			InterfaceTypeCode interfaceTypeCode = form.getAwacCalculator().getInterfaceTypeCode();
			List<CodeList> codeListsByForm = questionService.findCodeListsByForm(form);
			for (CodeList codeList : codeListsByForm) {
				List<InterfaceTypeCode> interfaceTypeCodes = res.get(codeList);
				if (interfaceTypeCodes == null) {
					interfaceTypeCodes = new ArrayList<>();
					interfaceTypeCodes.add(interfaceTypeCode);
					res.put(codeList, interfaceTypeCodes);
				} else {
					if (!interfaceTypeCodes.contains(interfaceTypeCode)) {
						interfaceTypeCodes.add(interfaceTypeCode);
					}
				}
			}

		}
		return res;
	}
}
