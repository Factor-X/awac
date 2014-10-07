package eu.factorx.awac.models.data.answer;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.VerificationStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by florian on 7/10/14.
 */
//@Entity
public class QuestionSetVerification extends AuditedAbstractEntity {

    @ManyToOne(optional = false)
    @Column(unique = true)
    private QuestionSetAnswer questionSetAnswer;

    private Account verificator;

    private VerificationStatus verificationStatus;

    @Column(columnDefinition = "TEXT")
    private String comment;

    public QuestionSetVerification() {
    }

    public QuestionSetAnswer getQuestionSetAnswer() {
        return questionSetAnswer;
    }

    public void setQuestionSetAnswer(QuestionSetAnswer questionSetAnswer) {
        this.questionSetAnswer = questionSetAnswer;
    }

    public Account getVerificator() {
        return verificator;
    }

    public void setVerificator(Account verificator) {
        this.verificator = verificator;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "QuestionSetVerification{" +
                "questionSetAnswer=" + questionSetAnswer +
                ", verificator=" + verificator +
                ", verificationStatus=" + verificationStatus +
                ", comment='" + comment + '\'' +
                '}';
    }
}
