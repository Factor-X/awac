package eu.factorx.awac.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.models.account.Account;


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
