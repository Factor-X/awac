package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.FormProgressDTO;


public class FormProgressListDTO extends DTO{

	List<FormProgressDTO> listFormProgress;

	public FormProgressListDTO() {
	}

	public FormProgressListDTO(List<FormProgressDTO> listFormProgress) {
		this.listFormProgress = listFormProgress;
	}

	public List<FormProgressDTO> getListFormProgress() {
		return listFormProgress;
	}

	public void setListFormProgress(List<FormProgressDTO> listFormProgress) {
		this.listFormProgress = listFormProgress;
	}

	@Override
	public String toString() {
		return "FormProgressListDTO{" +
				"listFormProgress=" + listFormProgress +
				'}';
	}
}
