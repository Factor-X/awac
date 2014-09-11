package eu.factorx.awac.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.ScopeService;

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

		if (site.getListPeriodAvailable() != null) {
			for (Period period : site.getListPeriodAvailable()) {
				dto.addPeriodAvailable(period.getPeriodCode().getKey());
			}
		}

		dto.setScope(site.getId());
		return dto;
	}
}
