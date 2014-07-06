package eu.factorx.awac.converter;

import eu.factorx.awac.dto.myrmex.get.MyselfDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by florian on 4/07/14.
 */
@Component
public class AccountToMyselfDTOConverter implements Converter<Account, MyselfDTO>{

    @Autowired
    private AccountToPersonDTOConverter personDTOAdapter;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public MyselfDTO convert(Account account) {

        MyselfDTO myselfDTO = new MyselfDTO();

        //create the person
        myselfDTO.setPersonDTO(personDTOAdapter.convert(account));

        myselfDTO.setOrganizationName(account.getOrganization().getName());

        myselfDTO.setOrganizationId(account.getOrganization().getId());

        return myselfDTO;
    }
}
