package eu.factorx.awac.dto.awac.shared;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.FilesUploadedDTO;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.ReducingActionAdviceBaseIndicatorAssociation;
import eu.factorx.awac.models.knowledge.Unit;

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
