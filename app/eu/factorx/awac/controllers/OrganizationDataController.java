package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.OrganizationDataDTO;
import eu.factorx.awac.dto.awac.shared.ListDTO;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

/**
 * Created by florian on 19/11/14.
 */
@org.springframework.stereotype.Controller
public class OrganizationDataController extends AbstractController {


	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private ConversionService conversionService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result getOrganizationData() {

		if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
			throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
		}

		List<Organization> organizationList = organizationService.findAll();

		ListDTO<OrganizationDataDTO> result = new ListDTO<>();

		for (Organization organization : organizationList) {
			if (!organization.getInterfaceCode().equals(InterfaceTypeCode.ADMIN) &&
					!organization.getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
				result.add(conversionService.convert(organization, OrganizationDataDTO.class));
			}
		}

		return ok(result);
	}

}
