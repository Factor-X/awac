package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.OrganizationDataDTO;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.ScopeTypeCode;
import eu.factorx.awac.models.forms.AwacCalculatorInstance;
import eu.factorx.awac.service.AwacCalculatorInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * Created by florian on 19/11/14.
 */
public class OrganizationToOrganizationDataDTOConverter implements Converter<Organization, OrganizationDataDTO> {

	@Autowired
	private OrganizationToOrganizationDTOConverter organizationToOrganizationDTOConverter;
	@Autowired
	private AwacCalculatorInstanceService awacCalculatorInstanceService;

	@Override
	public OrganizationDataDTO convert(Organization organization) {
		OrganizationDataDTO dto = new OrganizationDataDTO();

		dto.setOrganization(organizationToOrganizationDTOConverter.convert(organization));

		if (organization.getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.SITE)) {
			dto.setSiteNb(organization.getSites().size());

			//increment closed form
			for (Site site : organization.getSites()) {
				List<AwacCalculatorInstance> instanceList = awacCalculatorInstanceService.findByScope(site);
				for (AwacCalculatorInstance awacCalculatorInstance : instanceList) {
					if (awacCalculatorInstance.isClosed()) {
						dto.incrementClosedForm(awacCalculatorInstance.getPeriod().getPeriodCode().getKey());
					}
				}
			}
		} else if (organization.getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.PRODUCT)) {
			dto.setProductNb(organization.getProducts().size());

			//increment closed form
			for (Product product : organization.getProducts()) {
				List<AwacCalculatorInstance> instanceList = awacCalculatorInstanceService.findByScope(product);
				for (AwacCalculatorInstance awacCalculatorInstance : instanceList) {
					if (awacCalculatorInstance.isClosed()) {
						dto.incrementClosedForm(awacCalculatorInstance.getPeriod().getPeriodCode().getKey());
					}
				}
			}
		} else {

			//increment closed form
			List<AwacCalculatorInstance> instanceList = awacCalculatorInstanceService.findByScope(organization);
			for (AwacCalculatorInstance awacCalculatorInstance : instanceList) {
				if (awacCalculatorInstance.isClosed()) {
					dto.incrementClosedForm(awacCalculatorInstance.getPeriod().getPeriodCode().getKey());
				}
			}
		}

		return dto;
	}
}
