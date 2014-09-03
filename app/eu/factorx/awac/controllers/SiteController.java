package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.dto.awac.post.AssignPeriodToSiteDTO;
import eu.factorx.awac.dto.awac.shared.ReturnDTO;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.service.SiteService;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


@org.springframework.stereotype.Controller
public class SiteController  extends AbstractController {

	@Autowired
	private SiteService siteService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private PeriodService periodService;


	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = false, isSystemAdmin = false)
	public Result edit(){

		SiteDTO dto = extractDTOFromRequest(SiteDTO.class);

		//control id
		if(dto.getId()== null){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//load site
		Site site = siteService.findById(dto.getId());

		// if the site is null
		if(site == null){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//edit
		site.setName(dto.getName());
		site.setNaceCode(dto.getNaceCode());
		site.setDescription(dto.getDescription());

		site.setOrganizationalStructure(dto.getOrganizationalStructure());
		site.setEconomicInterest(dto.getEconomicInterest());
		site.setOperatingPolicy(dto.getOperatingPolicy());
		site.setAccountingTreatment(dto.getAccountingTreatment());
		site.setPercentOwned(dto.getPercentOwned());

		siteService.saveOrUpdate(site);


		return ok(conversionService.convert(site,SiteDTO.class));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = false, isSystemAdmin = false)
	public Result create(){

		SiteDTO dto = extractDTOFromRequest(SiteDTO.class);

		//create
		Site site = new Site(securedController.getCurrentUser().getOrganization(), dto.getName());

		site.setNaceCode(dto.getNaceCode());
		site.setDescription(dto.getDescription());

		site.setOrganizationalStructure(dto.getOrganizationalStructure());
		site.setEconomicInterest(dto.getEconomicInterest());
		site.setOperatingPolicy(dto.getOperatingPolicy());
		site.setAccountingTreatment(dto.getAccountingTreatment());
		site.setPercentOwned(dto.getPercentOwned());

		siteService.saveOrUpdate(site);

		// return the new site because some new information are added, like id and scope
		return ok(conversionService.convert(site,SiteDTO.class));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = false, isSystemAdmin = false)
	public Result assignPeriodToSite(){

		AssignPeriodToSiteDTO dto = extractDTOFromRequest(AssignPeriodToSiteDTO.class);


		//load period
		Period period = periodService.findByCode(new PeriodCode(dto.getPeriodKeyCode()));

		if(period==null){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//load site
		Site site = siteService.findById(dto.getSiteId());

		if(site == null || !site.getOrganization().equals(securedController.getCurrentUser().getOrganization())){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//assign period to site
		boolean toAdd = dto.isAssign();

		//control is the period is already into site
		if(site.getListPeriodAvailable()!=null){
			for(Period periodToTest : site.getListPeriodAvailable()){
				if(periodToTest.equals(period)){

					//founded and to assign => useless to add
					if(dto.isAssign()){
						toAdd = false;
					}
					//founded and to remove => remove
					else{
						site.getListPeriodAvailable().remove(dto.isAssign());
					}
					break;
				}
			}
		}

		//add ig it's needed
		if(toAdd){
			site.getListPeriodAvailable().add(period);
		}

		//save
		siteService.saveOrUpdate(site);


		return ok(new ReturnDTO());
	}
}
