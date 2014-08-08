package eu.factorx.awac.converter;

import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by florian on 4/07/14.
 */
@Component
public class AccountToPersonDTOConverter implements Converter<Account, PersonDTO> {

	@Override
	public PersonDTO convert(Account account) {

		PersonDTO personDTO = new PersonDTO();

		personDTO.setIdentifier(account.getIdentifier());
		personDTO.setEmail(account.getEmail());
		personDTO.setFirstName(account.getFirstname());
		personDTO.setLastName(account.getLastname());

		return personDTO;
	}
}
