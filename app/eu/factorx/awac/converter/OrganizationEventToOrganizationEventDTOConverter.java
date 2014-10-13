package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.OrganizationEventDTO;
import eu.factorx.awac.models.business.OrganizationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrganizationEventToOrganizationEventDTOConverter implements Converter<OrganizationEvent, OrganizationEventDTO> {

	@Autowired
	private OrganizationToOrganizationDTOConverter organizationDTOConverter;

	@Autowired
	private PeriodToPeriodDTOConverter periodDTOConverter;
	
	@Override
	public OrganizationEventDTO convert(OrganizationEvent orgEvent) {
		OrganizationEventDTO dto = new OrganizationEventDTO();

		//dto.setOrganization(organizationDTOConverter.convert(orgEvent.getOrganization()));
		dto.setPeriod(periodDTOConverter.convert(orgEvent.getPeriod()));
		dto.setName(orgEvent.getName());
		dto.setDescription(orgEvent.getDescription());
		dto.setId(orgEvent.getId());

		return dto;
	}
}
