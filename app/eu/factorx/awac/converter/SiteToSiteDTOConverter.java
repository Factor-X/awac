package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.AccountSiteAssociationService;
import eu.factorx.awac.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import play.Logger;

import java.util.List;

@Component
public class SiteToSiteDTOConverter implements Converter<Site, SiteDTO> {

    @Autowired
    private PeriodToPeriodDTOConverter periodToPeriodDTOConverter;

    @Autowired
    private AccountSiteAssociationService accountSiteAssociationService;

    @Autowired
    private AccountToPersonDTOConverter accountToPersonDTOConverter;

    @Override
    public SiteDTO convert(Site site) {

        SiteDTO dto = new SiteDTO();
        dto.setId(site.getId());
        dto.setName(site.getName());
        dto.setDescription(site.getDescription());
        dto.setNaceCode(site.getNaceCode());
        dto.setPercentOwned(site.getPercentOwned());
        dto.setOrganizationalStructure(site.getOrganizationalStructure());

        if (site.getListPeriodAvailable() != null) {
            for (Period period : site.getListPeriodAvailable()) {
                dto.addPeriodAvailable(periodToPeriodDTOConverter.convert(period));
            }
        }

        List<AccountSiteAssociation> associations = accountSiteAssociationService.findBySite(site);

        for (AccountSiteAssociation accountSiteAssociation : associations) {
            dto.addPerson(accountToPersonDTOConverter.convert(accountSiteAssociation.getAccount()));
        }


        Logger.info(dto.toString());

        dto.setScope(site.getId());
        return dto;
    }
}
