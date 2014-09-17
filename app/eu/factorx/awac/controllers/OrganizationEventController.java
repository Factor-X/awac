package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.dto.OrganizationEventDTO;
import eu.factorx.awac.dto.awac.dto.OrganizationEventResultDTO;
import eu.factorx.awac.dto.awac.dto.SiteAddUsersDTO;
import eu.factorx.awac.dto.awac.dto.SiteAddUsersResultDTO;
import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.OrganizationEvent;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.*;
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
	 */
	@Transactional
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result loadEvents() {

		OrganizationEventDTO dto = extractDTOFromRequest(OrganizationEventDTO.class);
		Logger.info("Load Events - Organization Name: " + dto.getOrganization().getName() +  " Period:" + dto.getPeriod());

		// get organization
		Organization org = organizationService.findByName(dto.getOrganization().getName());

		// get organization
		Period period = periodService.findByCode(new PeriodCode(dto.getPeriod().getKey()));
		//Period period = periodService.findByCode(PeriodCode.P2013);

		Logger.info ("Organization id : " + org.getId());
		Logger.info ("Period id : " + period.getId());


		// get events for organization and given period
		List<OrganizationEvent> organizationList = organizationEventService.findByOrganizationAndPeriod(org,period);
		Logger.info("organizationList.size():" + organizationList.size());

		List<OrganizationEventDTO> organizationEventDTOList = new ArrayList<OrganizationEventDTO>();
		for (OrganizationEvent item : organizationList) {
			Logger.info("item:" + item.getName());
			organizationEventDTOList.add(conversionService.convert(item,OrganizationEventDTO.class));
		}

		Logger.info("organizationEventDTOList.size():" + organizationEventDTOList.size());
		// create return DTO
		OrganizationEventResultDTO resultDto = new OrganizationEventResultDTO (organizationEventDTOList);

		// return list of associated
		return ok(resultDto);
	}

	/**
	 * save event
	 */

	@Transactional
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result saveEvent () {

		OrganizationEventDTO dto = extractDTOFromRequest(OrganizationEventDTO.class);
		Logger.info("Save Event - Organization Name: " + dto.getOrganization().getName() +  " Period: " + dto.getPeriod());

		// get organization
		Organization org = organizationService.findByName(dto.getOrganization().getName());

		// get period
		Period period = periodService.findByCode(new PeriodCode(dto.getPeriod().getKey()));
		//Period period = periodService.findByCode(PeriodCode.P2013);

		OrganizationEvent orgEvent = new OrganizationEvent(org,period,dto.getName(),dto.getDescription());

		organizationEventService.saveOrUpdate(orgEvent);

		// return event DTO
		return ok(conversionService.convert(orgEvent, OrganizationEventDTO.class));
	}

} // end of class