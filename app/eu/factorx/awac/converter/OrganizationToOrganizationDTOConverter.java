package eu.factorx.awac.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;

@Component
public class OrganizationToOrganizationDTOConverter implements Converter<Organization, OrganizationDTO> {

	@Autowired
	private SiteToSiteDTOConverter siteToSiteDTOConverter;

	@Autowired
	private AccountToPersonDTOConverter accountToPersonDTOConverter; 
	
	@Override
	public OrganizationDTO convert(Organization org) {
		OrganizationDTO dto = new OrganizationDTO();
		dto.setId(org.getId());
		dto.setName(org.getName());
		for (Site site : org.getSites()) {
			dto.getSites().add(siteToSiteDTOConverter.convert(site));
		}
		for (Account account : org.getAccounts()) {
			dto.getUsers().add(accountToPersonDTOConverter.convert(account));
		}
		return dto;
	}
}
