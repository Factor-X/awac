package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.service.QuestionSetService;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

@Component
public class QuestionSetServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionSet> implements QuestionSetService {

    @Override
    public QuestionSet findByCode(QuestionCode code){
        List<QuestionSet> resultList = JPA.em().createNamedQuery(QuestionSet.FIND_BY_CODE, QuestionSet.class)
                .setParameter("code", code).getResultList();
        if (resultList.size() > 1) {
            String errorMsg = "More than one question with code = '" + code.getKey() + "'";
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
    }

}
