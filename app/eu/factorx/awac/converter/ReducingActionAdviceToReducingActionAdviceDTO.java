package eu.factorx.awac.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.post.FilesUploadedDTO;
import eu.factorx.awac.dto.awac.shared.ReducingActionAdviceDTO;
import eu.factorx.awac.dto.awac.shared.ReducingActionAdviceDTO.BaseIndicatorAssociationDTO;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.knowledge.ReducingActionAdvice;
import eu.factorx.awac.models.knowledge.ReducingActionAdviceBaseIndicatorAssociation;

@Component
public class ReducingActionAdviceToReducingActionAdviceDTO implements Converter<ReducingActionAdvice, ReducingActionAdviceDTO> {

	@Autowired
	private BaseIndicatorToBaseIndicatorDTOConverter baseIndicatorToBaseIndicatorDTOConverter;

	@Override
	public ReducingActionAdviceDTO convert(ReducingActionAdvice reducingAction) {
		ReducingActionAdviceDTO dto = new ReducingActionAdviceDTO();

		dto.setId(reducingAction.getId());
		dto.setTitle(reducingAction.getTitle());
		dto.setInterfaceTypeKey(reducingAction.getAwacCalculator().getInterfaceTypeCode().getKey());
		dto.setTypeKey(reducingAction.getType().getKey());
		dto.setPhysicalMeasure(reducingAction.getPhysicalMeasure());

		dto.setFinancialBenefit(reducingAction.getFinancialBenefit());
		dto.setInvestmentCost(reducingAction.getInvestmentCost());
		dto.setExpectedPaybackTime(reducingAction.getExpectedPaybackTime());

		dto.setWebSite(reducingAction.getWebSite());
		dto.setResponsiblePerson(reducingAction.getResponsiblePerson());
		dto.setComment(reducingAction.getComment());

		List<FilesUploadedDTO> fileDTOs = new ArrayList<>();
		for (StoredFile storedFile : reducingAction.getDocuments()) {
			fileDTOs.add(new FilesUploadedDTO(storedFile.getId(), storedFile.getOriginalName()));
		}
		dto.setFiles(fileDTOs);

		List<BaseIndicatorAssociationDTO> baseIndicatorAssociationDTOs = new ArrayList<>();
		for (ReducingActionAdviceBaseIndicatorAssociation baseIndicatorAssociation : reducingAction.getBaseIndicatorAssociations()) {
			String baseIndicatorKey = baseIndicatorAssociation.getBaseIndicator().getCode().getKey();
			Double percent = baseIndicatorAssociation.getPercent();
			baseIndicatorAssociationDTOs.add(new BaseIndicatorAssociationDTO(baseIndicatorKey, percent));
		}
		dto.setBaseIndicatorAssociations(baseIndicatorAssociationDTOs);

		return dto;
	}
}