package eu.factorx.awac.converter;

import eu.factorx.awac.converter.AccountToPersonDTOConverter;
import eu.factorx.awac.converter.OrganizationToOrganizationDTOConverter;
import eu.factorx.awac.converter.PeriodToPeriodDTOConverter;
import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import play.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Component
public class AccountToAccountDTOConverter implements Converter<Account, AccountDTO> {

	@Autowired
	private AccountToPersonDTOConverter accountToPersonDTOConverter;

	@Autowired
	private OrganizationToOrganizationDTOConverter organizationToOrganizationDTOConverter;

	@Override
	public AccountDTO convert(Account account) {

		AccountDTO accountDTO = new AccountDTO();

		accountDTO.setIdentifier(account.getIdentifier());
		//create the person
		accountDTO.setPerson(accountToPersonDTOConverter.convert(account));
		// create the organization
		accountDTO.setOrganization(organizationToOrganizationDTOConverter.convert(account.getOrganization()));

		return accountDTO;
	}
}
