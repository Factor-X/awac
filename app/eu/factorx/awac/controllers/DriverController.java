package eu.factorx.awac.controllers;


import eu.factorx.awac.dto.admin.get.DriverDTO;
import eu.factorx.awac.dto.admin.get.DriverValueDTO;
import eu.factorx.awac.dto.admin.get.ListDriverDTO;
import eu.factorx.awac.dto.awac.shared.ListDTO;
import eu.factorx.awac.dto.awac.shared.ResultMessageDTO;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.data.question.Driver;
import eu.factorx.awac.models.data.question.DriverValue;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.DriverService;
import eu.factorx.awac.service.DriverValueService;
import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexFatalException;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Collection;
import java.util.Collections;


@org.springframework.stereotype.Controller
public class DriverController extends AbstractController {

    @Autowired
    private ConversionService conversionService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private DriverValueService driverValueService;
    @Autowired
    private PeriodService periodService;

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result update() {

        //extract DTO
        ListDriverDTO dto = extractDTOFromRequest(ListDriverDTO.class);

        //control interface
        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_RIGHT);
        }

        //load dirver and period
        for (DriverDTO driverDTO : dto.getList()) {
            Driver driver = driverService.findById(driverDTO.getId());

            //for all drivers, at least one value from 2000
            boolean founded = false;
            if (driverDTO.getDriverValues() != null) {
                for (DriverValueDTO driverValueDTO : driverDTO.getDriverValues()) {
                    if (driverValueDTO.getFromPeriodKey().equals("2000") &&
                            driverValueDTO.getDefaultValue() != null) {
                        founded = true;
                    }
                }
            }

            if (!founded) {
                throw new MyrmexFatalException("Each driver must have a value from 2000 ");
            }

            //remove all
            for (DriverValue driverValue : driver.getDriverValueList()) {
                driverValueService.remove(driverValue);
            }

            //create new
            for (DriverValueDTO driverValueDTO : driverDTO.getDriverValues()) {
                if (driverValueDTO.getDefaultValue() != null && driverValueDTO.getFromPeriodKey() != null) {
                    //load period
                    Period period = periodService.findByCode(new PeriodCode(driverValueDTO.getFromPeriodKey()));

                    if (period != null) {
                        //create value
                        DriverValue driverValue = new DriverValue();
                        driverValue.setDefaultValue(driverValueDTO.getDefaultValue());
                        driverValue.setFromPeriod(period);
                        driverValue.setDriver(driver);

                        driverValueService.saveOrUpdate(driverValue);
                    }
                }
            }
        }

        return ok(new ResultMessageDTO());
    }

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

		Collections.sort(resultList.getList());

        return ok(resultList);
    }


}
