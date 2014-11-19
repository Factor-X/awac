package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.OrganizationDataDTO;
import eu.factorx.awac.dto.awac.get.ResultsDTO;
import eu.factorx.awac.dto.awac.post.SendEmailDTO;
import eu.factorx.awac.dto.awac.shared.ListDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexRuntimeException;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by florian on 19/11/14.
 */
@org.springframework.stereotype.Controller
public class OrganizationDataController extends AbstractController {


	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private ConversionService conversionService;
	@Autowired
	private EmailService emailService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result sendEmail() {

		if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
			throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
		}

		SendEmailDTO dto = extractDTOFromRequest(SendEmailDTO.class);

		//load interface code
		final InterfaceTypeCode interfaceTypeCode;

		if (dto.getCalculatorKey().equals(InterfaceTypeCode.ENTERPRISE.getKey())) {
			interfaceTypeCode = InterfaceTypeCode.ENTERPRISE;
		} else if (dto.getCalculatorKey().equals(InterfaceTypeCode.EVENT.getKey())) {
			interfaceTypeCode = InterfaceTypeCode.EVENT;
		} else if (dto.getCalculatorKey().equals(InterfaceTypeCode.HOUSEHOLD.getKey())) {
			interfaceTypeCode = InterfaceTypeCode.HOUSEHOLD;
		} else if (dto.getCalculatorKey().equals(InterfaceTypeCode.MUNICIPALITY.getKey())) {
			interfaceTypeCode = InterfaceTypeCode.MUNICIPALITY;
		} else if (dto.getCalculatorKey().equals(InterfaceTypeCode.LITTLE_EMITTER.getKey())) {
			interfaceTypeCode = InterfaceTypeCode.LITTLE_EMITTER;
		} else {
			throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
		}


		//load the email list
		List<Organization> organizations = organizationService.findByInterfaceTypeCode(interfaceTypeCode);
		Set<String> emailList = new HashSet<>();

		for (Organization organization : organizations) {
			if (dto.isOnlyOrganizationSharedData() == false ||
					(organization.getStatisticsAllowed() != null && organization.getStatisticsAllowed() == true)) {
				for (Account account : organization.getAccounts()) {
					//TODO select ??
					emailList.add(account.getPerson().getEmail());
				}
			}
		}

		//send email
		if (emailList.size() > 0) {
			EmailMessage email = new EmailMessage(emailList, dto.getEmailTitle(), dto.getEmailContent());
			emailService.send(email);
		}

		return ok(new ResultsDTO());
	}

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
