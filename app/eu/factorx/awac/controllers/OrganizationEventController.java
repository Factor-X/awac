package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.get.OrganizationEventDTO;
import eu.factorx.awac.dto.awac.get.OrganizationEventResultDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.OrganizationEvent;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.OrganizationEventService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * get all accounts for specified organization
     *//*
    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result loadEventsForOrganization() {
        return ok(getOrganizationEventDTO(securedController.getCurrentUser().getOrganization()));
    }*/


    /**
     * get all accounts for specified organization
     */
    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result loadEventsByPeriod(String periodKey) {
        Period period = periodService.findByCode(new PeriodCode(periodKey));
        OrganizationEventResultDTO resultDTO = getOrganizationEventDTO(period, securedController.getCurrentUser().getOrganization());
        Logger.info(resultDTO+"");
        return ok(resultDTO);
    }


    private OrganizationEventResultDTO getOrganizationEventDTO(Period period, Organization organization) {

        // get events for organization and given period
        //List<OrganizationEvent> organizationList = organizationEventService.findByOrganizationAndPeriod(org,period);
        // get events for organization and all periods
        List<OrganizationEvent> organizationList = organizationEventService.findByOrganization(organization);

        List<OrganizationEventDTO> organizationEventDTOList = new ArrayList<>();
        for (OrganizationEvent item : organizationList) {
            organizationEventDTOList.add(conversionService.convert(item, OrganizationEventDTO.class));
        }

        // create return DTO
        OrganizationEventResultDTO resultDto = new OrganizationEventResultDTO(organizationEventDTOList);

        // return list of associated
        return resultDto;
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

            }
            else{
                throw new MyrmexRuntimeException("organization event with id "+dto.getId()+" was not found");
            }
        } else {

            //control name / period / organization
            if(organizationEventService.findByOrganizationAndPeriodAndName(securedController.getCurrentUser().getOrganization(),period,dto.getName())!=null){
                return unauthorized(new ExceptionsDTO("you cannot use the same name and period for two event"));
            }


            orgEvent = new OrganizationEvent(securedController.getCurrentUser().getOrganization(), period, dto.getName(), dto.getDescription());
        }
        Logger.info(orgEvent+"");
        organizationEventService.saveOrUpdate(orgEvent);

        // return event DTO
        return ok(conversionService.convert(orgEvent, OrganizationEventDTO.class));
    }

} // end of class