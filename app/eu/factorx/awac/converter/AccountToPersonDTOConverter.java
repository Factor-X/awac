package eu.factorx.awac.converter;

import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Administrator;
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
		personDTO.setEmail(account.getPerson().getEmail());
		personDTO.setFirstName(account.getPerson().getFirstname());
		personDTO.setLastName(account.getPerson().getLastname());
		personDTO.setIsActive(account.getActive());

		// is administrator ?
		if(account instanceof Administrator){
			personDTO.setIsAdmin(true);
		}
		else{
			personDTO.setIsAdmin(false);
		}

		return personDTO;
	}
}
