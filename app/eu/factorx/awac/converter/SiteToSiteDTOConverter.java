package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.dto.myrmex.get.MyselfDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SiteToSiteDTOConverter implements Converter<Site, SiteDTO> {

    @Override
    public SiteDTO convert(Site site) {
        SiteDTO dto = new SiteDTO();
        dto.setId(site.getId());
        dto.setName(site.getName());
        return dto;
    }
}
