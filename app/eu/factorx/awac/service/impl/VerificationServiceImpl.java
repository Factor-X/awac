package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.data.answer.Verification;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.VerificationRequest;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.service.VerificationService;
import org.springframework.stereotype.Repository;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

/**
 * Created by florian on 10/10/14.
 */
@Repository
public class VerificationServiceImpl  extends AbstractJPAPersistenceServiceImpl<Verification> implements VerificationService {

    @Override
    public Verification findByVerificationRequestAndQuestionSet(VerificationRequest verificationRequest, QuestionSet questionSet){

        List<Verification> resultList = JPA.em().createNamedQuery(Verification.FIND_BY_VERIFICATION_REQUEST_AND_QUESTION_SET, Verification.class)
                .setParameter("verificationRequest", verificationRequest)
                .setParameter("questionSet", questionSet)
                        .getResultList();

        if (resultList.size() > 1) {
            String errorMsg = "More than one account with verificationRequest = '" + verificationRequest.getId() + ",questionSet:"+questionSet.getCode();
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);

    }

}
