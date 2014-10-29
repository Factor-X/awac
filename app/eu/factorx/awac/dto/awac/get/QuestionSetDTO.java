package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;

public class QuestionSetDTO extends DTO {

	private String code;

	private Boolean repetitionAllowed;

    private PersonDTO datalocker;

    private PersonDTO dataValidator;

    private VerificationDTO verification;

	private List<QuestionSetDTO> children;

	private List<QuestionDTO> questions;

	public QuestionSetDTO() {
	}

	public QuestionSetDTO(String code, Boolean repetitionAllowed, List<QuestionSetDTO> children, List<QuestionDTO> questions) {
		this.code = code;
		this.repetitionAllowed = repetitionAllowed;
		this.children = children;
		this.questions = questions;
	}

    public VerificationDTO getVerification() {
        return verification;
    }

    public void setVerification(VerificationDTO verification) {
        this.verification = verification;
    }

    public PersonDTO getDatalocker() {
        return datalocker;
    }

    public void setDatalocker(PersonDTO datalocker) {
        this.datalocker = datalocker;
    }

    public PersonDTO getDataValidator() {
        return dataValidator;
    }

    public void setDataValidator(PersonDTO dataValidator) {
        this.dataValidator = dataValidator;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getRepetitionAllowed() {
		return repetitionAllowed;
	}

	public void setRepetitionAllowed(Boolean repetitionAllowed) {
		this.repetitionAllowed = repetitionAllowed;
	}

	public List<QuestionSetDTO> getChildren() {
		return children;
	}

	public void setChildren(List<QuestionSetDTO> children) {
		this.children = children;
	}

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "QuestionSetDTO [code=" + code + ", repetitionAllowed=" + repetitionAllowed + ", children=" + children + ", questions="
				+ questions + "]";
	}

}
