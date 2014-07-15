package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import play.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by root on 15/07/14.
 */
@Component
public class AccountToLoginResultDTOConverter implements Converter<Account, LoginResultDTO> {

    @Autowired
    private AccountToPersonDTOConverter accountToPersonDTOConverter;

    @Autowired
    private OrganizationToOrganizationDTOConverter organizationToOrganizationDTOConverter;

    @Autowired
    private PeriodToPeriodDTOConverter periodToPeriodDTOConverter;

    @Autowired
    private PeriodService periodService;

    @Override
    public LoginResultDTO convert(Account account) {

        LoginResultDTO loginResultDTO = new LoginResultDTO();

        //create the person
        loginResultDTO.setPerson(accountToPersonDTOConverter.convert(account));

        //create periodDTO
        List<PeriodDTO> periodsDTO = new ArrayList<>();
        List<Period> periods = periodService.findAll();
        Collections.sort(periods, new Comparator<Period>() {
            @Override
            public int compare(Period a, Period b) {
                return -a.getLabel().compareTo(b.getLabel());
            }
        });
        for (final Period period : periods) {
            periodsDTO.add(periodToPeriodDTOConverter.convert(period));
        }

        Logger.debug("map period : ");

        for(PeriodDTO periodDTO : periodsDTO){
            Logger.debug("  ->"+periodDTO);
        }

        loginResultDTO.setAvailablePeriods(periodsDTO);
        loginResultDTO.setDefaultPeriod(periods.get(0).getId());

        loginResultDTO.setOrganization(organizationToOrganizationDTOConverter.convert(account.getOrganization()));


        return loginResultDTO;
    }
}
