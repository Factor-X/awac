package eu.factorx.awac.service;

import eu.factorx.awac.models.data.answer.Verification;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.VerificationRequest;
import eu.factorx.awac.models.knowledge.UnitConversionFormula;

/**
 * Created by florian on 10/10/14.
 */

public interface VerificationService extends PersistenceService<Verification> {
    public Verification findByVerificationRequestAndQuestionSet(VerificationRequest verificationRequest, QuestionSet questionSet);
}
