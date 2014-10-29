package eu.factorx.awac.dto.awac.shared;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.FilesUploadedDTO;

public class ActionAdviceDTO extends DTO {

	private String title;

	private String interfaceTypeKey;

	private String physicalMeasure;

	private Double ghgBenefit;

	private String ghgBenefitUnitKey;

	private Double financialBenefit;

	private Double investmentCost;

	private String expectedPaybackTime;

	private String webSite;

	private String responsiblePerson;

	private String comment;

	private List<FilesUploadedDTO> files;

	private List<BaseIndicatorDTO> baseIndicatorAssociations;

}
