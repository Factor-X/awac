package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

public class UpdateSurveysLabelsDTO extends DTO {

	private List<FormLabelsDTO> forms;

	public UpdateSurveysLabelsDTO() {
		this.forms = new ArrayList<>();
	}

	public List<FormLabelsDTO> getForms() {
		return forms;
	}

	public void setForms(List<FormLabelsDTO> forms) {
		this.forms = forms;
	}

	public boolean addForm(FormLabelsDTO formLabelsDTO) {
		return this.forms.add(formLabelsDTO);
	}

}
