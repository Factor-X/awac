package eu.factorx.awac.controllers;


import eu.factorx.awac.dto.admin.get.DriverDTO;
import eu.factorx.awac.dto.awac.shared.ListDTO;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.data.question.Driver;
import eu.factorx.awac.service.DriverService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexFatalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;


@org.springframework.stereotype.Controller
public class DriverController extends AbstractController {

    @Autowired
    private ConversionService conversionService;
    @Autowired
    private DriverService driverService;

    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result getAll() {

        //control interface
        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
            throw new MyrmexFatalException(BusinessErrorType.WRONG_RIGHT);
        }

        ListDTO<DriverDTO> resultList = new ListDTO();

        //load
        for (Driver driver : driverService.findAll()) {
            resultList.add(conversionService.convert(driver, DriverDTO.class));
        }
        return ok(resultList);
    }


}
