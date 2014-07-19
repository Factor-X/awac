package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrganizationToOrganizationDTOConverter implements Converter<Organization, OrganizationDTO> {

	@Autowired
	private SiteToSiteDTOConverter converter;

	@Override
	public OrganizationDTO convert(Organization org) {
		OrganizationDTO dto = new OrganizationDTO();
		dto.setId(org.getId());
		dto.setName(org.getName());
		for (Site site : org.getSites()) {
			dto.getSites().add(converter.convert(site));
		}
		return dto;
	}
}
