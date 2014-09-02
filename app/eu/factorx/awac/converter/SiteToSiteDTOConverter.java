package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SiteToSiteDTOConverter implements Converter<Site, SiteDTO> {

	@Autowired
	private ScopeService scopeService;

	@Override
	public SiteDTO convert(Site site) {

		SiteDTO dto = new SiteDTO();
		dto.setId(site.getId());
		dto.setName(site.getName());
		dto.setAccountingTreatment(site.getAccountingTreatment());
		dto.setDescription(site.getDescription());
		dto.setEconomicInterest(site.getEconomicInterest());
		dto.setNaceCode(site.getNaceCode());
		dto.setOperatingPolicy(site.getOperatingPolicy());
		dto.setPercentOwned(site.getPercentOwned());
		dto.setOrganizationalStructure(site.getOrganizationalStructure());


		Scope scope = scopeService.findBySite(site);
		dto.setScope(scope.getId());
		return dto;
	}
}
