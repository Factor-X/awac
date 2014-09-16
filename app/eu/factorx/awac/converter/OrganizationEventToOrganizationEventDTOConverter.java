package eu.factorx.awac.converter;

import eu.factorx.awac.converter.AccountToPersonDTOConverter;
import eu.factorx.awac.converter.SiteToSiteDTOConverter;
import eu.factorx.awac.dto.awac.dto.OrganizationEventDTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.OrganizationEvent;
import eu.factorx.awac.models.business.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OrganizationEventToOrganizationEventDTOConverter implements Converter<OrganizationEvent, OrganizationEventDTO> {

	@Autowired
	private OrganizationToOrganizationDTOConverter organizationDTOConverter;

	@Autowired
	private PeriodToPeriodDTOConverter periodDTOConverter;
	
	@Override
	public OrganizationEventDTO convert(OrganizationEvent orgEvent) {
		OrganizationEventDTO dto = new OrganizationEventDTO();

		dto.setOrganization(organizationDTOConverter.convert(orgEvent.getOrganization()));
		dto.setPeriod(periodDTOConverter.convert(orgEvent.getPeriod()));

		return dto;
	}
}
