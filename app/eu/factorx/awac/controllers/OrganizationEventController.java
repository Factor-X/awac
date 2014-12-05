package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.get.OrganizationEventDTO;
import eu.factorx.awac.dto.awac.get.OrganizationEventResultDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.OrganizationEvent;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.OrganizationEventService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.service.VerificationRequestService;
import eu.factorx.awac.util.MyrmexRuntimeException;

//annotate as Spring Component
@Transactional(readOnly = false)
@org.springframework.stereotype.Controller
public class OrganizationEventController extends AbstractController {


    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationEventService organizationEventService;

    @Autowired
    private PeriodService periodService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private VerificationRequestService verificationRequestService;

    /**
     * get all accounts for specified organization
     */
    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result loadEventsByOrganization(Long organizationId) {

        //control organization
        boolean checked = false;
        Organization organizationTarget=organizationService.findById(organizationId);
        if (organizationTarget.equals(securedController.getCurrentUser().getOrganization())) {
            checked = true;
        } else if (securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {

            //load organizaiton
            if (verificationRequestService.findByOrganizationCustomerAndOrganizationVerifier(organizationTarget, securedController.getCurrentUser().getOrganization()).size() > 0) {
                checked = true;
            }
        }
        if (!checked) {
            return unauthorized(new ExceptionsDTO("You doesn't have right for this action"));
        }

        List<OrganizationEvent> organizationList = organizationEventService.findByOrganization(organizationTarget);

        List<OrganizationEventDTO> organizationEventDTOList = new ArrayList<>();
        for (OrganizationEvent item : organizationList) {
            organizationEventDTOList.add(conversionService.convert(item, OrganizationEventDTO.class));
        }

        return ok(new OrganizationEventResultDTO(organizationEventDTOList));
    }


    /**
     * get all accounts for specified organization
     */
    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result load() {

        List<OrganizationEvent> organizationList = organizationEventService.findByOrganization(securedController.getCurrentUser().getOrganization());

        Logger.info(organizationList+"");

        List<OrganizationEventDTO> organizationEventDTOList = new ArrayList<>();
        for (OrganizationEvent item : organizationList) {
            organizationEventDTOList.add(conversionService.convert(item, OrganizationEventDTO.class));
        }

        return ok(new OrganizationEventResultDTO(organizationEventDTOList));
    }

    /**
     * save event
     */

    @Transactional
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result saveEvent() {

        OrganizationEventDTO dto = extractDTOFromRequest(OrganizationEventDTO.class);

        OrganizationEvent orgEvent = null;

        Period period = periodService.findByCode(new PeriodCode(dto.getPeriod().getKey()));

        if (dto.getId() != null) {
            // update
            orgEvent = organizationEventService.findById(dto.getId());
            if (orgEvent != null) {
                orgEvent.setName(dto.getName());
                orgEvent.setDescription(dto.getDescription());
                orgEvent.setPeriod(period);

            } else {
                throw new MyrmexRuntimeException("organization event with id " + dto.getId() + " was not found");
            }
        } else {

            //control name / period / organization
            if (organizationEventService.findByOrganizationAndPeriodAndName(securedController.getCurrentUser().getOrganization(), period, dto.getName()) != null) {
                return unauthorized(new ExceptionsDTO("you cannot use the same name and period for two event"));
            }


            orgEvent = new OrganizationEvent(securedController.getCurrentUser().getOrganization(), period, dto.getName(), dto.getDescription());
        }
        Logger.info(orgEvent + "");
        organizationEventService.saveOrUpdate(orgEvent);

        // return event DTO
        return ok(conversionService.convert(orgEvent, OrganizationEventDTO.class));
    }

} // end of class