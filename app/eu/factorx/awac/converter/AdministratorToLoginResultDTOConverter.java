package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Administrator;
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
public class AdministratorToLoginResultDTOConverter implements Converter<Administrator, LoginResultDTO> {

	@Autowired
	private AccountToLoginResultDTOConverter accountToLoginResultDTOConverter;

	@Override
	public LoginResultDTO convert(Administrator administrator) {
		return accountToLoginResultDTOConverter.convert(administrator);
	}
}
