package eu.factorx.awac.models.data.answer.type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import eu.factorx.awac.models.data.answer.AnswerRawData;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.file.StoredFile;

@Entity
@DiscriminatorValue("DOCUMENT")
public class DocumentAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;

    @ManyToOne
    private StoredFile storedFile;

    public DocumentAnswerValue() {
    }

    public DocumentAnswerValue(QuestionAnswer questionAnswer, StoredFile storedFile) {
        super();
        this.questionAnswer = questionAnswer;
        this.storedFile = storedFile;
    }

    public StoredFile getStoredFile() {
        return storedFile;
    }

    public void setStoredFile(StoredFile storedFile) {
        this.storedFile = storedFile;
    }

    @Override
    protected AnswerRawData getRawData() {
        AnswerRawData rawData = new AnswerRawData();

        //TODO implement

        return rawData;
    }

    @Override
    protected void setRawData(AnswerRawData rawData) {
        //TODO implement
    }

    @Override
    public String toString() {
        return "DocumentAnswerValue{" +
                "storedFile=" + storedFile +
                '}';
    }
}