package eu.factorx.awac.models.data.answer;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.VerificationStatus;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.VerificationRequest;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by florian on 7/10/14.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Verification.FIND_BY_VERIFICATION_REQUEST_AND_QUESTION_SET, query = "select p from Verification p where p.verificationRequest = :verificationRequest AND  p.questionSetAnswer.questionSet=:questionSet"),
})
public class Verification extends AuditedAbstractEntity {

    public static final String FIND_BY_VERIFICATION_REQUEST_AND_QUESTION_SET = "Verification_FIND_BY_VERIFICATION_REQUEST_AND_QUESTION_SET";

    @ManyToOne(optional = false)
    private Account verifier;

    @Embedded
    private VerificationStatus verificationStatus;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(optional = false,cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VerificationRequest verificationRequest;

    @OneToOne(optional = false)
    private QuestionSetAnswer questionSetAnswer;

    public Verification() {
    }

    public Verification(Account verifier, VerificationStatus verificationStatus) {
        this.verifier = verifier;
        this.verificationStatus = verificationStatus;
    }

    public Verification(Account verifier, VerificationStatus verificationStatus, String comment) {
        this.verifier = verifier;
        this.verificationStatus = verificationStatus;
        this.comment = comment;
    }

    public QuestionSetAnswer getQuestionSetAnswer() {
        return questionSetAnswer;
    }

    public void setQuestionSetAnswer(QuestionSetAnswer questionSetAnswer) {
        this.questionSetAnswer = questionSetAnswer;
    }

    public VerificationRequest getVerificationRequest() {
        return verificationRequest;
    }

    public void setVerificationRequest(VerificationRequest verificationRequest) {
        this.verificationRequest = verificationRequest;
    }

    public Account getVerifier() {
        return verifier;
    }

    public void setVerifier(Account verifier) {
        this.verifier = verifier;
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
                "verifier=" + verifier +
                ", verificationStatus=" + verificationStatus +
                ", comment='" + comment + '\'' +
                '}';
    }
}
